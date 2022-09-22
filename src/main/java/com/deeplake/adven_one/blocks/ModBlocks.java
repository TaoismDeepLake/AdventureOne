package com.deeplake.adven_one.blocks;

import java.util.ArrayList;
import java.util.List;

import com.deeplake.adven_one.blocks.blockBasic.ModBlockGlassBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
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
}
