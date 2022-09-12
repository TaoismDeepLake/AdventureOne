package com.deeplake.adven_one.world.dimension;

import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.world.biome.InitBiome;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.IntCache;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.deeplake.adven_one.util.CommonDef.CHUNK_SIZE;

public class PeriodicBiomeProvider extends BiomeProvider {
    public final BiomeCache biomeCacheOpen;
    final GenLayerPeriodic genLayerPeriodic;
    List<Biome> biomesList = new ArrayList<>();

    public PeriodicBiomeProvider(World world) {
        super(world.getWorldInfo());
        this.biomeCacheOpen = new BiomeCache(this);

        getBiomesToSpawnIn().clear();
        addBiomes();
        setSpawnableBiomes();

        genLayerPeriodic = new GenLayerPeriodic(ModConfig.WORLD_GEN_CONF.BIOME_X_SPAN, biomesList.size());
    }

    void setSpawnableBiomes() {
        for(EnumSuit suit : EnumSuit.values())
        {
            Biome biome = suit.getBiome();
            if (biome != null)
            {
                getBiomesToSpawnIn().add(biome);
            }
        }
    }

    void addBiomes() {
        for(EnumSuit suit : EnumSuit.values())
        {
            Biome biome = suit.getBiome();
            if (biome != null)
            {
                biomesList.add(biome);
            }
        }
    }


    public Biome getBiome(BlockPos pos, Biome defaultBiome) {
        return this.biomeCacheOpen.getBiome(pos.getX(), pos.getZ(), defaultBiome);
    }

    public void cleanupCache() {
        super.cleanupCache();
        this.biomeCacheOpen.cleanupCache();
    }

    /**
     * Gets a list of biomes for the specified blocks.
     */
    public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag) {
        IntCache.resetIntCache();

        if (listToReuse == null || listToReuse.length < width * length) {
            listToReuse = new Biome[width * length];
        }

        if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0) {
            Biome[] abiome = this.biomeCacheOpen.getCachedBiomes(x, z);
            System.arraycopy(abiome, 0, listToReuse, 0, width * length);
            return listToReuse;
        } else {
            int[] aint = this.genLayerPeriodic.getInts(x, z, width, length);

            for (int i = 0; i < width * length; ++i) {
                listToReuse[i] = biomesList.get(aint[i]);
            }
//            Idealland.Log("listToReuse:%s", listToReuse);

            return listToReuse;
        }
    }
}
