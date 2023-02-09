package com.deeplake.adven_one.blocks.blockSuit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.blocks.BlockBase;
import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.util.IHasModel;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.text.translation.I18n;


public class BlockDirtSuitBase extends BlockBase implements IHasModel, IBlockSuit {
    static final String NAME = "dirt";
    EnumSuit suit;
    public BlockDirtSuitBase(EnumSuit suit)
    {
        super(String.format("%s_"+NAME,suit.getName()), Material.GROUND);

        setHardness(0.5F);
        setHarvestLevel("shovel", 0);
        setSoundType(SoundType.GROUND);
        this.suit = suit;
    }

    @Override
    public String getLocalizedName() {
        return I18n.translateToLocalFormatted(Idealland.MODID+"."+NAME,
                I18n.translateToLocalFormatted(Idealland.MODID+"."+suit.getName()));
    }
}
