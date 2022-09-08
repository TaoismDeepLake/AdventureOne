package com.deeplake.adven_one.designs;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.blocks.BlockBase;
import com.deeplake.adven_one.blocks.blockSuit.BlockDirtSuitBase;
import com.deeplake.adven_one.blocks.blockSuit.BlockLogSuitBase;
import com.deeplake.adven_one.blocks.blockSuit.BlockPlanksSuitBase;
import com.deeplake.adven_one.blocks.blockSuit.BlockStoneSuitBase;
import com.deeplake.adven_one.util.WorldGenUtil;
import com.deeplake.adven_one.world.biome.BiomeSuit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;

public enum EnumSuit {

    SET_ONE("suit_test"),
    SET_TWO("suit_back"),//backup
    ;

    final String name;
    final HashMap<Integer, SetTier> tierHashMap;
    Block WOOD_PLANKS;
    Block WOOD_LOG;
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
        WOOD_PLANKS = new BlockPlanksSuitBase(this);
        WOOD_LOG = new BlockLogSuitBase(this);
        DIRT = new BlockDirtSuitBase(this);
        STONE = new BlockStoneSuitBase(this);

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
    public static void initOreDict() {
        SET_ONE.registerOreDict();
        SET_TWO.registerOreDict();
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

    public Block getWOOD_LOG() {
        return WOOD_LOG;
    }

    public IBlockState getOreByTier(int tier)
    {
        IBlockState result = WorldGenUtil.AIR;
        SetTier setTier = tierHashMap.get(tier);

        if (setTier != null)
        {
            if (setTier.gem_ore != null)
            {
                result = setTier.gem_ore.getDefaultState();
            }
        }

        return result;
    }

    public void registerOreDict()
    {
        OreDictionary.registerOre("plankWood", WOOD_PLANKS);
        OreDictionary.registerOre("dirt", DIRT);
        OreDictionary.registerOre("logWood", WOOD_LOG);
//        OreDictionary.registerOre("stone", STONE);
        OreDictionary.registerOre("cobblestone", STONE);
    }

    public HashMap<Integer, SetTier> getTierMap() {
        return tierHashMap;
    }
}
