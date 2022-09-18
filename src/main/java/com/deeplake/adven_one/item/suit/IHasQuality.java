package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.util.NBTStrDef.IDLNBTDef;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public interface IHasQuality {
    public default boolean needFirstTick(ItemStack stack)
    {
        return IDLNBTUtil.GetBoolean(stack, IDLNBTDef.NEED_FIRST_TICK, true);
    }

    public default void finishFirstTick(ItemStack stack)
    {
        IDLNBTUtil.SetBoolean(stack, IDLNBTDef.NEED_FIRST_TICK, false);
    }

    public default void setQuality(ItemStack stack, double value)
    {
        IDLNBTUtil.SetDouble(stack, IDLNBTDef.QUALITY, value);
        finishFirstTick(stack);
    }

    public default double getQuality(ItemStack stack)
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
