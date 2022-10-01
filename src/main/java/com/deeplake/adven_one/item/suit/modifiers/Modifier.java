package com.deeplake.adven_one.item.suit.modifiers;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.item.suit.modifiers.types.EnumGeartype;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static com.deeplake.adven_one.item.suit.modifiers.ModifierList.ID_TO_ENUM;
import static com.deeplake.adven_one.item.suit.modifiers.ModifierList.MAX_LV;

public enum Modifier {

    BLANK(0, "blank"),
    ATK_UP(1, "atk_up", MAX_LV),
    HP_UP(2, "hp_up", MAX_LV),
    HARDNESS(3, "hardness", MAX_LV);

    final int id;
    final String name;
    final int maxLv;

    static final String NAME_KEY = Idealland.MODID + ".modifiers.%s.name";

    final HashSet<EnumGeartype> applicable = new HashSet<>();

    Modifier(int id, String name) {
        this.id = id;
        this.name = name;
        maxLv = 1;

        ID_TO_ENUM.put(id, this);
    }

    Modifier(int id, String name, int maxLv) {
        this.id = id;
        this.name = name;
        this.maxLv = maxLv;
    }

    public void addGearTypes(EnumGeartype... gearTypes)
    {
        Collections.addAll(applicable, gearTypes);
    }

    public void addGearTypesAll()
    {
        applicable.addAll(Arrays.asList(EnumGeartype.values()));
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

    public int getId() {
        return id;
    }

    public int getMaxLv() {
        return maxLv;
    }

    public int getClampedLevel(int rawLevel) {
        if (rawLevel < 0)
        {
            return 0;
        }

        return Math.min(rawLevel, maxLv);

    }

    public String getLangKey()
    {
        return String.format(NAME_KEY, name);
    }
}
