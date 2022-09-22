package com.deeplake.adven_one.designs;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.blocks.ModBlocks;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.util.CommonFunctions;
import com.deeplake.adven_one.util.EntityUtil;
import com.deeplake.adven_one.util.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class BarrierProtection {


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
