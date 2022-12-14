package com.deeplake.adven_one.blocks;

import java.util.Random;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.blocks.blockSuit.IBlockSuit;
import com.deeplake.adven_one.init.ModCreativeTabsList;
import com.deeplake.adven_one.item.ItemBlockBase;
import com.deeplake.adven_one.item.ModItems;
import com.deeplake.adven_one.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IHasModel
{
	public BlockBase(String name, Material material)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTabsList.IDL_MISC);
		
		ModBlocks.BLOCKS.add(this);
		if (this instanceof IBlockSuit)
		{
			ModItems.ITEMS.add(new ItemBlockBase(this).setRegistryName(this.getRegistryName()));
		}
		else {
			ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return super.getItemDropped(state, rand, fortune);
	}

	@Override
	public int quantityDropped(Random rand) {
		return super.quantityDropped(rand);
	}
	
	@Override
	public void registerModels() {
		Idealland.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
