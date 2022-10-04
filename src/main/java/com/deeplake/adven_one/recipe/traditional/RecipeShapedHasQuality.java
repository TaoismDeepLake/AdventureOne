package com.deeplake.adven_one.recipe.traditional;

import com.deeplake.adven_one.item.suit.IHasModifiers;
import com.deeplake.adven_one.item.suit.IHasQuality;
import com.deeplake.adven_one.item.suit.modifiers.EnumModifier;
import com.deeplake.adven_one.item.suit.modifiers.IHasType;
import com.deeplake.adven_one.item.suit.modifiers.types.EnumGeartype;
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

                if (stack.getItem() instanceof IHasModifiers)
                {
                    IHasModifiers iHasModifiers = (IHasModifiers) stack.getItem();
                    HashMap<EnumModifier, Integer> tempMap = iHasModifiers.getAllFromNBT(stack);

                    EnumGeartype enumGeartype = EnumGeartype.ALL;
                    if (rawResult.getItem() instanceof IHasType)
                    {
                        enumGeartype = ((IHasType) rawResult.getItem()).getType(rawResult);
                    }

                    for (EnumModifier modifier : tempMap.keySet())
                    {
                        if (modifier.isApplicable(enumGeartype))
                        {
                            //todo: modifiers count limit
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
        }

        if (rawResult.getItem() instanceof IHasQuality && count > 0)
        {
            IHasQuality iHasQuality = (IHasQuality) rawResult.getItem();
            iHasQuality.setQuality(rawResult, sumQuality/count);
        }

        if (rawResult.getItem() instanceof IHasModifiers && resultMap.keySet().size() > 0)
        {
            IHasModifiers iHasModifiers = (IHasModifiers) rawResult.getItem();
            iHasModifiers.storeAllToNBT(rawResult, resultMap);
        }

        return rawResult;
    }
}
