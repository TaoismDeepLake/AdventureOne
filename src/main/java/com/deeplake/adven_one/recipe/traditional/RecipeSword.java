package com.deeplake.adven_one.recipe.traditional;

import com.deeplake.adven_one.util.CraftUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Objects;

import static com.deeplake.adven_one.util.CraftUtil.SUIT;

public class RecipeSword extends ShapedRecipes {

    public RecipeSword(Item gem, Item result) {
        super(SUIT, 1, 3, NonNullList.create(), new ItemStack(result));
        Ingredient gemIng = Ingredient.fromItems(gem);
        Ingredient stickIng = CraftUtil.create(OreDictionary.getOres(CraftUtil.STICK_WOOD));

        recipeItems.add(gemIng);
        recipeItems.add(gemIng);
        recipeItems.add(stickIng);

        setRegistryName(Objects.requireNonNull(result.getRegistryName()));
    }
}
