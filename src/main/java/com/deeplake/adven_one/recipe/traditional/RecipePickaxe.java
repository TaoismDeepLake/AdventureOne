package com.deeplake.adven_one.recipe.traditional;

import com.deeplake.adven_one.item.ModItems;
import com.deeplake.adven_one.item.suit.IHasQuality;
import com.deeplake.adven_one.util.CraftUtil;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Objects;
import java.util.Random;

public class RecipePickaxe extends RecipeShapedHasQuality {

    private ItemStack result;

    public RecipePickaxe(Item gem, Item result) {
        super(CraftUtil.SUIT, 3, 3, NonNullList.create(), new ItemStack(result));
        Ingredient gemIng = Ingredient.fromItems(gem);
        Ingredient stickIng = Ingredient.fromItems(ModItems.MOD_STICK);

        recipeItems.add(gemIng);
        recipeItems.add(gemIng);
        recipeItems.add(gemIng);

        recipeItems.add(Ingredient.EMPTY);
        recipeItems.add(stickIng);
        recipeItems.add(Ingredient.EMPTY);

        recipeItems.add(Ingredient.EMPTY);
        recipeItems.add(stickIng);
        recipeItems.add(Ingredient.EMPTY);

        setRegistryName(Objects.requireNonNull(result.getRegistryName()));

        this.result = new ItemStack(result);
    }

    @Override
    public ItemStack getRecipeOutput() {
        return result;
    }
}
