package com.deeplake.adven_one.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class CraftUtil {
    public static final String STICK_WOOD = "stickWood";
    public static final String SUIT = "suit";

    public static Ingredient create(List<ItemStack> stacks)
    {
         ArrayList<Ingredient> list = new ArrayList<Ingredient>();
         for (ItemStack stack : stacks)
         {
              Ingredient adding = Ingredient.fromStacks(stack);
              list.add(adding);
         }
         return Ingredient.merge(list);
    }
}
