package com.deeplake.adven_one.blocks.blockSuit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.blocks.ModBlocks;
import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.init.ModCreativeTabsList;
import com.deeplake.adven_one.item.ItemBlockBase;
import com.deeplake.adven_one.item.ModItems;
import com.deeplake.adven_one.util.IHasModel;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class BlockWoodStairsSuitBase extends BlockStairs implements IHasModel, IBlockSuit {
    static final String NAME = "wood_stairs";
    EnumSuit suit;
    public BlockWoodStairsSuitBase(EnumSuit suit)
    {
        super(Blocks.PLANKS.getDefaultState());

        this.setRegistryName(String.format("%s_"+NAME,suit.getName()));
        this.setUnlocalizedName(String.format("%s_"+NAME,suit.getName()));

        setSoundType(SoundType.WOOD);
        this.suit = suit;

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlockBase(this).setRegistryName(this.getRegistryName()));

        setCreativeTab(ModCreativeTabsList.IDL_MISC);

        useNeighborBrightness = true;
    }

    @Override
    public String getLocalizedName() {
        return I18n.format(Idealland.MODID+"."+NAME,
                I18n.format(Idealland.MODID+"."+suit.getName()));
    }

    @Override
    public void registerModels() {
        Idealland.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

}
