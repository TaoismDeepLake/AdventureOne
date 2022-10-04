package com.deeplake.adven_one.worldgen;

import com.deeplake.adven_one.world.populate.PopulateCrater;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitWorldGen {
    static int nextWeight = 100;

    public static void registerWorldGen()
    {
        register(new PopulateCrater());
    }

    static void register(IWorldGenerator generator)
    {
        GameRegistry.registerWorldGenerator(generator, nextWeight);
        nextWeight++;
    }
}
