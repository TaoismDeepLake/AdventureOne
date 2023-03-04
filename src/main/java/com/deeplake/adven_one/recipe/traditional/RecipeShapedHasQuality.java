package com.deeplake.adven_one.recipe.traditional;

import com.deeplake.adven_one.item.suit.IHasModifiers;
import com.deeplake.adven_one.item.suit.IHasQuality;
import com.deeplake.adven_one.item.suit.modifiers.EnumModifier;
import com.deeplake.adven_one.item.suit.modifiers.ModifierList;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;

import java.util.HashMap;

public class RecipeShapedHasQuality extends ShapedRecipes {
    public RecipeShapedHasQuality(String group, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result) {
        super(group, width, height, ingredients, result);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        double sumQuality = 0;
        int count = 0;

        HashMap<EnumModifier, Integer> resultMap = new HashMap<>();

        ItemStack rawResult = super.getCraftingResult(inv);

        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
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

                if (stack.getItem() instanceof IHasModifiers)
                {
                    IHasModifiers iHasModifiers = (IHasModifiers) stack.getItem();
                    HashMap<EnumModifier, Integer> tempMap = iHasModifiers.getAllModiFromNBT(stack);

                    ModifierList.mergeModifiersToStack(resultMap, rawResult, tempMap);
                }
            }
        }

        if (rawResult.getItem() instanceof IHasQuality && count > 0)
        {
            IHasQuality iHasQuality = (IHasQuality) rawResult.getItem();
            iHasQuality.setQuality(rawResult, sumQuality/count);
        }

        return rawResult;
    }
}
