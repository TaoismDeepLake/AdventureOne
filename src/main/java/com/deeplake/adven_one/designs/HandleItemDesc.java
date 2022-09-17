package com.deeplake.adven_one.designs;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.item.suit.IHasQuality;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class HandleItemDesc {
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
                    event.getToolTip().add(I18n.format("adven_one.shared.quality_desc_bad", String.format("%.2f", quality) ));
                }else {
                    event.getToolTip().add(I18n.format("adven_one.shared.quality_desc_good", String.format("%.2f", quality) ));
                }


            }
        }
    }
}
