package com.deeplake.adven_one.recipe.traditional;

import com.deeplake.adven_one.item.suit.IHasModifers;
import com.deeplake.adven_one.item.suit.IHasQuality;
import com.deeplake.adven_one.item.suit.modifiers.Modifier;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;

import java.util.HashMap;
import java.util.Random;

public class RecipeShapedHasQuality extends ShapedRecipes {
    public RecipeShapedHasQuality(String group, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result) {
        super(group, width, height, ingredients, result);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        double sumQuality = 0;
        int count = 0;

        HashMap<Modifier, Integer> resultMap = new HashMap<>();

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

                if (stack.getItem() instanceof IHasModifers)
                {
                    IHasModifers iHasModifers = (IHasModifers) stack.getItem();
                    HashMap<Modifier, Integer> tempMap = iHasModifers.getAllFromNBT(stack);
                    for (Modifier modifier : tempMap.keySet())
                    {
                        int newLevel = tempMap.get(modifier);
                        if (resultMap.containsKey(modifier))
                        {
                            int oldLevel = resultMap.get(modifier);
                            resultMap.replace(modifier, oldLevel + newLevel);//todo: come up with a better algorithm
                        } else {
                            resultMap.put(modifier, newLevel);
                        }
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

        if (rawResult.getItem() instanceof IHasModifers && resultMap.keySet().size() > 0)
        {
            IHasModifers iHasModifers = (IHasModifers) rawResult.getItem();
            iHasModifers.storeAllToNBT(rawResult, resultMap);
        }

        return rawResult;
    }
}
