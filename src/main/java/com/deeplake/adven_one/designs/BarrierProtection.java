package com.deeplake.adven_one.designs;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.blocks.ModBlocks;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.util.CommonFunctions;
import com.deeplake.adven_one.util.PlayerUtil;
import com.deeplake.adven_one.world.dimension.DimensionMain;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class BarrierProtection {
    private static ModConfig.MeteorConf meteorConf;

    @SubscribeEvent
    public static void onPlayerTick(LivingEvent.LivingUpdateEvent event)
    {
        meteorConf = ModConfig.GENERAL_CONF.METEOR_CONF;
        if (meteorConf.ENABLE_METEOR_RAIN)
        {
            EntityLivingBase livingBase = event.getEntityLiving();
            if (livingBase instanceof EntityPlayer)
            {
                World world = livingBase.world;
                int meteorPeriod = meteorConf.METEOR_PERIOD;
                if (!world.isRemote
                        && world.getWorldTime() % meteorPeriod == 0
                        && world.provider instanceof DimensionMain)
                {
                    float remainCount = meteorConf.STANDARD_RATE;
                    BlockPos pos = livingBase.getPosition();
                    if (!world.canBlockSeeSky(pos))
                    {
                        remainCount -= meteorConf.COVERED_REDUCTION;
                    }

                    int seaLevel = world.getSeaLevel();
                    int y = pos.getY();
                    int delta = y - seaLevel;
                    double heightBonus = meteorConf.HEIGHT_BONUS;
                    if (seaLevel - y > 0)
                    {
                        remainCount += heightBonus * delta;
                    } else {
                        remainCount += heightBonus * delta * 2;
                    }

                    Random rng = livingBase.getRNG();
                    int cycleTimes = CommonFunctions.fromRandomFloat(remainCount, rng);
                    while (cycleTimes > 0)
                    {
                        summonMeteroidAround(livingBase.getPositionVector(), rng, world);
                        cycleTimes--;
                    }
                }
            }
        }
    }

    static void summonMeteroidAround(Vec3d pos, Random random, World world)
    {
        ModConfig.MeteorConf meteor_conf = ModConfig.GENERAL_CONF.METEOR_CONF;
        float maxRange = meteor_conf.METEOR_RADIUS;
        float range = random.nextFloat() * maxRange;
        float theta = 360 * random.nextFloat();

        float dy = 100f;

        Vec3d target = Vec3d.fromPitchYaw(0f, theta).scale(range).add(pos);
        EntityFireball fireball = new EntitySmallFireball(world,
                target.x, Math.max(target.y+dy, 260), target.z,
                0d, -meteor_conf.METEOR_RADIUS, 0d
        );
        world.spawnEntity(fireball);
    }

    public static int fullExploreCount(int tier, EntityPlayer player)
    {
        if (player == null)
        {
            return 0;
        }
        //todo: handle with advancements
        return player.getHeldItemOffhand().getCount();
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event)
    {
        EntityPlayer player = event.getPlayer();

        //bypass check
        if (PlayerUtil.isCreative(player))
        {
            return;
        }

        int tier = -1;
        if (event.getState().getBlock() == ModBlocks.BARRIER_EARTH)
        {
            tier = 2;

            if (fullExploreCount(tier, player) < ModConfig.TIER_CONF.EARTH_BARRIER_REQ_BIOME)
            {
                event.setCanceled(true);
            }
        } else if (event.getState().getBlock() == ModBlocks.BARRIER_SKY)
        {
            tier = 3;

            if (fullExploreCount(tier, player) < ModConfig.TIER_CONF.SKY_BARRIER_REQ_BIOME)
            {
                event.setCanceled(true);
            }
        }
    }
}
