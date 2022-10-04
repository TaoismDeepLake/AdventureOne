package com.deeplake.adven_one.designs;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.item.suit.IHasModifiers;
import com.deeplake.adven_one.item.suit.IHasQuality;
import com.deeplake.adven_one.item.suit.modifiers.Modifier;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class HandleItemDesc {
    static final String MODIFIER_LEVEL_FORMAT = Idealland.MODID + ".desc.modifier";

    @SubscribeEvent
    public static void onToolTip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof IHasQuality)
        {
            IHasQuality iHasQuality = (IHasQuality) stack.getItem();
            if (iHasQuality.needFirstTick(stack))
            {
                event.getToolTip().add(I18n.format("adven_one.shared.quality_random"));
            }
            else {
                double quality = iHasQuality.getQuality(stack);
                if (quality < 1.0f)
                {
                    event.getToolTip().add(I18n.format("adven_one.shared.quality_desc_bad", String.format("%.2f", quality * 100)));
                }else {
                    event.getToolTip().add(I18n.format("adven_one.shared.quality_desc_good", String.format("%.2f", quality * 100)));
                }
            }
        }

        if (stack.getItem() instanceof IHasModifiers) {
            IHasModifiers iHasModifiers = (IHasModifiers) stack.getItem();
            if (iHasModifiers.needFirstTick(stack)) {
                //event.getToolTip().add(I18n.format("adven_one.shared.quality_random"));
            }
            else
            {
                HashMap<Modifier, Integer> map = iHasModifiers.getAllFromNBT(stack);
                for (Modifier modifier : map.keySet())
                {
                    if (modifier.getMaxLv() == 1)
                    {
                        event.getToolTip().add(I18n.format(modifier.getLangKey()));
                    }
                    else {
                        int lv = map.get(modifier);
                        event.getToolTip().add(
                                I18n.format(MODIFIER_LEVEL_FORMAT,
                                    I18n.format(modifier.getLangKey()), lv)
                        );
                    }
                }
            }
        }
    }
}
