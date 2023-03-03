package com.deeplake.adven_one.item.suit.modifiers;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.item.suit.modifiers.types.EnumGeartype;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import static com.deeplake.adven_one.item.suit.IHasModifiers.GENERAL_MODIFIER;
import static com.deeplake.adven_one.item.suit.IHasModifiers.NAME_IN;
import static com.deeplake.adven_one.item.suit.modifiers.ModifierList.*;

public enum EnumFeature {

    BLANK(0, "blank"),
    ATK_UP(1, "atk_up", MAX_LV),
    ;

    final int id;
    final String name;
    final int maxLv;

    static final String NAME_KEY = Idealland.MODID + ".features.%s.name";

    final HashSet<EnumGeartype> applicable = new HashSet<>();

    EnumFeature(int id, String name) {
        this(id, name, 1);
    }

    EnumFeature(int id, String name, int maxLv) {
        this.id = id;
        this.name = name;
        this.maxLv = maxLv;
        ID_TO_FEAT.put(id, this);
        applicable.add(EnumGeartype.ALL);
    }

    public void addGearTypes(EnumGeartype... gearTypes)
    {
        Collections.addAll(applicable, gearTypes);
    }

    public void addGearTypesSword()
    {
        Collections.addAll(applicable, EnumGeartype.SWORD);
    }

    public void addGearTypesPickaxe()
    {
        Collections.addAll(applicable, EnumGeartype.PICKAXE);
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

    public static void addToListFromConfig(Multimap<String, AttributeModifier> result, HashMap<EnumFeature, Integer> attrMap,
                                           IAttribute attribute, EnumFeature modifier, ModConfig.ModifierConfGroup confGroup, ModConfig.EnumFixLevel enumFixLevel) {
        result.put(attribute.getName(),
                new AttributeModifier(GENERAL_MODIFIER,
                        NAME_IN,
                        attrMap.getOrDefault(modifier, 0) * confGroup.getByLevel(enumFixLevel),
                        0));
    }
}
