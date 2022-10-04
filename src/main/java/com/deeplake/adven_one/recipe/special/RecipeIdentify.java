package com.deeplake.adven_one.recipe.special;

import com.deeplake.adven_one.item.IGuaEnhance;
import com.deeplake.adven_one.item.suit.IHasQuality;
import com.deeplake.adven_one.util.IDLGeneral;
import com.deeplake.adven_one.util.IDLSkillNBT;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public class RecipeIdentify extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
        boolean found = false;
        for(int i = 0; i < var1.getSizeInventory(); i++) {
            ItemStack stack = var1.getStackInSlot(i);
            if(stack.isEmpty()) {
               continue;
            }
            else {
                if (found)
                {
                    return false;
                }

                if (stack.getItem() instanceof IHasQuality)
                {
                    IHasQuality iHasQuality = (IHasQuality) stack.getItem();
                    if (iHasQuality.canIdentify(stack))
                    {
                        found = true;
                    }
                    else {
                        return false;
                    }
                }
                else
                {
                    return false;
                }
            }
        }
        return found;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if(stack.isEmpty()) {
                continue;
            }
            else {
                if (stack.getItem() instanceof IHasQuality)
                {
                    IHasQuality iHasQuality = (IHasQuality) stack.getItem();
                    ItemStack result = stack.copy();
                    result.setCount(1);
                    iHasQuality.setNeedFirstTick(result);
                    return result;
                }
                else
                {
                    return ItemStack.EMPTY;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }
}
