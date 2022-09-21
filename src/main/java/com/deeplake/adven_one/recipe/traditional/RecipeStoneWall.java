package com.deeplake.adven_one.recipe.traditional;

import com.deeplake.adven_one.util.CraftUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Objects;

public class RecipeStoneWall extends ShapedRecipes {

    public RecipeStoneWall(Item stone, Item result) {
        super(CraftUtil.SUIT, 3, 2, NonNullList.create(), new ItemStack(result, 6));
        Ingredient gemIng = Ingredient.fromItems(stone);

        recipeItems.add(gemIng);
        recipeItems.add(gemIng);
        recipeItems.add(gemIng);

        recipeItems.add(gemIng);
        recipeItems.add(gemIng);
        recipeItems.add(gemIng);

        setRegistryName(Objects.requireNonNull(result.getRegistryName()));
    }
}
