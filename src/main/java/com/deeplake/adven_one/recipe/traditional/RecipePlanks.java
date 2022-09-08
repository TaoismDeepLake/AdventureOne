package com.deeplake.adven_one.recipe.traditional;

import com.deeplake.adven_one.util.CraftUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;

import java.util.Objects;

public class RecipePlanks extends ShapedRecipes {

    public RecipePlanks(Item log, Item result) {
        super(CraftUtil.SUIT, 1, 1, NonNullList.create(), new ItemStack(result, 4));
        Ingredient gemIng = Ingredient.fromItems(log);

        recipeItems.add(gemIng);

        setRegistryName(Objects.requireNonNull(result.getRegistryName()));
    }
}
