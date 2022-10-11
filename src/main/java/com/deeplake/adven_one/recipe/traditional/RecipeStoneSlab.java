package com.deeplake.adven_one.recipe.traditional;

import com.deeplake.adven_one.util.CraftUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;

import java.util.Objects;

public class RecipeStoneSlab extends ShapedRecipes {

    public RecipeStoneSlab(Item stone, Item result) {
        super(CraftUtil.SUIT, 3, 1, NonNullList.create(), new ItemStack(result, 6));
        Ingredient gemIng = Ingredient.fromItems(stone);

        recipeItems.add(gemIng);
        recipeItems.add(gemIng);
        recipeItems.add(gemIng);

        setRegistryName(Objects.requireNonNull(result.getRegistryName()));
    }
}
