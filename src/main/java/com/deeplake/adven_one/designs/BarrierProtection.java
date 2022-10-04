package com.deeplake.adven_one.designs;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.blocks.ModBlocks;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.util.CommonFunctions;
import com.deeplake.adven_one.util.EntityUtil;
import com.deeplake.adven_one.util.PlayerUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
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

    static int meteorPeriod = 3;

    @SubscribeEvent
    public static void onPlayerTick(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase livingBase = event.getEntityLiving();
        if (livingBase instanceof EntityPlayer)
        {

            World world = livingBase.world;
            if (!world.isRemote && world.getWorldTime() % meteorPeriod == 0)
            {
                float remainCount = 1f;
                BlockPos pos = livingBase.getPosition();
                if (!world.canBlockSeeSky(pos))
                {
                    remainCount -= 0.8f;
                }

                int seaLevel = world.getSeaLevel();
                int y = pos.getY();
                int delta = y - seaLevel;
                if (seaLevel - y > 0)
                {
                    remainCount += 0.2 * delta;
                } else {
                    remainCount += 0.4 * delta;
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

    static void summonMeteroidAround(Vec3d pos, Random random, World world)
    {
        float maxRange = 32f;
        float range = random.nextFloat() * maxRange;
        float theta = 360 * random.nextFloat();

        float dy = 100f;

        Vec3d target = Vec3d.fromPitchYaw(0f, theta).scale(range).add(pos);
        EntityFireball fireball = new EntitySmallFireball(world,
                target.x, target.y+dy, target.z,
                0d, -4, 0d
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
