package com.deeplake.adven_one.item.suit;

import net.minecraft.item.ItemStack;

public interface IHasCost {
    int getCost(ItemStack stack);
}
