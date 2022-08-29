package com.deeplake.adven_one.world.dimension;


import com.deeplake.adven_one.init.ModConfig;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class InitDimension {
    public static DimensionType DIM_UNIV = DimensionType.register("adv_one_main", "_advmain", ModConfig.WORLD_GEN_CONF.DIM_ONE_ID, DimensionMain.class, false);

    public static void registerDimensions() {

        DimensionManager.registerDimension(ModConfig.WORLD_GEN_CONF.DIM_ONE_ID, DIM_UNIV);
    }

    public static NBTTagCompound getDimensionData(World world) {
        return world.getWorldInfo().getDimensionData(ModConfig.WORLD_GEN_CONF.DIM_ONE_ID);
    }

    public void init() {

    }
}
