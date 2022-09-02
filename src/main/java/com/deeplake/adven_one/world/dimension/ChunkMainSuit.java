package com.deeplake.adven_one.world.dimension;

import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.util.WorldGenUtil;
import com.deeplake.adven_one.world.biome.BiomeSuit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

import static com.deeplake.adven_one.util.CommonDef.CHUNK_SIZE;

public class ChunkMainSuit extends WorldChunkBase {

    Random rand;

    //need to do this for different biome
    public IBlockState WATER = Blocks.WATER.getDefaultState();
    public IBlockState SUIT_DIRT;
    public IBlockState SUIT_STONE;
    public IBlockState SUIT_PLANKS;


    BiomeProvider provider;
    World world;

    public ChunkMainSuit(World worldIn, Random rand) {
        world = worldIn;
        provider = worldIn.getBiomeProvider();
        this.rand = rand;
    }

    @Override
    public void buildChunk(int x, int z, Chunk chunk) {

        int seaLevel = world.getSeaLevel();
        Biome mainBiome = chunk.getBiome(new BlockPos(7,7,7), provider);
        if (mainBiome instanceof BiomeSuit)
        {
            EnumSuit suit = ((BiomeSuit) mainBiome).getSuit();
            SUIT_DIRT = suit.getDIRT().getDefaultState();
            SUIT_STONE = suit.getSTONE().getDefaultState();
            SUIT_PLANKS = suit.getWOOD_PLANKS().getDefaultState();

            //Base Part

            for (int _x = 0; _x < CHUNK_SIZE; _x++)
            {
                for (int _z = 0; _z < CHUNK_SIZE; _z++)
                {
                    int curY = 127;
                    int depth = 4;
                    curY -= depth;
                    fill(curY, depth, chunk, _x, _z, SUIT_DIRT);

                    depth = 2;

                    curY -= depth;
                    fill(curY, depth, chunk, _x, _z, WorldGenUtil.CLAY);

                    curY -= depth;
                    fill(curY, depth, chunk, _x, _z, WorldGenUtil.DIRT);

                    depth = 4;

                    curY -= depth;
                    fill(curY, depth, chunk, _x, _z, WorldGenUtil.SAND);

                    curY -= depth;
                    fill(curY, depth, chunk, _x, _z, WorldGenUtil.STONE);

                    curY -= depth;
                    fill(curY, depth, chunk, _x, _z, SUIT_DIRT);

                    depth = 12;
                    curY -= depth;
                    fill(curY, depth, chunk, _x, _z, SUIT_STONE);

                    depth = 14;
                    curY -= depth;
//                    for (int _y = curY + depth; _y > curY; _y--) {
//                        WorldGenUtil.setBlockState(chunk, _x, _y, _z, AIR);
//                    }

                    depth = 63;
                    curY -= depth;
                    fill(curY, depth, chunk, _x, _z, SUIT_STONE);

                    depth = 1;
                    curY -= depth;
                    fill(curY, depth, chunk, _x, _z, WorldGenUtil.BEDROCK);

                    //Inner Sea of Stars

                    curY -= depth;
                    fill(curY, depth, chunk, _x, _z, WorldGenUtil.LANTERN);

                    depth = 11;
                    curY -= depth;
//                    for (int _y = curY + depth; _y >= curY; _y--) {
//                        WorldGenUtil.setBlockState(chunk, _x, _y, _z, AIR);
//                    }

                    depth = 1;
                    curY -= depth;
                    fill(curY, depth, chunk, _x, _z, WorldGenUtil.TALLGRASS);

                    curY -= depth;
                    fill(curY, depth, chunk, _x, _z, WorldGenUtil.GRASS);

                    curY -= depth;
                    fill(curY, depth, chunk, _x, _z, WorldGenUtil.DIRT);

                    curY -= depth;
                    fill(curY, depth, chunk, _x, _z, WorldGenUtil.BEDROCK);
                }
            }

//            for (int _y = 100; _y < seaLevel; _y++) {
//                for (int _x = 0; _x < CHUNK_SIZE; _x++)
//                {
//                    for (int _z = 0; _z < CHUNK_SIZE; _z++)
//                    {
//                        WorldGenUtil.setBlockState(chunk, _x, _y, _z, WATER);
//                    }
//                }
//            }

            //trees
            for (int _x = 0; _x < CHUNK_SIZE; _x++)
            {
                for (int _z = 0; _z < CHUNK_SIZE; _z++)
                {
                    if (rand.nextFloat() < ModConfig.WORLD_GEN_CONF.TREE_DENSITY)
                    {
                        for (int _y = seaLevel; _y < seaLevel+8; _y++) {
                            WorldGenUtil.setBlockState(chunk, _x, _y, _z, SUIT_PLANKS);
                        }
                    }
                }
            }
        }
    }

    private void fill(int curY, int depth, Chunk chunk, int _x, int _z, IBlockState SUIT_DIRT) {
        for (int _y = curY + depth; _y > curY; _y--) {
            WorldGenUtil.setBlockState(chunk, _x, _y, _z, SUIT_DIRT);
        }
    }
}
