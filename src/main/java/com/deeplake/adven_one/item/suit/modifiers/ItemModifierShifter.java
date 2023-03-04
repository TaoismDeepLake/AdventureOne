package com.deeplake.adven_one.item.suit.modifiers;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.item.ItemBase;
import com.deeplake.adven_one.item.suit.IHasModifiers;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ItemModifierShifter extends ItemBase implements IHasModifiers {
    public ItemModifierShifter(String name) {
        super(name);
    }

    @SubscribeEvent
    public static void onAnvil(AnvilUpdateEvent event)
    {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (left.getItem() instanceof IHasModifiers && right.getItem() instanceof IHasModifiers)
        {
            IHasModifiers _left = (IHasModifiers) left.getItem();
            IHasModifiers _right = (IHasModifiers) right.getItem();

            if (_left instanceof ItemModifierShifter || _right instanceof ItemModifierShifter)
            {
                ItemStack result = left.copy();
                HashMap<EnumModifier, Integer> resultMap = _left.getAllModiFromNBT(result);
                ModifierList.mergeModifiersToStack(resultMap, result, _right.getAllModiFromNBT(right));
                _left.storeAllModiToNBT(result, resultMap);

                event.setMaterialCost(1);
                event.setCost(resultMap.size());

                event.setOutput(result);
            }
        }
    }
}
