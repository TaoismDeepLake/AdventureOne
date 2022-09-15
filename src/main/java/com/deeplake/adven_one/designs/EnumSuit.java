package com.deeplake.adven_one.designs;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.blocks.blockSuit.BlockDirtSuitBase;
import com.deeplake.adven_one.blocks.blockSuit.BlockLogSuitBase;
import com.deeplake.adven_one.blocks.blockSuit.BlockPlanksSuitBase;
import com.deeplake.adven_one.blocks.blockSuit.BlockStoneSuitBase;
import com.deeplake.adven_one.util.WorldGenUtil;
import com.deeplake.adven_one.world.biome.BiomeSuit;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;

public enum EnumSuit {

    SET_ONE("suit_test"),
    SET_TWO("suit_back"),//backup
    SET_CELESTIAL("suit_celestial", true),//for T4 colorless
    SET_LUCK("suit_luck_a", true);//for T1 T2 luck suit


    final String name;
    final HashMap<Integer, SetTier> tierHashMap;
    Block woodPlanks;
    Block woodLog;
    Block dirt;
    Block stone;

    Biome biome = Biomes.OCEAN;

    //a hidden suit has no biome registered, also may have lots of nulls.
    boolean isHidden;

    EnumSuit(String name)
    {
        this.name = name;
        tierHashMap = new HashMap<>();
    }

    EnumSuit(String name, boolean hidden)
    {
        this(name);
        this.isHidden = hidden;
    }

    public static EnumSuit getSuit(World world, BlockPos pos)
    {
        Biome biome = world.getBiome(pos);
        if (biome instanceof BiomeSuit)
        {
            return ((BiomeSuit) biome).getSuit();
        }
        return SET_ONE;
    }

    public static EnumSuit getSuit(World world, StructureBoundingBox sbb)
    {
        BlockPos pos = new BlockPos(sbb.minX, sbb.minY, sbb.minZ);
        Biome biome = world.getBiome(pos);
        if (biome instanceof BiomeSuit)
        {
            return ((BiomeSuit) biome).getSuit();
        }
        return SET_ONE;
    }

    public void createInternalDefault()
    {
        if (isHidden)
        {
            switch (this)
            {
                case SET_ONE:
                case SET_TWO:
                    break;
                case SET_CELESTIAL:
                    createDefaultTier(4);
                    break;
                case SET_LUCK:
                    createDefaultTier(1);
                    createDefaultTier(2);
                    break;
            }
        }
        else {
            woodPlanks = new BlockPlanksSuitBase(this);
            woodLog = new BlockLogSuitBase(this);
            dirt = new BlockDirtSuitBase(this);
            stone = new BlockStoneSuitBase(this);

            createDefaultTier(1);
            createDefaultTier(2);
            createDefaultTier(3);
            biome = new BiomeSuit(String.format("%s_%s", Idealland.MODID, name), this);
            biome.fillerBlock = stone.getDefaultState();
            biome.topBlock = dirt.getDefaultState();
        }
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

    public Block getWoodPlanks() {
        return woodPlanks;
    }

    public Block getDirt() {
        return dirt;
    }

    public Block getStone() {
        return stone;
    }

    public Biome getBiome() {
        return biome;
    }

    public String getName() {
        return name;
    }

    public Block getWoodLog() {
        return woodLog;
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
        OreDictionary.registerOre("plankWood", woodPlanks);
        OreDictionary.registerOre("dirt", dirt);
        OreDictionary.registerOre("logWood", woodLog);
//        OreDictionary.registerOre("stone", STONE);
        OreDictionary.registerOre("cobblestone", stone);
    }

    public HashMap<Integer, SetTier> getTierMap() {
        return tierHashMap;
    }

    public boolean isHidden() {
        return isHidden;
    }
}
