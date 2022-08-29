package com.deeplake.adven_one.world.biome;

import com.deeplake.adven_one.Idealland;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class InitBiome {
//    public static final BiomeBase BIOME_TEST = new BiomeBase("hex_cube", Type.MAGICAL);

    public static void registerBiomes() {
//        initBiome(BIOME_CUBE, Type.SPOOKY, Type.MAGICAL);
    }

    static void registerToOverworld(BiomeBase biome, BiomeManager.BiomeType biomeType, int weight) {
        BiomeManager.addBiome(biomeType, new BiomeEntry(biome, weight));
        Idealland.Log("Biome registered to overworld:%s", biome.getAccessibleName());
    }

    public static BiomeBase initBiome(BiomeBase biome, Type... type) {
        String name = biome.getAccessibleName();
        biome.setRegistryName(name);
        ForgeRegistries.BIOMES.register(biome);
        BiomeDictionary.addTypes(biome, type);
        BiomeManager.addSpawnBiome(biome);
        Idealland.Log("Biome registered:%s", name);
        return biome;
    }

}
