package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.util.NBTStrDef.IDLNBTDef;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public interface IHasQuality {
    default boolean needFirstTick(ItemStack stack)
    {
        return IDLNBTUtil.GetBoolean(stack, IDLNBTDef.NEED_FIRST_TICK, true);
    }

    default boolean setNeedFirstTick(ItemStack stack)
    {
        return IDLNBTUtil.SetBoolean(stack, IDLNBTDef.NEED_FIRST_TICK, true);
    }

    default boolean canIdentify(ItemStack stack)
    {
        return false;
    }

    default void finishFirstTick(ItemStack stack)
    {
        IDLNBTUtil.SetBoolean(stack, IDLNBTDef.NEED_FIRST_TICK, false);
    }

    default void setQuality(ItemStack stack, double value)
    {
        IDLNBTUtil.SetDouble(stack, IDLNBTDef.QUALITY, value);
        finishFirstTick(stack);
    }

    default double getQuality(ItemStack stack)
    {
        return IDLNBTUtil.GetDouble(stack, IDLNBTDef.QUALITY, 1f);
    }

    default double getRandomQuality(ItemStack stack)
    {
        return IDLNBTUtil.GetDouble(stack, IDLNBTDef.QUALITY, 1f);
    }

    //not very good, as quality range may be configured. I don't have time for that now.
    default EnumRarity getRarityByQuality(ItemStack stack)
    {
        double quality = getQuality(stack);
        if (quality < 1)
        {
            return EnumRarity.COMMON;
        } else if (quality <= 1.1)
        {
            return EnumRarity.UNCOMMON;
        }else if (quality <= 1.25)
        {
            return EnumRarity.RARE;
        }else// if (quality <= 1.1)
        {
            return EnumRarity.EPIC;
        }
    }
}
