package com.deeplake.adven_one.entity.creatures.boss;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.entity.creatures.EntityGeneralMob;
import com.deeplake.adven_one.entity.creatures.ai.EntityMeleeAttackFakeSteve;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.util.CommonFunctions;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EntityBossSteve extends EntityBossBase{
    public EntityBossSteve(World worldIn) {
        super(worldIn);
        aiAttackOnCollide = new EntityMeleeAttackFakeSteve(this, 1.0D, false);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        setAttr(32, 0.3, ModConfig.SPECIAL_MOB_CONF.BOSS_STEVE_ATK,
                0,
                ModConfig.SPECIAL_MOB_CONF.BOSS_STEVE_HP);
    }

    enum EnumCombatClass
    {
        SWORD_SHIELD,
        AXE,
        RIDER,
        ARCHER,
        SUMMONER
    }

    EnumCombatClass combatClass = EnumCombatClass.AXE;

    public void setCombatClass(EnumCombatClass enumCombatClass)
    {
        if (!world.isRemote)
        {
            switch (enumCombatClass)
            {
                case SWORD_SHIELD:
                    setHeldItem(EnumHand.MAIN_HAND, getSuperSword());
                    setHeldItem(EnumHand.OFF_HAND, new ItemStack(Items.SHIELD));
                    break;
                case AXE:
                    setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.DIAMOND_AXE));
                    setHeldItem(EnumHand.OFF_HAND, new ItemStack(Items.TOTEM_OF_UNDYING));
                    break;
                case RIDER:
                    setHeldItem(EnumHand.MAIN_HAND, getSuperSword());
                    AbstractHorse horse = new EntityHorse(world);
                    horse.setPosition(posX, posY, posZ);
                    world.spawnEntity(horse);
                    startRiding(horse);
                    tasks.addTask(2, new EntityAIPanic(this, 3f));
                    break;
                case ARCHER:
                    setHeldItem(EnumHand.MAIN_HAND, getSuperBow());
                    setHeldItem(EnumHand.OFF_HAND, new ItemStack(Items.TOTEM_OF_UNDYING));
                    tasks.addTask(2, new EntityAIPanic(this, 3f));
                    break;
                case SUMMONER:
                    setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.DIAMOND_HOE));
                    setHeldItem(EnumHand.OFF_HAND, new ItemStack(Items.TOTEM_OF_UNDYING));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + enumCombatClass);
            }

            setItemStackToSlot(EntityEquipmentSlot.HEAD, getSuperHelm());
            setItemStackToSlot(EntityEquipmentSlot.CHEST, getSuperChest());
            setItemStackToSlot(EntityEquipmentSlot.LEGS, getSuperPants());
            setItemStackToSlot(EntityEquipmentSlot.FEET, getSuperShoes());
        }

        combatClass = enumCombatClass;
        setCombatTask();
    }

    private ItemStack getSuperSword() {
        ItemStack stack1;
        stack1 = new ItemStack(Items.DIAMOND_SWORD);
        stack1.addEnchantment(Enchantments.SHARPNESS, 4);
        stack1.addEnchantment(Enchantments.FIRE_ASPECT, 2);
        stack1.addEnchantment(Enchantments.UNBREAKING, 3);
        stack1.addEnchantment(Enchantments.KNOCKBACK, 2);
        return stack1;
    }

    private ItemStack getSuperBow() {
        ItemStack stack1;
        stack1 = new ItemStack(Items.BOW);
        stack1.addEnchantment(Enchantments.POWER, 4);
        stack1.addEnchantment(Enchantments.UNBREAKING, 3);
        stack1.addEnchantment(Enchantments.INFINITY, 1);
        stack1.addEnchantment(Enchantments.FLAME, 1);
        return stack1;
    }

    public ItemStack getSuperHelm(){
        ItemStack stack1 = new ItemStack(Items.DIAMOND_HELMET);
        stack1.addEnchantment(Enchantments.BLAST_PROTECTION, 4);
        stack1.addEnchantment(Enchantments.UNBREAKING, 3);
        return stack1;
    }

    public ItemStack getSuperChest(){
        ItemStack stack1 = new ItemStack(Items.DIAMOND_CHESTPLATE);
        stack1.addEnchantment(Enchantments.PROTECTION, 4);
        stack1.addEnchantment(Enchantments.UNBREAKING, 3);
        stack1.addEnchantment(Enchantments.THORNS, 2);
        return stack1;
    }

    public ItemStack getSuperPants(){
        ItemStack stack1 = new ItemStack(Items.DIAMOND_LEGGINGS);
        stack1.addEnchantment(Enchantments.FIRE_PROTECTION, 4);
        stack1.addEnchantment(Enchantments.UNBREAKING, 3);
//        stack1.addEnchantment(Enchantments.THORNS, 2);
        return stack1;
    }

    public ItemStack getSuperShoes(){
        ItemStack stack1 = new ItemStack(Items.DIAMOND_BOOTS);
        stack1.addEnchantment(Enchantments.FEATHER_FALLING, 4);
        stack1.addEnchantment(Enchantments.UNBREAKING, 3);
        stack1.addEnchantment(Enchantments.DEPTH_STRIDER, 3);
        return stack1;
    }

    //for multi-tick working
    static int performanceY = 0;
    static int performanceY_P = 3;
    public float rangeX = 3f, rangeZ = 3f, rangeYup = 5f, rangeYdown = 3f;

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.CACTUS || source == DamageSource.CRAMMING ||
        source == DamageSource.IN_WALL)
        {
            setEntityInvulnerable(true);
            world.createExplosion(this, posX,posY,posZ,2,true);
            setEntityInvulnerable(false);
        }
        else if (source == DamageSource.DROWN || source == DamageSource.LAVA ||
            source == DamageSource.HOT_FLOOR)
        {
            if (removeNearbyDanger()) return true;
        }

        return super.attackEntityFrom(source, amount);
    }

    private boolean removeNearbyDanger() {
        BlockPos origin = getPosition();
        BlockPos target = origin;

        World worldIn = world;

        int maxDone = 5;//overload protection

        //another protection. Y scanning.
        //don't scan all the blocks in one tick.
        int yStart = (int) (-rangeYdown + performanceY);
        int yEnd =  Math.min((int) (-rangeYdown + performanceY + performanceY_P), (int)rangeYup);
        performanceY += performanceY_P;
        performanceY %= rangeYup + rangeYdown;

        for (int x = (int) -rangeX; x <= rangeX; x++){
            for (int y = yStart; y <= yEnd; y++){
                for (int z = (int) -rangeZ; z <= rangeZ; z++){
                    target = origin.add(x,y,z);
                    IBlockState targetBlock = worldIn.getBlockState(target);
                    Block type =  targetBlock.getBlock();

                    if (type == Blocks.WATER || type == Blocks.FLOWING_WATER ||
                            type == Blocks.LAVA || type == Blocks.FLOWING_LAVA ||
                                    type == Blocks.MAGMA || type == Blocks.FIRE )
                    {
                        if (//type == Blocks.FLOWING_WATER ||
                                type == Blocks.FLOWING_LAVA)
                        {
                            TakeDamage(0.1f);
                            worldIn.setBlockState(target, Blocks.AIR.getDefaultState());
                            maxDone--;

                            //otherwise the tower will be soon destroyed
                        }
                        else {
                            TakeDamage(1f);
                            worldIn.setBlockState(target, Blocks.AIR.getDefaultState());
                            maxDone--;
                        }
                    }

                    if (maxDone <= 0)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void TakeDamage(float val)
    {
        damageEntity(DamageSource.GENERIC, val);
    }

    @Override
    public void onFirstTickInLife() {
        super.onFirstTickInLife();
        //no summoner now. not worked on
        setCombatClass(EnumCombatClass.values()[rand.nextInt(4)]);
    }


    @SubscribeEvent
    public static void onArrowImpact(ProjectileImpactEvent event)
    {
        Entity projectile = event.getEntity();
        if (!projectile.getEntityWorld().isRemote)
        {
            RayTraceResult rayTraceResult = event.getRayTraceResult();
            Entity hurtOne = rayTraceResult.entityHit;

            if (hurtOne instanceof EntityBossSteve &&
                    ((EntityGeneralMob) hurtOne).getHeldItemOffhand().getItem() instanceof ItemShield)
            {
                Vec3d vec3d = new Vec3d(projectile.motionX, projectile.motionY, projectile.motionZ);
                if (vec3d.dotProduct(hurtOne.getLookVec()) > 0)
                {
                    return;
                }

                boolean success = ((EntityLivingBase) hurtOne).getRNG().nextFloat() < ModConfig.SPECIAL_MOB_CONF.BOSS_DEFLECT_CHANCE;
                if (success)
                {
                    projectile.setVelocity(-projectile.motionX, -projectile.motionY, -projectile.motionZ);
                    event.setCanceled(true);
                    if (projectile instanceof EntityArrow)
                    {
                        if (((EntityArrow) projectile).shootingEntity instanceof EntityPlayer)
                        {
                            CommonFunctions.SafeSendMsgToPlayer((EntityPlayer) ((EntityArrow) projectile).shootingEntity,
                                    "adven_one.vocal.boss.steve.deflect");
                        }
                    }
                }
            }
        }
    }

    public void addTrackingPlayer(EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        CommonFunctions.SafeSendMsgToPlayer(player, "adven_one.vocal.boss.steve.birth");
    }

    @Override
    public void onKillEntity(EntityLivingBase entityLivingIn) {
        super.onKillEntity(entityLivingIn);
        CommonFunctions.SafeSendMsgToPlayer(entityLivingIn, "adven_one.vocal.boss.steve.kill");
    }

    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
        CommonFunctions.BroadCastByKey( "adven_one.vocal.boss.steve.death");
    }
}
