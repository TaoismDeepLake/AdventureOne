package com.deeplake.adven_one.world.populate;

import com.deeplake.adven_one.util.WorldGenUtil;
import com.deeplake.adven_one.world.biome.BiomeSuit;
import net.minecraft.block.state.IBlockState;
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
        int maxHeight = 6;
        int startX = chunkX * 16 + 1 + random.nextInt(9) + random.nextInt(9);//max17
        int startZ = chunkZ * 16 + 1 + random.nextInt(9) + random.nextInt(9);//max17
        int startY = world.getSeaLevel();
        int width = 3 + random.nextInt(5) + random.nextInt(5);//max11

        int xMax = startX + width - 1;
        int zMax = startZ + width - 1;

        float shrinkChance = 0.7f;
        float lakeChance = 0.1f;

        boolean isLake = random.nextFloat() < lakeChance;

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
                    return;
                }
            }
        }
    }
}
