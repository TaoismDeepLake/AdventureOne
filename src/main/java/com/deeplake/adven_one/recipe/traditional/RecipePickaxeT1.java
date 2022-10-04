package com.deeplake.adven_one.recipe.traditional;

import com.deeplake.adven_one.util.CraftUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Objects;

public class RecipePickaxeT1 extends RecipeShapedHasQuality {

    public RecipePickaxeT1(Item gem, Item result, Item dirt) {
        super(CraftUtil.SUIT, 3, 3, NonNullList.create(), new ItemStack(result));
        Ingredient gemIng = Ingredient.fromItems(gem);
        Ingredient stickIng = CraftUtil.create(OreDictionary.getOres(CraftUtil.STICK_WOOD));
        Ingredient dirtIng = Ingredient.fromItems(dirt);

        recipeItems.add(gemIng);
        recipeItems.add(gemIng);
        recipeItems.add(gemIng);

        recipeItems.add(dirtIng);
        recipeItems.add(stickIng);
        recipeItems.add(dirtIng);

        recipeItems.add(dirtIng);
        recipeItems.add(stickIng);
        recipeItems.add(dirtIng);

        setRegistryName(result.getRegistryName()+"sp");
    }
}
