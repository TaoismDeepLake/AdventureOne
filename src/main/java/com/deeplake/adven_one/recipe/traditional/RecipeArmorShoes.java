package com.deeplake.adven_one.recipe.traditional;

import com.deeplake.adven_one.util.CraftUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;

import java.util.Objects;

import static net.minecraft.item.crafting.Ingredient.EMPTY;

public class RecipeArmorShoes extends RecipeShapedHasQuality {

    public RecipeArmorShoes(Item gem, Item result) {
        super(CraftUtil.SUIT, 3, 2, NonNullList.create(), new ItemStack(result));
        Ingredient gemIng = Ingredient.fromItems(gem);

        recipeItems.add(gemIng);
        recipeItems.add(EMPTY);
        recipeItems.add(gemIng);

        recipeItems.add(gemIng);
        recipeItems.add(EMPTY);
        recipeItems.add(gemIng);

        setRegistryName(Objects.requireNonNull(result.getRegistryName()));
    }
}
