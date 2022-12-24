package com.deeplake.adven_one.item.suit.modifiers;

import com.deeplake.adven_one.item.suit.IHasModifiers;
import com.deeplake.adven_one.item.suit.modifiers.types.EnumGeartype;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class ModifierList {
    static HashMap<Integer, EnumModifier> ID_TO_ENUM = new HashMap<>();

    public static final int MAX_LV = 99;
    public static final int MAX_LV_WALKER = 1;

    public static void initModifier() {
        //Init modifier gear types
        EnumModifier.BLANK.addGearTypesAll();
        EnumModifier.ATK_UP.addGearTypes(EnumGeartype.SWORD, EnumGeartype.PICKAXE);
        EnumModifier.HP_UP.addGearTypesAllArmor();
        EnumModifier.HARDNESS.addGearTypesAll();
        EnumModifier.EFFICIENCY_UP.addGearTypes(EnumGeartype.PICKAXE);

        EnumModifier.WEAPON_UP.addGearTypesSword();
        EnumModifier.KILL_CREEPER.addGearTypesSword();
        EnumModifier.KILL_ENDERMAN.addGearTypesSword();
        EnumModifier.KILL_PLAYER.addGearTypesSword();
        EnumModifier.KILL_MELEE.addGearTypesSword();
        EnumModifier.KILL_BOW.addGearTypesSword();
        EnumModifier.KILL_PIG.addGearTypesSword();
        EnumModifier.KILL_COW.addGearTypesSword();
        EnumModifier.KILL_SHEEP.addGearTypesSword();
        EnumModifier.KILL_CHICK.addGearTypesSword();

        EnumModifier.COST_SAVE.addGearTypesAll();
        EnumModifier.COST_SAVE_SWORD.addGearTypesSword();
        EnumModifier.COST_SAVE_PICK.addGearTypesPickaxe();
        EnumModifier.COST_SAVE_ARMOR.addGearTypesAllArmor();
        EnumModifier.OVERLOAD_SWORD.addGearTypesSword();
        EnumModifier.OVERLOAD_PICK.addGearTypesPickaxe();
        EnumModifier.OVERLOAD_ARMOR.addGearTypesAllArmor();

        EnumModifier.ANTI_PRESSURE_DEPTH.addGearTypesAll();
        EnumModifier.ANTI_PRESSURE_HEIGHT.addGearTypesAll();

        EnumModifier.FOREST_WALK.addGearTypes(EnumGeartype.ARMOR_BOOTS);
        EnumModifier.MOUNTAIN_WALK.addGearTypes(EnumGeartype.ARMOR_BOOTS);
        EnumModifier.SWAMP_WALK.addGearTypes(EnumGeartype.ARMOR_BOOTS);
        EnumModifier.PLAINS_WALK.addGearTypes(EnumGeartype.ARMOR_BOOTS);
        EnumModifier.ISLAND_WALK.addGearTypes(EnumGeartype.ARMOR_BOOTS);
        EnumModifier.PATH_FINDER.addGearTypes(EnumGeartype.ARMOR_BOOTS);
    }

    public static EnumModifier getFromID(int id)
    {
        EnumModifier result = EnumModifier.BLANK;
        if (ID_TO_ENUM.containsKey(id))
        {
            result = ID_TO_ENUM.get(id);
        }
        return result;
    }

    public static void mergeModifiersToStack(HashMap<EnumModifier, Integer> resultMap, ItemStack rawResult, HashMap<EnumModifier, Integer> tempMap) {
        EnumGeartype enumGeartype = EnumGeartype.ALL;
        if (rawResult.getItem() instanceof IHasType)
        {
            enumGeartype = ((IHasType) rawResult.getItem()).getType(rawResult);
        }

        for (EnumModifier modifier : tempMap.keySet())
        {
            if (modifier.isApplicable(enumGeartype))
            {
                //todo: modifiers count limit
                int newLevel = tempMap.get(modifier);
                if (resultMap.containsKey(modifier))
                {
                    int oldLevel = resultMap.get(modifier);
                    resultMap.replace(modifier, oldLevel + newLevel);
                } else {
                    resultMap.put(modifier, newLevel);
                }
            }
        }

        if (rawResult.getItem() instanceof IHasModifiers && resultMap.keySet().size() > 0)
        {
            IHasModifiers iHasModifiers = (IHasModifiers) rawResult.getItem();
            iHasModifiers.storeAllToNBT(rawResult, resultMap);
        }
    }
}
