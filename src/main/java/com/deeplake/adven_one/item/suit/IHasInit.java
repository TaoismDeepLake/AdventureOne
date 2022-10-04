package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.util.NBTStrDef.IDLNBTDef;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.item.ItemStack;

public interface IHasInit {
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

}
