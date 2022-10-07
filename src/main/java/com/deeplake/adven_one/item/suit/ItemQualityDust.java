package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.item.ItemBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ItemQualityDust extends ItemBase implements IHasQuality {
    public ItemQualityDust(String name) {
        super(name);
    }

    //right one is dust.
    @SubscribeEvent
    public static void onAnvil(AnvilUpdateEvent event)
    {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (left.getItem() instanceof IHasQuality && right.getItem() instanceof ItemQualityDust)
        {
            IHasQuality _left = (IHasQuality) left.getItem();

            ItemStack result = left.copy();
            double quality = _left.getQuality(left);

            int rightCount = right.getCount();

            quality += rightCount * ModConfig.QUALITY_CONF.QUALITY_PER_DUST;

            _left.setQuality(result, Math.min(quality, _left.getMaxQuality(result)));

            event.setMaterialCost(rightCount);
            event.setCost(rightCount);

            event.setOutput(result);

        }
    }
}
