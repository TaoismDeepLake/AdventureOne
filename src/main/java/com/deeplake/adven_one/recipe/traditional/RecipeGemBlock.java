package com.deeplake.adven_one.recipe.traditional;

import com.deeplake.adven_one.util.CraftUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;

import java.util.Objects;

public class RecipeGemBlock extends ShapedRecipes {

    public RecipeGemBlock(Item gem, Item result) {
        super(CraftUtil.SUIT, 2, 2, NonNullList.create(), new ItemStack(result, 2));
        Ingredient gemIng = Ingredient.fromItems(gem);

        recipeItems.add(gemIng);
        recipeItems.add(gemIng);

        recipeItems.add(gemIng);
        recipeItems.add(gemIng);

        setRegistryName(Objects.requireNonNull(result.getRegistryName()));
    }
}
