package com.deeplake.adven_one.item.suit.modifiers;

import net.minecraft.util.WeightedRandom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModifierPool {
    public static class ModifierGroup extends WeightedRandom.Item
    {
        public EnumModifier modifier;
        public int minLevel = 1;
        public int maxLevel = 1;

        public ModifierGroup(int itemWeightIn, EnumModifier modifier) {
            this(itemWeightIn,modifier,1,1);
        }

        public ModifierGroup(int itemWeightIn, EnumModifier modifier, int minLevel, int maxLevel) {
            super(itemWeightIn);
            this.modifier = modifier;
            this.minLevel = minLevel;
            this.maxLevel = maxLevel;
        }
    }

    //It is about quality and harvest level, not about item type itself.
    public static HashMap<Integer, List<ModifierGroup>> modifierLists = new HashMap<>();

    static final int W_NORMAL_2 = 200;
    static final int W_NORMAL = 100;
    static final int W_HALF = 50;

    public static void initModifierPool()
    {
        List<ModifierGroup> list = new ArrayList<>();
        add(list,W_NORMAL * 10,EnumModifier.BLANK);
        add(list,W_NORMAL,EnumModifier.KILL_CHICK);
        add(list,W_NORMAL,EnumModifier.KILL_PIG);
        add(list,W_NORMAL,EnumModifier.KILL_SHEEP);
        add(list,W_NORMAL,EnumModifier.KILL_COW);
        add(list,W_NORMAL,EnumModifier.KILL_ENDERMAN,3);
        add(list,W_NORMAL,EnumModifier.KILL_CREEPER,3);
        add(list,W_NORMAL,EnumModifier.KILL_PLAYER,3);
        add(list,W_NORMAL,EnumModifier.ATK_UP);
        add(list,W_NORMAL,EnumModifier.HP_UP);
        add(list,W_NORMAL,EnumModifier.HARDNESS);
        add(list,W_NORMAL,EnumModifier.EFFICIENCY_UP);
        add(list,W_NORMAL,EnumModifier.WEAPON_UP);

        modifierLists.put(1,list);

        List<ModifierGroup> listPrev = list;
        list = new ArrayList<>();
        for (int i = 0; i < listPrev.size(); i++) {
            add(list, listPrev.get(i).itemWeight, listPrev.get(i).modifier);
        }

        add(list,W_NORMAL_2,EnumModifier.ATK_UP_2);
        add(list,W_NORMAL_2,EnumModifier.HP_UP_2);
        add(list,W_NORMAL_2,EnumModifier.COST_SAVE);
        add(list,W_NORMAL_2,EnumModifier.COST_SAVE_SWORD);
        add(list,W_NORMAL_2,EnumModifier.COST_SAVE_PICK);
        add(list,W_NORMAL_2,EnumModifier.COST_SAVE_ARMOR);
        add(list,W_NORMAL_2,EnumModifier.OVERLOAD_SWORD);
        add(list,W_NORMAL_2,EnumModifier.OVERLOAD_PICK);
        add(list,W_NORMAL_2,EnumModifier.OVERLOAD_ARMOR);
        add(list,W_NORMAL_2,EnumModifier.ANTI_PRESSURE_DEPTH);
        add(list,W_NORMAL_2,EnumModifier.ANTI_PRESSURE_HEIGHT);
        modifierLists.put(2,list);
    }

    public static void add(List<ModifierGroup> list, int weight, EnumModifier modifier)
    {
        list.add(new ModifierGroup(weight, modifier));
    }
    public static void add(List<ModifierGroup> list, int weight, EnumModifier modifier, int maxLv)
    {
        list.add(new ModifierGroup(weight, modifier, 1, maxLv));
    }

}
