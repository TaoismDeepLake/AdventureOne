package com.deeplake.adven_one.designs;

import com.deeplake.adven_one.blocks.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.HashMap;

public enum EnumSuit {

    SET_ONE("suit_test"),
    SET_TWO("suit_test_2"),
    ;

    final String name;
    final HashMap<Integer, SetTier> tierHashMap;
    Block WOOD_PLANKS;
    Block DIRT;
    Block STONE;

    EnumSuit(String name)
    {
        this.name = name;
        tierHashMap = new HashMap<>();
    }

    public void createInternalDefault()
    {
        WOOD_PLANKS = new BlockBase(String.format("%s_%s", name, "planks"), Material.WOOD);
        DIRT = new BlockBase(String.format("%s_%s", name, "dirt"), Material.WOOD);
        STONE = new BlockBase(String.format("%s_%s", name, "stone"), Material.WOOD);
        createDefaultTier(1);
        createDefaultTier(2);
        createDefaultTier(3);
    }

    public void createDefaultTier(int tier)
    {
        tierHashMap.put(tier, new SetTier(tier, name));
    }


    public static void init() {
        SET_ONE.createInternalDefault();
        SET_TWO.createInternalDefault();
    }
}
