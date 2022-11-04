package com.deeplake.adven_one.entity.creatures;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityStrongAIMob extends EntityModUnit{

    public static final String SPEED = "speed";
    public static final String COOLDOWN = "cd";
    public static final String RANGE = "range";
    public static final String AI_ID = "ai_id";
    public static final String TYPE = "type";

    public EntityStrongAIMob(World worldIn) {
        super(worldIn);
        attack_all_players = 2;
        melee_atk = true;
        avengeful = true;
    }

    protected void firstTickAI() {

        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        applyTargetAI();
    }

    protected void applyTargetAI() {

    }

    //todo: write to nbt

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        int[] priorities = compound.getIntArray("ai_prior_list");
        for (int priority : priorities) {
            NBTTagCompound taskNBT = compound.getCompoundTag("p"+priority);
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
            NBTTagCompound taskNBT = compound.getCompoundTag("p"+priority);
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

    static EntityAIBase getAIFromTag(NBTTagCompound taskNBT, EntityCreature living)
    {
        if (taskNBT.hasNoTags() || !taskNBT.hasKey(AI_ID, 99))
        {
            return null;
        }

        EntityAIBase result = null;
        int id = taskNBT.getInteger(AI_ID);
        switch (id)
        {
            case 1:
                result = new EntityAISwimming(living);
                break;
            case 2:
                result = new EntityAIAttackMelee(living, 1, false);
                break;
            case 3:
                if (living instanceof IRangedAttackMob && living instanceof EntityMob)
                {
                    result = new EntityAIAttackRangedBow((EntityMob) living,
                            getFloat(taskNBT, SPEED, 0.5f),
                            getInteger(taskNBT, COOLDOWN, 20),
                            getFloat(taskNBT, RANGE, 15f));
                }
                break;
            case 4:
                if (living instanceof IRangedAttackMob)
                {
                    result = new EntityAIAttackRanged((IRangedAttackMob) living,
                            getFloat(taskNBT, SPEED, 0.5f),
                            getInteger(taskNBT, COOLDOWN, 20),
                            getFloat(taskNBT, RANGE, 15f));
                }
                break;
            case 5:
                result = new EntityAIWanderAvoidWater(living, 1.0, 0.001f);
                break;
            case 6:
                result = new EntityAIWatchClosest(living,
                        getEntityFromID(getInteger(taskNBT, TYPE, 0)),
                        getFloat(taskNBT, RANGE, 8f));
                break;
            case 7:
                result = new EntityAILookIdle(living);
                break;
            case 8:
                break;
            case 9:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
        return result;
    }

    public static Class<? extends Entity> getEntityFromID(int id)
    {
        switch (id)
        {
            case 0:
                return EntityPlayer.class;

            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
    }
}

