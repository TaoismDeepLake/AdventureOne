package com.deeplake.adven_one.recipe.misc;

import com.deeplake.adven_one.item.ModItems;
import com.deeplake.adven_one.util.CraftUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;

import java.util.Objects;

public class RecipeHandle extends ShapelessRecipes {

    public RecipeHandle() {
        super(CraftUtil.SUIT, new ItemStack(ModItems.MOD_STICK, 1), NonNullList.create());

        recipeItems.add(Ingredient.fromItems(Items.STICK));

        recipeItems.add(Ingredient.fromItems(ModItems.FIBRE));

        setRegistryName(Objects.requireNonNull(ModItems.MOD_STICK.getRegistryName()));
    }
}