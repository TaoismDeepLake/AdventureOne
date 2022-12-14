package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTDef;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public interface IHasQuality extends IHasInit {

    default void setQuality(ItemStack stack, double value)
    {
        IDLNBTUtil.SetDouble(stack, IDLNBTDef.QUALITY, value);
        finishFirstTick(stack);
    }

    default double getQuality(ItemStack stack)
    {
        return IDLNBTUtil.GetDouble(stack, IDLNBTDef.QUALITY, ModConfig.TIER_CONF.UNIDENTIFIED_QUALITY);
    }

    default double getRandomQuality(ItemStack stack)
    {
        return IDLNBTUtil.GetDouble(stack, IDLNBTDef.QUALITY, ModConfig.TIER_CONF.UNIDENTIFIED_QUALITY);
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

    //todo: add min max interface here.
    default double getMinQuality(ItemStack stack)
    {
        return 0;
    }

    default double getMaxQuality(ItemStack stack)
    {
        return 1.5;
    }
}
