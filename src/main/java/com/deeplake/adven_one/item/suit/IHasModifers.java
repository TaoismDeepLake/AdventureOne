package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.item.suit.modifiers.Modifier;
import com.deeplake.adven_one.item.suit.modifiers.ModifierList;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTDef;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderLivingEvent;

import java.util.*;

public interface IHasModifers extends IHasInit{
    int SHIFTER = 1000;
    default void setModifierLevel(ItemStack stack, Modifier modifier, int level)
    {
        if (level < 0)
        {
            return;
        }
        HashMap<Modifier, Integer> map = getAllFromNBT(stack);
        if (map.containsKey(modifier))
        {
            if (level == 0)
            {
                map.remove(modifier);
            }
            else {
                map.replace(modifier, level);
            }
        }
        else {
            if (level > 0)
            {
                map.put(modifier, level);
            }
        }
    }

    default int getModifierLevel(ItemStack stack, Modifier modifier)
    {
        int result = 0;
        HashMap<Modifier, Integer> map = getAllFromNBT(stack);
        if (map.containsKey(modifier))
        {
            return map.get(modifier);
        }
        return result;
    }

    default void storeAllToNBT(ItemStack stack, HashMap<Modifier, Integer> attrMap)
    {
        List<Integer> list = new ArrayList<>();
        for (Modifier modifier : attrMap.keySet())
        {
            list.add(modifier.getId() * SHIFTER + attrMap.get(modifier));
        }
        IDLNBTUtil.SetIntArray(stack, IDLNBTDef.KEY_MODIFIER_SEQ, Arrays.stream(list.toArray(new Integer[0])).mapToInt(Integer::valueOf).toArray());
    }

    default HashMap<Modifier, Integer> getAllFromNBT(ItemStack stack)
    {
        HashMap<Modifier, Integer> attrMap = new HashMap<>();

        int[] array = IDLNBTUtil.GetIntArray(stack, IDLNBTDef.KEY_MODIFIER_SEQ);

        for (int num:
                array) {
            if (num > 0)
            {
                int level = num % SHIFTER;
                int id = num / SHIFTER;

                Modifier modifier = ModifierList.getFromID(id);

                if (!attrMap.containsKey(modifier))
                {
                    attrMap.put(modifier, level);
                }
                else {
                    Idealland.LogWarning("Detected Duplicate Modifier on %s", stack);
                }
            }
        }

        return attrMap;
    }
}
