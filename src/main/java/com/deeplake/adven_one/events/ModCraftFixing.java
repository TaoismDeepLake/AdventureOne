package com.deeplake.adven_one.events;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.item.suit.IHasModifiers;
import com.deeplake.adven_one.item.suit.modifiers.EnumFeature;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTDef;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ModCraftFixing {

    @SubscribeEvent
    public static void giveExtra(PlayerEvent.ItemCraftedEvent event)
    {
        ItemStack result = event.crafting;
        IInventory inv = event.craftMatrix;
        int extraCount = 0;
        boolean maxCount = false;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof IHasModifiers)
                {

                    IHasModifiers iHasModifiers = (IHasModifiers) stack.getItem();
                    HashMap<EnumFeature, Integer> tempMap = iHasModifiers.getAllFeatureFromNBT(stack);

                    if (tempMap.containsKey(EnumFeature.EXTRA_COUNT))
                    {
                        extraCount += tempMap.get(EnumFeature.EXTRA_COUNT);
                    }

                    if (tempMap.containsKey(EnumFeature.MAX_COUNT))
                    {
                        maxCount = true;
                    }
                }
            }
        }

        int count = extraCount;
        int maxStackSize = result.getMaxStackSize();
//        int count = IDLNBTUtil.GetInt(result, IDLNBTDef.EXTRA_COUNT_NBT);
        if (count > 0)
        {
            IDLNBTUtil.SetInt(result, IDLNBTDef.EXTRA_COUNT_NBT, 0);
            for (int i = count; i > 0;) {
                ItemStack stack = result.copy();

                if (i > maxStackSize)
                {
                    stack.setCount(i);
                    break;
                }
                else {
                    stack.setCount(maxStackSize);
                    i -= maxStackSize;
                }
                event.player.dropItem(stack, false);
            }
        }

        if (maxCount)
        {
            result.setCount(maxStackSize);
        }
    }
}
