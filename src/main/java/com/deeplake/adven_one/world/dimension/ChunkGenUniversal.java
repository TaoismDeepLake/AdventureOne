package com.deeplake.adven_one.world.dimension;

import com.deeplake.adven_one.util.WorldGenUtil;
import com.deeplake.adven_one.world.biome.BiomeSuit;
import com.deeplake.adven_one.world.populate.PopulateMainSuit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

import java.util.Arrays;

import static com.deeplake.adven_one.util.CommonDef.CHUNK_SIZE;

public class ChunkGenUniversal extends ChunkGenBase {

    ChunkMainSuit chunkMainSuit;
    PopulateMainSuit populateMainSuit;

    public static final int sky_depth = 1;

    public ChunkGenUniversal(World worldIn, long seed, boolean mapFeaturesEnabledIn, String generatorOptions) {
        super(worldIn, seed, mapFeaturesEnabledIn, generatorOptions);
        worldIn.setSeaLevel(127);
        chunkMainSuit = new ChunkMainSuit(worldIn, rand);
        //Disable Vanilla Ore Generation
        doDecorate = false;
        populateMainSuit = new PopulateMainSuit(this, chunkMainSuit);
    }

    @Override
    public void setBiomeArray(Chunk chunk, World world, int x, int z) {
        byte[] abyte = chunk.getBiomeArray();

        BlockPos ref = WorldGenUtil.blockPosFromXZ(x, z);
        byte biome = (byte)Biome.getIdForBiome(world.getBiomeForCoordsBody(ref));
        Arrays.fill(abyte, biome);
    }

    @Override
    public void buildChunk(int x, int z, Chunk chunk) {
        int xAbs = x * CHUNK_SIZE;
        int zAbs = z * CHUNK_SIZE;

        Biome biome = world.getBiome(new BlockPos(xAbs+7, 128, zAbs+7));

        if (biome instanceof BiomeSuit)
        {
            chunkMainSuit.buildChunk(x, z, chunk);
        }

        super.buildChunk(x, z, chunk);
    }

    @Override
    public void postVanillaPopulate(int x, int z, Biome biome) {
        super.postVanillaPopulate(x, z, biome);
        if (biome instanceof BiomeSuit)
        {
            populateMainSuit.onPostPopulate(x, z, biome);
        }
//        if (biome instanceof BiomeFlameTrapTower) {
//            populateFlameTrapTower.onPostPopulate(x, z, biome);
//        }
    }
}
