package com.deeplake.adven_one.world.populate;

import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.util.CommonDef;
import com.deeplake.adven_one.util.WorldGenUtil;
import com.deeplake.adven_one.world.biome.BiomeSuit;
import com.deeplake.adven_one.world.dimension.ChunkGenBase;
import com.deeplake.adven_one.world.dimension.ChunkMainSuit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

public class PopulateMainSuit {
    ChunkGenBase chunkGenBase;
    ChunkMainSuit chunkMainSuit;

    public PopulateMainSuit(ChunkGenBase chunkGenBase, ChunkMainSuit chunkMainSuit) {
        this.chunkGenBase = chunkGenBase;
        this.chunkMainSuit = chunkMainSuit;
    }

    public void onPostPopulate(int x, int z, Biome biome) {
        if (biome instanceof BiomeSuit)
        {
            BiomeSuit biomeSuit = (BiomeSuit) biome;
            Chunk chunk = chunkGenBase.world.getChunkFromChunkCoords(x, z);

            spreadOre(chunk, x, z, 115, 115, WorldGenUtil.COAL, ModConfig.WORLD_GEN_CONF.COAL_DENSITY);

            spreadOre(chunk, x, z, 114, 114, WorldGenUtil.REDS, ModConfig.WORLD_GEN_CONF.REDSTONE_DENSITY);
            spreadOre(chunk, x, z, 114, 114, WorldGenUtil.IRON, ModConfig.WORLD_GEN_CONF.IRON_DENSITY);

            spreadOre(chunk, x, z, 113, 113, WorldGenUtil.GOLD, ModConfig.WORLD_GEN_CONF.GOLD_DENSITY);
            spreadOre(chunk, x, z, 113, 113, WorldGenUtil.LAPIS, ModConfig.WORLD_GEN_CONF.LAPIS_DENSITY);

            spreadOre(chunk, x, z, 112, 112, WorldGenUtil.DIAMOND, ModConfig.WORLD_GEN_CONF.DIAMOND_DENSITY);

            spreadOre(chunk, x, z, 96, 107, biomeSuit.getSuit().getOreByTier(2), ModConfig.WORLD_GEN_CONF.T1_DENSITY);

            spreadOre(chunk, x, z, 64, 79, WorldGenUtil.LAVA, ModConfig.WORLD_GEN_CONF.T1_DENSITY_PLUS);
            spreadOre(chunk, x, z, 64, 79, biomeSuit.getSuit().getOreByTier(2), ModConfig.WORLD_GEN_CONF.T1_DENSITY_PLUS);

            spreadOre(chunk, x, z, 48, 63, biomeSuit.getSuit().getOreByTier(2), ModConfig.WORLD_GEN_CONF.T1_DENSITY);

            spreadOre(chunk, x, z, 16, 31, WorldGenUtil.BEDROCK, ModConfig.WORLD_GEN_CONF.BEDROCK_DENSITY_PLUS);
            spreadOre(chunk, x, z, 16, 31, biomeSuit.getSuit().getOreByTier(2), ModConfig.WORLD_GEN_CONF.T1_DENSITY_PLUS);
        }
    }

    public void spreadOre(Chunk chunk, int x, int z, int minY, int maxY, IBlockState state, int density)
    {
        Random random = chunkGenBase.rand;
        for (int i = 0; i < density * (maxY - minY + 1); i++)
        {
            int _x = CommonDef.CHUNK_CENTER_INT + random.nextInt(CommonDef.CHUNK_SIZE);
            int _z = CommonDef.CHUNK_CENTER_INT + random.nextInt(CommonDef.CHUNK_SIZE);
            int _y = minY;
            if (maxY > minY)
            {
                _y += random.nextInt(maxY - minY + 1);
            }
            WorldGenUtil.setBlockState(chunk, _x, _y, _z, state);
        }
    }

}
