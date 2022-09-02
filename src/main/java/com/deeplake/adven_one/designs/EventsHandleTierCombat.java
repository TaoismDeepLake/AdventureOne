package com.deeplake.adven_one.designs;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EventsHandleTierCombat {

    @SubscribeEvent
    public static void onCombat(LivingHurtEvent event)
    {
        DamageSource source = event.getSource();
        if (source.getTrueSource() instanceof EntityLivingBase)
        {
            EntityLivingBase livingBase = (EntityLivingBase) source.getTrueSource();
            double atkTier = livingBase.getEntityAttribute(ModAttributes.ATK_TIER).getAttributeValue();
            double defTier = event.getEntityLiving().getEntityAttribute(ModAttributes.DEF_TIER).getAttributeValue();

            double delta = atkTier - defTier;
            if (delta > -0.01)
            {
                event.setAmount((float) ((1+delta) * event.getAmount()));
                source.setDamageBypassesArmor();
                event.getEntityLiving().hurtResistantTime = 0;
            }
            else {
                event.setAmount(1f);
            }
        }
    }

    @SubscribeEvent
    public static void onCombat(LivingKnockBackEvent event)
    {
        if (event.getAttacker() instanceof EntityLivingBase)
        {
            EntityLivingBase livingBase = (EntityLivingBase) event.getAttacker();
            double atkTier = livingBase.getEntityAttribute(ModAttributes.ATK_TIER).getAttributeValue();
            double defTier = event.getEntityLiving().getEntityAttribute(ModAttributes.DEF_TIER).getAttributeValue();

            double delta = atkTier - defTier;
            if (delta > -0.01)
            {

            }
            else {
                event.setCanceled(true);
            }
        }
    }
}
