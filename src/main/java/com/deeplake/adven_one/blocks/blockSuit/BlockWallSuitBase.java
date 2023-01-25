package com.deeplake.adven_one.blocks.blockSuit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.blocks.ModBlocks;
import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.init.ModCreativeTabsList;
import com.deeplake.adven_one.item.ItemBlockBase;
import com.deeplake.adven_one.item.ModItems;
import com.deeplake.adven_one.util.IHasModel;
import net.minecraft.block.BlockWall;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class BlockWallSuitBase extends BlockWall implements IHasModel, IBlockSuit {
    static final String NAME = "stone_wall";
    EnumSuit suit;

    public BlockWallSuitBase(EnumSuit suit)
    {

        super(Blocks.STONEBRICK);
        this.setRegistryName(String.format("%s_"+NAME,suit.getName()));
        this.setUnlocalizedName(String.format("%s_"+NAME,suit.getName()));

        this.suit = suit;

        setCreativeTab(ModCreativeTabsList.IDL_MISC);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlockBase(this).setRegistryName(this.getRegistryName()));


    }

    @Override
    public String getLocalizedName() {
        return I18n.translateToLocalFormatted(Idealland.MODID+"."+NAME,
                I18n.translateToLocalFormatted(Idealland.MODID+"."+suit.getName()));
    }

    @Override
    public void registerModels() {
        Idealland.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this));
    }
}
