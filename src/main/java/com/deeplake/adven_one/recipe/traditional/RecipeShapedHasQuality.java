package com.deeplake.adven_one.recipe.traditional;

import com.deeplake.adven_one.item.suit.IHasQuality;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;

import java.util.Random;

public class RecipeShapedHasQuality extends ShapedRecipes {
    public RecipeShapedHasQuality(String group, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result) {
        super(group, width, height, ingredients, result);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        Random random = new Random();
        double sumQuality = 0;
        int count = 0;

        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack.isEmpty()) {
                continue;
            }
            else {
                if (stack.getItem() instanceof IHasQuality)
                {
                    count++;
                    IHasQuality iHasQuality = (IHasQuality) stack.getItem();
                    if (iHasQuality.needFirstTick(stack))
                    {
                        sumQuality += iHasQuality.getRandomQuality(stack);
                    }
                    else {
                        sumQuality += iHasQuality.getQuality(stack);
                    }
                }
            }
        }

        ItemStack rawResult = super.getCraftingResult(inv);
        if (rawResult.getItem() instanceof IHasQuality && count > 0)
        {
            IHasQuality iHasQuality = (IHasQuality) rawResult.getItem();
            iHasQuality.setQuality(rawResult, sumQuality/count);
        }

        return rawResult;
    }
}
