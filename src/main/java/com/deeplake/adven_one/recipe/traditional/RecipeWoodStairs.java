package com.deeplake.adven_one.recipe.traditional;

import com.deeplake.adven_one.util.CraftUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;

import java.util.Objects;

public class RecipeWoodStairs extends ShapedRecipes {

    public RecipeWoodStairs(Item plank, Item result) {
        super(CraftUtil.SUIT, 3, 3, NonNullList.create(), new ItemStack(result));
        Ingredient gemIng = Ingredient.fromItems(plank);

        recipeItems.add(gemIng);
        recipeItems.add(Ingredient.EMPTY);
        recipeItems.add(Ingredient.EMPTY);

        recipeItems.add(gemIng);
        recipeItems.add(gemIng);
        recipeItems.add(Ingredient.EMPTY);

        recipeItems.add(gemIng);
        recipeItems.add(gemIng);
        recipeItems.add(gemIng);

        setRegistryName(Objects.requireNonNull(result.getRegistryName()));
    }
}
