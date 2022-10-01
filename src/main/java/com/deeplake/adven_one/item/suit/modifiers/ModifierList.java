package com.deeplake.adven_one.item.suit.modifiers;

import java.util.HashMap;

public class ModifierList {
    static HashMap<Integer, Modifier> ID_TO_ENUM = new HashMap<>();

    {
        //Init modifier gear types
        Modifier.BLANK.addGearTypesAll();
    }

}
