package com.deeplake.adven_one.blocks.blockSuit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.blocks.ModBlocks;
import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.init.ModCreativeTabsList;
import com.deeplake.adven_one.item.ItemBlockBase;
import com.deeplake.adven_one.item.ModItems;
import com.deeplake.adven_one.util.IHasModel;
import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;

public class BlockFenceSuitBase extends BlockFence implements IHasModel, IBlockSuit  {
    static final String NAME = "wood_fence";
    EnumSuit suit;
    public BlockFenceSuitBase(EnumSuit suit) {
        super(Material.WOOD, MapColor.WOOD);

        this.setRegistryName(String.format("%s_"+NAME,suit.getName()));
        this.setUnlocalizedName(String.format("%s_"+NAME,suit.getName()));

        setHardness(2.0F);
        setResistance(5.0F);

        setSoundType(SoundType.WOOD);
        this.suit = suit;

        setCreativeTab(ModCreativeTabsList.IDL_MISC);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlockBase(this).setRegistryName(this.getRegistryName()));

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
