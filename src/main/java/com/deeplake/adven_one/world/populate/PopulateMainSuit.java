package com.deeplake.adven_one.world.populate;

import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.util.CommonDef;
import com.deeplake.adven_one.util.WorldGenUtil;
import com.deeplake.adven_one.world.biome.BiomeSuit;
import com.deeplake.adven_one.world.dimension.ChunkGenBase;
import com.deeplake.adven_one.world.dimension.ChunkMainSuit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
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

            spreadOre(chunk, x, z, 145, 150, WorldGenUtil.BARRIER_SKY, ModConfig.WORLD_GEN_CONF.GLASS_BARRIER_DENSITY);

            spreadOre(chunk, x, z, 119, 119, WorldGenUtil.COAL, ModConfig.WORLD_GEN_CONF.COAL_DENSITY);

            spreadOre(chunk, x, z, 118, 118, WorldGenUtil.REDS, ModConfig.WORLD_GEN_CONF.REDSTONE_DENSITY);
            spreadOre(chunk, x, z, 118, 118, WorldGenUtil.IRON, ModConfig.WORLD_GEN_CONF.IRON_DENSITY);

            spreadOre(chunk, x, z, 117, 117, WorldGenUtil.GOLD, ModConfig.WORLD_GEN_CONF.GOLD_DENSITY);
            spreadOre(chunk, x, z, 117, 117, WorldGenUtil.LAPIS, ModConfig.WORLD_GEN_CONF.LAPIS_DENSITY);

            spreadOre(chunk, x, z, 116, 116, WorldGenUtil.DIAMOND, ModConfig.WORLD_GEN_CONF.DIAMOND_DENSITY);

            spreadOre(chunk, x, z, 96, 107, biomeSuit.getSuit().getOreByTier(2), ModConfig.WORLD_GEN_CONF.T1_DENSITY);

            spreadOre(chunk, x, z, 72, 78, WorldGenUtil.LAVA, ModConfig.WORLD_GEN_CONF.T1_DENSITY_PLUS);
            spreadOre(chunk, x, z, 72, 78, biomeSuit.getSuit().getOreByTier(2), ModConfig.WORLD_GEN_CONF.T1_DENSITY_PLUS);

            spreadOre(chunk, x, z, 56, 71, biomeSuit.getSuit().getOreByTier(2), ModConfig.WORLD_GEN_CONF.T1_DENSITY);

            spreadOre(chunk, x, z, 48, 55, WorldGenUtil.BARRIER_EARTH, ModConfig.WORLD_GEN_CONF.GLASS_BARRIER_DENSITY);

            spreadOre(chunk, x, z, 16, 31, WorldGenUtil.BEDROCK, ModConfig.WORLD_GEN_CONF.BEDROCK_DENSITY_PLUS);
            spreadOre(chunk, x, z, 16, 31, biomeSuit.getSuit().getOreByTier(2), ModConfig.WORLD_GEN_CONF.T1_DENSITY_PLUS);

            if (chunkGenBase.rand.nextInt(ModConfig.WORLD_GEN_CONF.CHUNK_PER_HOLE) == 0)
            {
                digHole(chunk, x, z);
            }
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

    static Vec3i[] cycle = {
        new Vec3i(1,0,1),
        new Vec3i(0,0,1),
        new Vec3i(-1,0,1),
        new Vec3i(-1,0,0),
        new Vec3i(-1,0,-1),
        new Vec3i(0,0,-1),
        new Vec3i(1,0,-1),
        new Vec3i(1,0,0),
    };

    public void digHole(Chunk chunk, int x, int z)
    {
        int minDepth = 8;
        int maxDepth = 32;
        Random random = chunkGenBase.rand;
        int depth = minDepth + random.nextInt(maxDepth - minDepth);

        int caveHeight = 4;
        int maxY = 127+caveHeight;
        int minY = maxY - depth;

        int _x = 7;
        int _z = 7;

        BlockPos.MutableBlockPos pointer = new BlockPos.MutableBlockPos(_x,maxY,_z);

        for (int _y = maxY; _y > minY; _y--)
        {
            for (int i = 0; i < caveHeight; i++)
            {
                BlockPos pos = pointer.add(cycle[_y % 8].getX(), 0, cycle[_y % 8].getZ());
                WorldGenUtil.setBlockState(chunk, pos.getX(), _y-i, pos.getZ(), WorldGenUtil.AIR);
                WorldGenUtil.setBlockState(chunk, _x, _y-i, _z, WorldGenUtil.AIR);
            }
        }
    }
}
