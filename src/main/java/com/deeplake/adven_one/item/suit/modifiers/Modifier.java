package com.deeplake.adven_one.item.suit.modifiers;

import com.deeplake.adven_one.item.suit.modifiers.types.EnumGeartype;

import java.util.HashSet;

import static com.deeplake.adven_one.item.suit.modifiers.ModifierList.ID_TO_ENUM;

public enum Modifier {
    BLANK(0, "blank");

    int id;
    String name;

    HashSet<EnumGeartype> applicable = new HashSet<>();

    Modifier(int id, String name) {
        this.id = id;
        this.name = name;

        ID_TO_ENUM.put(id, this);
    }

    public void addGearTypes(EnumGeartype... gearTypes)
    {
        for (EnumGeartype type : gearTypes)
        {
            applicable.add(type);
        }
    }

    public void addGearTypesAll()
    {
        for (EnumGeartype type : EnumGeartype.values())
        {
            applicable.add(type);
        }
    }

    public void addGearTypesAllArmor()
    {
        for (EnumGeartype type : EnumGeartype.values())
        {
            if (type.isArmor())
            applicable.add(type);
        }
    }

    public boolean isApplicable(EnumGeartype gearType)
    {
        return applicable.contains(gearType);
    }

}
