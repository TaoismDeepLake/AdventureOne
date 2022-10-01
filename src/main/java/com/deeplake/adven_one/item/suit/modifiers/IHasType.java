package com.deeplake.adven_one.item.suit.modifiers;

import com.deeplake.adven_one.item.suit.modifiers.types.EnumGeartype;
import net.minecraft.item.ItemStack;

public interface IHasType {
    EnumGeartype getType(ItemStack stack);
}
