package com.deeplake.adven_one.item.suit.modifiers;

import com.deeplake.adven_one.item.suit.modifiers.types.EnumGeartype;

import java.util.HashMap;

public class ModifierList {
    static HashMap<Integer, Modifier> ID_TO_ENUM = new HashMap<>();

    public static final int MAX_LV = 99;

    {
        //Init modifier gear types
        Modifier.BLANK.addGearTypesAll();
        Modifier.ATK_UP.addGearTypes(EnumGeartype.SWORD);
        Modifier.HP_UP.addGearTypesAllArmor();
        Modifier.HARDNESS.addGearTypesAll();
    }

    public static Modifier getFromID(int id)
    {
        Modifier result = Modifier.BLANK;
        if (ID_TO_ENUM.containsKey(id))
        {
            result = ID_TO_ENUM.get(id);
        }
        return result;
    }

}
