package com.deeplake.adven_one.blocks;

import com.deeplake.adven_one.blocks.blockBasic.BlockOreBase;
import com.deeplake.adven_one.blocks.blockBasic.ModBlockGlassBase;
import com.deeplake.adven_one.blocks.terrain.BlockLeavesWooded;
import com.deeplake.adven_one.blocks.terrain.BlockMountainStone;
import com.deeplake.adven_one.blocks.terrain.BlockSwampSink;
import com.deeplake.adven_one.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<>();
	
	/*
	 * To add a block, put a line here,
	 * -Create a json at assets.eo.blockstates
	 * -Create a json at assets.eo.models.block
	 * -Create a json at assets.eo.models.item
	 * -Add corresponding texture png
	 */

	//T3
	public static final Block BARRIER_SKY = new ModBlockGlassBase("barrier_sky", Material.GLASS).setHardness(40f);
	public static final Block BARRIER_EARTH = new ModBlockGlassBase("barrier_earth", Material.GLASS).setHardness(20f);
	public static final Block ORE_COST = new BlockOreBase("cost_ore", ModItems.FOOD_COST_SMALL).setHardness(5f);
	public static final Block FOREST_TERRAIN = new BlockLeavesWooded("forest_terrain", Material.LEAVES).setHardness(5f);
	public static final Block MOUNTAIN_TERRAIN = new BlockMountainStone("mountain_terrain", Material.ROCK).setHardness(5f);
	public static final Block SWAMP_TERRAIN = new BlockSwampSink("swamp_terrain", Material.SAND).setHardness(5f);


}
