package com.deeplake.adven_one.world.populate;

import com.deeplake.adven_one.blocks.ModBlocks;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.util.CommonDef;
import com.deeplake.adven_one.util.WorldGenUtil;
import com.deeplake.adven_one.world.biome.BiomeSuit;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class PopulateCrater implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimension() != ModConfig.WORLD_GEN_CONF.DIM_ONE_ID)
        {
            return;
        }
        int maxHeight = 6;
        int startX = chunkX * 16 + 3 + random.nextInt(9) + random.nextInt(9);//max20
        int startZ = chunkZ * 16 + 3 + random.nextInt(9) + random.nextInt(9);//max20
        int startY = world.getSeaLevel();
        int width = 3 + random.nextInt(5) + random.nextInt(4);//max10

        int xMax = startX + width - 1;
        int zMax = startZ + width - 1;

        float shrinkChance = 0.7f;
        float lakeChance = 0.1f;

        boolean isLake = random.nextFloat() < lakeChance;
        boolean hasTree = random.nextFloat() < ModConfig.WORLD_GEN_CONF.CRATER_CONF.CRATER_TREE_CHANCE;

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int y = startY; y <= startY + maxHeight; y++) {
            for (int x = startX; x <= xMax; x++) {
                for (int z = startZ; z <= zMax; z++) {
                    if (x == startX || z == startZ
                            || x == xMax || z == zMax) {
                        pos.setPos(x, startY, z);
                        Biome biome = world.getBiome(pos);
                        if (biome instanceof BiomeSuit) {
                            BiomeSuit biomeSuit = (BiomeSuit) biome;
                            IBlockState state = biomeSuit.getSuit().getDirt().getDefaultState();
                            //is boundary

                            pos.setY(y);
                            world.setBlockState(pos, state, 2);
                        }
                    }
                    else{
                        //inner lake
                        if (isLake) {
                            pos.setPos(x, startY, z);
                            world.setBlockState(pos, WorldGenUtil.WATER, 2);
                            pos.add(0, -1, 0);
                            world.setBlockState(pos, WorldGenUtil.WATER, 2);
                        } else {
                            pos.setPos(x, startY, z);
                            world.setBlockState(pos, WorldGenUtil.AIR, 2);
                            pos.add(0, -1, 0);
                            world.setBlockState(pos, WorldGenUtil.AIR, 2);
                        }
                    }
                }
            }

            if (random.nextFloat() < shrinkChance) {
                width-=2;
                startX++;
                startZ++;
                xMax--;
                zMax--;
                if (width <= 3) {
                    break;
                }
            }
        }

        if (!isLake && hasTree)
        {
            createTree(random, world, (startX + xMax)/2,  startY-1,(startZ + zMax)/2);
        }
    }

    static final IBlockState LEAVES = Blocks.LEAVES.getDefaultState()
            .withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE).withProperty(BlockLeaves.DECAYABLE, Boolean.FALSE);
    static final IBlockState ORE = ModBlocks.ORE_COST.getDefaultState();

    //y is the root height
    public void createTree(Random random, World world, int x, int y, int z) {
        Biome biome = world.getBiome(new BlockPos(x, y, z));

        if (biome instanceof BiomeSuit)
        {
            IBlockState wood = ((BiomeSuit) biome).getSuit().getWoodLog().getDefaultState();
            IBlockState fence = ((BiomeSuit) biome).getSuit().getWoodFence().getDefaultState();

            int minHeightTrunk = 1;
            int heightTrunkDelta = 2;
            int heightTrunk = minHeightTrunk + random.nextInt(heightTrunkDelta + 1);

            int minHeightLeaves = 1;
            int heightLeavesDelta = 2;
            int heightLeaves = minHeightLeaves + random.nextInt(heightLeavesDelta + 1);

            float oreChance = 0.5f;

            int _y = y;
            BlockPos.MutableBlockPos pointer = new BlockPos.MutableBlockPos(x,y,z);
            //root, may have ore
            world.setBlockState(pointer, random.nextFloat() < oreChance ? ORE : fence);

            //trunk
            _y++;
            for (; _y <= y + heightTrunk; _y++) {
                pointer.setY(_y);
                world.setBlockState(pointer, wood);
            }

            //transistion
            for (int _x = x-1; _x <= x+1; _x++) {
                for (int _z = z - 1; _z <= z+1; _z++) {
                    pointer.setPos(_x, _y, _z);
                    tryLeaves(world, pointer);
                }
            }

            pointer.setPos(x,_y,z);
            world.setBlockState(pointer, wood);

            //main bulk
            for (; _y <= y + heightTrunk + heightLeaves - 1; _y++) {
                pointer.setY(_y);
                for (int _x = x-2; _x <= x+2; _x++) {
                    for (int _z = z - 2; _z <= z+2; _z++) {
                        pointer.setPos(_x, _y, _z);
                        tryLeaves(world, pointer);
                    }
                }
                pointer.setPos(x,_y,z);
                world.setBlockState(pointer, fence);
            }

            for (int _x = x-1; _x <= x+1; _x++) {
                for (int _z = z - 1; _z <= z+1; _z++) {
                    pointer.setPos(_x, _y, _z);
                    tryLeaves(world, pointer);
                }
            }
        }
    }

    private void tryLeaves(World world, BlockPos.MutableBlockPos pointer) {
        if (world.getBlockState(pointer).getMaterial() == Material.AIR)
        {
            world.setBlockState(pointer, LEAVES, CommonDef.BlockFlags.TO_CLIENT);
        }
    }
}
