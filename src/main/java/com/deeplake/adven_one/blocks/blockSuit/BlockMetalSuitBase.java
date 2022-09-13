package com.deeplake.adven_one.blocks.blockSuit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.blocks.BlockBase;
import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.util.IHasModel;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;

public class BlockMetalSuitBase extends BlockBase implements IHasModel, IBlockSuit {
    static final String NAME = "block";
    SetTier tier;
    public BlockMetalSuitBase(SetTier tier)
    {
        super(String.format("%s_%d_%s", tier.getSuitName(), tier.getTier(), NAME), Material.IRON);
        this.tier = tier;

        setHardness(5.0F * tier.getTier());
        setResistance(10.0F * tier.getTier());
        setSoundType(SoundType.METAL);
    }

    @Override
    public String getLocalizedName() {
        return I18n.format(Idealland.MODID+"."+NAME,
                I18n.format(tier.getTransKey()));
    }
}
