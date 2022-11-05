package com.deeplake.adven_one.entity.creatures;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.util.AIDef;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityStrongAIMob extends EntityModUnit{

    public static final String AI_PRIOR_LIST = "ai_prior_list";

    public EntityStrongAIMob(World worldIn) {
        super(worldIn);
    }

    protected void firstTickAI() {
        reloadAI();
    }

    protected void applyEntityAI() {
    }

    protected void applyTargetAI() {
    }

    public void reloadAI() {
        tasks.taskEntries.clear();
        targetTasks.taskEntries.clear();
        loadAIFromNBT(getEntityData());//todo: check whether those data will hold.
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        loadAIFromNBT(compound);
    }

    private void loadAIFromNBT(NBTTagCompound compound) {
        int[] priorities = compound.getIntArray(AI_PRIOR_LIST);
        for (int priority : priorities) {
            NBTTagCompound taskNBT = compound.getCompoundTag(String.valueOf(priority));
            if (!taskNBT.hasNoTags())
            {
                EntityAIBase aiBase = getAIFromTag(taskNBT, this);
                if (aiBase != null)
                {
                    this.tasks.addTask(priority, aiBase);
                }
            }
        }

        priorities = compound.getIntArray("target_prior_list");
        for (int priority : priorities) {
            NBTTagCompound taskNBT = compound.getCompoundTag(String.valueOf(priority));
            if (!taskNBT.hasNoTags())
            {
                EntityAIBase aiBase = getAIFromTag(taskNBT, this);
                if (aiBase != null)
                {
                    this.targetTasks.addTask(priority, aiBase);
                }
            }
        }
    }

    static float getFloat(NBTTagCompound taskNBT, String key, float fallback)
    {
        if (taskNBT.hasKey(key, 99))
        {
            return taskNBT.getFloat(key);
        }
        else {
            return fallback;
        }
    }

    static int getInteger(NBTTagCompound taskNBT, String key, int fallback)
    {
        if (taskNBT.hasKey(key, 99))
        {
            return taskNBT.getInteger(key);
        }
        else {
            return fallback;
        }
    }

    //not applicable. most vanilla variables are private.
//    static NBTTagCompound getTagFromAI(EntityAIBase aiBase, EntityCreature living) {
//        NBTTagCompound tag = new NBTTagCompound();
//
//    }

    static EntityAIBase getAIFromTag(NBTTagCompound taskNBT, EntityCreature living)
    {
        if (taskNBT.hasNoTags() || !taskNBT.hasKey(AIDef.AI_ID, 99))
        {
            return null;
        }

        EntityAIBase result = null;
        int id = taskNBT.getInteger(AIDef.AI_ID);
        switch (id)
        {
            case AIDef.ID_SWIM:
                result = new EntityAISwimming(living);
                break;
            case AIDef.ID_MELEE:
                result = new EntityAIAttackMelee(living, 1, false);
                break;
            case AIDef.ID_BOW:
                if (living instanceof IRangedAttackMob && living instanceof EntityMob)
                {
                    result = new EntityAIAttackRangedBow((EntityMob) living,
                            getFloat(taskNBT, AIDef.SPEED, 0.5f),
                            getInteger(taskNBT, AIDef.COOLDOWN, 20),
                            getFloat(taskNBT, AIDef.RANGE, 15f));
                }
                break;
            case AIDef.ID_RANGED:
                if (living instanceof IRangedAttackMob)
                {
                    result = new EntityAIAttackRanged((IRangedAttackMob) living,
                            getFloat(taskNBT, AIDef.SPEED, 0.5f),
                            getInteger(taskNBT, AIDef.COOLDOWN, 20),
                            getFloat(taskNBT, AIDef.RANGE, 15f));
                }
                break;
            case AIDef.ID_WANDER_LAND:
                result = new EntityAIWanderAvoidWater(living, 1.0, 0.001f);
                break;
            case AIDef.ID_WATCH:
                result = new EntityAIWatchClosest(living,
                        AIDef.getEntityFromID(getInteger(taskNBT, AIDef.TYPE, 0)),
                        getFloat(taskNBT, AIDef.RANGE, 8f));
                break;
            case AIDef.ID_IDLE:
                result = new EntityAILookIdle(living);
                break;
            case AIDef.ID_MOVE_VILLAGE:
                result = new EntityAIMoveThroughVillage(living, 1f, false);
                break;
            case AIDef.ID_EATGRASS:
                result = new EntityAIEatGrass(living);
                break;
            case AIDef.ID_FLEE_SUN:
                result = new EntityAIFleeSun(living, 1f);
                break;
            case AIDef.ID_LEAP:
                result = new EntityAILeapAtTarget(living, getFloat(taskNBT, AIDef.SPEED, 0.5f));
                break;
            case AIDef.ID_MOVE_TARGET:
                result = new EntityAIMoveTowardsTarget(living, 1f, getFloat(taskNBT, AIDef.RANGE, 8f));
                break;
            case 13:
                //is Abstract
                //result = new EntityAIMoveToBlock(living, 1f, getInteger(taskNBT, RANGE, 8)) ;
                break;
            case AIDef.ID_OPEN_DOOR:
                result = new EntityAIOpenDoor(living, true);
                break;
            case AIDef.ID_AVOID:
                result = new EntityAIAvoidEntity(living, AIDef.getEntityFromID(getInteger(taskNBT, AIDef.TYPE, 0)),
                        getFloat(taskNBT, AIDef.RANGE, 16f), 0.5f, 1.0f);
                break;
            case AIDef.INT_TEMPT:
                Item item = Item.getByNameOrId(taskNBT.getString(AIDef.TYPE));
                if (item != null)
                {
                    result = new EntityAITempt(living, getFloat(taskNBT, AIDef.RANGE, 16f), item, false);
                }
                else {
                    Idealland.LogWarning("Cannot parse Tempt Item for %s : %s", living.toString(), taskNBT.getString(AIDef.TYPE));
                }
                break;
            case AIDef.ID_PANIC:
                result = new EntityAIPanic(living, 1.5f);
                break;
            case AIDef.ID_INDOOR:
                result = new EntityAIMoveIndoors(living);
                break;
            case AIDef.ID_BREAK_DOOR:
                result = new EntityAIBreakDoor(living);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
        return result;
    }

}

