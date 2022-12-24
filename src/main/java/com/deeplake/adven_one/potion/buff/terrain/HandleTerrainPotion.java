package com.deeplake.adven_one.potion.buff.terrain;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.potion.ModPotions;
import com.deeplake.adven_one.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class HandleTerrainPotion {
    static float damageRatioPerLevel = 0.25f;
    @SubscribeEvent
    public static void onHit(LivingHurtEvent evt)
    {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();
        int lv = EntityUtil.getBuffLevelIDL(hurtOne, ModPotions.TERRAIN_DEFENCE_UP) -
                EntityUtil.getBuffLevelIDL(hurtOne, ModPotions.TERRAIN_DEFENCE_DOWN);

        if (lv != 0)
        {
            evt.setAmount(evt.getAmount() * (1 + lv * damageRatioPerLevel));
        }
    }
}
