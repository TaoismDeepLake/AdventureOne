package com.deeplake.adven_one.item.suit.modifiers;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.item.suit.modifiers.types.EnumGeartype;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static com.deeplake.adven_one.item.suit.modifiers.ModifierList.ID_TO_ENUM;
import static com.deeplake.adven_one.item.suit.modifiers.ModifierList.MAX_LV;

public enum EnumModifier {

    BLANK(0, "blank"),
    ATK_UP(1, "atk_up", MAX_LV),
    HP_UP(2, "hp_up", MAX_LV),
    HARDNESS(3, "hardness", MAX_LV),
    EFFICIENCY_UP(4, "efficiency_up", MAX_LV),
    WEAPON_UP(5, "weapon_up", MAX_LV),
    KILL_CREEPER(101, "kill_creeper", MAX_LV),
    KILL_ENDERMAN(102, "kill_enderman", MAX_LV),
    KILL_PLAYER(103, "kill_player", MAX_LV),

    KILL_MELEE(104, "kill_melee", MAX_LV),
    KILL_BOW(105, "kill_bow", MAX_LV),
    KILL_PIG(106, "kill_pig"),
    KILL_COW(107, "kill_cow"),
    KILL_SHEEP(108, "kill_sheep"),
    KILL_CHICK(109, "kill_chick"),

    COST_SAVE(201, "cost_save"),
    COST_SAVE_SWORD(202, "cost_save_sword"),
    COST_SAVE_PICK(203, "cost_save_pick"),
    COST_SAVE_ARMOR(204, "cost_save_armor"),
    OVERLOAD_SWORD(205, "overload_sword"),
    OVERLOAD_PICK(206, "overload_pick"),
    OVERLOAD_ARMOR(207, "overload_armor"),


    ;

    final int id;
    final String name;
    final int maxLv;

    static final String NAME_KEY = Idealland.MODID + ".modifiers.%s.name";

    final HashSet<EnumGeartype> applicable = new HashSet<>();

    EnumModifier(int id, String name) {
        this(id, name, 1);
    }

    EnumModifier(int id, String name, int maxLv) {
        this.id = id;
        this.name = name;
        this.maxLv = maxLv;
        ID_TO_ENUM.put(id, this);
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
}