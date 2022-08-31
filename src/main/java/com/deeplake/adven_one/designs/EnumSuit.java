package com.deeplake.adven_one.designs;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.blocks.BlockBase;
import com.deeplake.adven_one.world.biome.BiomeSuit;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;

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

    Biome biome = Biomes.OCEAN;

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
        biome = new BiomeSuit(String.format("%s_%s", Idealland.MODID, name), this);
        biome.fillerBlock = STONE.getDefaultState();
        biome.topBlock = DIRT.getDefaultState();
    }

    public void createDefaultTier(int tier)
    {
        tierHashMap.put(tier, new SetTier(tier, name, this));
    }


    public static void init() {
        SET_ONE.createInternalDefault();
        SET_TWO.createInternalDefault();
    }

    public Block getWOOD_PLANKS() {
        return WOOD_PLANKS;
    }

    public Block getDIRT() {
        return DIRT;
    }

    public Block getSTONE() {
        return STONE;
    }

    public Biome getBiome() {
        return biome;
    }

    public String getName() {
        return name;
    }
}
