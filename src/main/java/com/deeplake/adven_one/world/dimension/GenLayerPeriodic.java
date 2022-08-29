package com.deeplake.adven_one.world.dimension;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerPeriodic extends GenLayer {
    int xUnit;
    int maxCount;

    public GenLayerPeriodic(int xUnit, int maxCount) {
        super(0L);
        this.xUnit = xUnit;
        this.maxCount = maxCount;
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);

        for (int x = 0; x < areaHeight; ++x) {
            for (int z = 0; z < areaWidth; ++z) {
                aint1[x + z * areaWidth] = (((areaX + x) / xUnit) % maxCount + maxCount) % maxCount;//prevent negative
            }
        }

        return aint1;
    }
}
