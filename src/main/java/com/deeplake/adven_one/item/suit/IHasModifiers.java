package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.item.suit.modifiers.EnumModifier;
import com.deeplake.adven_one.item.suit.modifiers.ModifierList;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTDef;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

import java.util.*;

import static com.deeplake.adven_one.item.suit.modifiers.EnumModifier.addToListFromConfig;

public interface IHasModifiers extends IHasInit{
    UUID GENERAL_MODIFIER = UUID.fromString("aaca48c6-1778-e791-a5f7-177594d7e699");
    public static final String NAME_IN = "Weapon modifier";
    int SHIFTER = 1000;
    default void setModifierLevel(ItemStack stack, EnumModifier modifier, int level)
    {
        if (level < 0)
        {
            return;
        }
        HashMap<EnumModifier, Integer> map = getAllFromNBT(stack);
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

    default int getModifierLevel(ItemStack stack, EnumModifier modifier)
    {
        int result = 0;
        HashMap<EnumModifier, Integer> map = getAllFromNBT(stack);
        if (map.containsKey(modifier))
        {
            return map.get(modifier);
        }
        return result;
    }

    default void storeAllToNBT(ItemStack stack, HashMap<EnumModifier, Integer> attrMap)
    {
        List<Integer> list = new ArrayList<>();
        for (EnumModifier modifier : attrMap.keySet())
        {
            list.add(modifier.getId() * SHIFTER + attrMap.get(modifier));
        }
        IDLNBTUtil.SetIntArray(stack, IDLNBTDef.KEY_MODIFIER_SEQ, Arrays.stream(list.toArray(new Integer[0])).mapToInt(Integer::valueOf).toArray());
    }

    default HashMap<EnumModifier, Integer> getAllFromNBT(ItemStack stack)
    {
        HashMap<EnumModifier, Integer> attrMap = new HashMap<>();

        int[] array = IDLNBTUtil.GetIntArray(stack, IDLNBTDef.KEY_MODIFIER_SEQ);

        for (int num:
                array) {
            if (num > 0)
            {
                int level = num % SHIFTER;
                int id = num / SHIFTER;

                EnumModifier modifier = ModifierList.getFromID(id);

                if (modifier == EnumModifier.BLANK)
                {
                    continue;
                }

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

    //make sure only merge this into correct slot.
    default Multimap<String, AttributeModifier> sharedAttributeModifiers(ItemStack stack)
    {
        Multimap<String, AttributeModifier> result = HashMultimap.create();
        HashMap<EnumModifier, Integer> attrMap = getAllFromNBT(stack);
        if (attrMap == null)
        {
            Idealland.LogWarning("Error: Null list");
        }
        else {
            addToListFromConfig(result, attrMap,
                    ModAttributes.ANTI_PRESSURE_EARTH,
                    EnumModifier.ANTI_PRESSURE_DEPTH,
                    ModConfig.MODIFIER_CONF.PRESSURE_DOWN_FIXED_GROUP,
                    ModConfig.EnumFixLevel.D);
        }

        return result;
    }


}
