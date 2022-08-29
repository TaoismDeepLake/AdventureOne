package com.deeplake.adven_one.blocks.builder;

import com.deeplake.adven_one.blocks.BlockBase;
import com.deeplake.adven_one.blocks.tileEntity.builder.TileEntityBuilderOne;
import com.deeplake.adven_one.init.ModCreativeTabsList;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBuilderOne extends BlockBase implements ITileEntityProvider {

    public BlockBuilderOne(String name, Material material) {
        super(name, material);
        setCreativeTab(ModCreativeTabsList.IDL_MISC);
        setSoundType(SoundType.METAL);
        setHardness(5.0F);
        setResistance(15.0F);
        setHarvestLevel("pickaxe", 3);
        setLightOpacity(1);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        TileEntityBuilderOne t = new TileEntityBuilderOne();
        t.buildRatePerTick = 1f;
        return t;
    }
}
