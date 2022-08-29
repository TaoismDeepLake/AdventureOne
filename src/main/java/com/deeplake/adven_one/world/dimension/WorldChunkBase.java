package com.deeplake.adven_one.world.dimension;

import net.minecraft.world.chunk.Chunk;

public abstract class WorldChunkBase {
    public abstract void buildChunk(int x, int z, Chunk chunk);
}
