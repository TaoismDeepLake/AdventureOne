package com.deeplake.adven_one.designs;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.world.dimension.DimensionMain;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class WorldPressure {

    static class DataGroup {
        public DataGroup(ModConfig.WorldPressureConf conf, double dy) {
            this.conf = conf;
            this.dy = dy;
        }

        public ModConfig.WorldPressureConf conf;
        public double dy;
    }

    public static DataGroup getPressureFactor(EntityLivingBase entityLiving)
    {
        float y = entityLiving.getPosition().getY();
        double dy;
        if (y >= ModConfig.GENERAL_CONF.CONF_UP.Y_START)
        {
            float anti_pressure = (float) entityLiving.getEntityAttribute(ModAttributes.ANTI_PRESSURE_EARTH).getAttributeValue();
            dy = y - ModConfig.GENERAL_CONF.CONF_UP.Y_START - anti_pressure;
            if (dy > 0) {
                return new DataGroup(ModConfig.GENERAL_CONF.CONF_UP, dy);
            }
        } else if (y <= ModConfig.GENERAL_CONF.CONF_DOWN.Y_START)
        {
            float anti_pressure = (float) entityLiving.getEntityAttribute(ModAttributes.ANTI_PRESSURE_SKY).getAttributeValue();
            dy = ModConfig.GENERAL_CONF.CONF_UP.Y_START - y - anti_pressure;
            if (dy > 0) {
                return new DataGroup(ModConfig.GENERAL_CONF.CONF_DOWN, dy);
            }
        }
        return null;
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onPlayerHurt(LivingHurtEvent event)
    {
        if (event.getEntityLiving() instanceof EntityPlayer)
        {
            EntityLivingBase entityLiving = event.getEntityLiving();
            World world = entityLiving.world;
            if (world.provider instanceof DimensionMain)
            {
                double factor = 0;
                DataGroup dataGroup = getPressureFactor(entityLiving);
                if (dataGroup != null)
                {
                    factor = dataGroup.dy * dataGroup.conf.DAMAGE_TAKEN_UP_RATIO / 100;
                }
                event.setAmount((float) (event.getAmount() * (1 + factor)));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void digSpeed(PlayerEvent.BreakSpeed event)
    {
        EntityPlayer player = event.getEntityPlayer();
        if (player != null)
        {
            World world = player.world;
            if (world.provider instanceof DimensionMain)
            {
                double factor = 0;
                DataGroup dataGroup = getPressureFactor(player);
                if (dataGroup != null)
                {
                    factor = dataGroup.dy * dataGroup.conf.DIG_TIME_UP_RATIO / 100;
                }
                event.setNewSpeed((float) (event.getNewSpeed() / (1 + factor)));
            }
        }
    }
}
