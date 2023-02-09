package com.deeplake.adven_one.blocks.blockSuit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.blocks.blockBasic.BlockOreBase;
import com.deeplake.adven_one.designs.SetTier;
import net.minecraft.util.text.translation.I18n;

public class BlockOreSuitBase extends BlockOreBase implements IBlockSuit {
    static final String NAME = "ore";
    SetTier tier;
    public BlockOreSuitBase(SetTier tier) {
        super(String.format("%s_%d_%s", tier.getSuitName(), tier.getTier(), NAME), tier.getGem());
        this.tier = tier;
        setHardness(tier.getTier() * 8);
    }

    @Override
    public String getLocalizedName() {
        return I18n.translateToLocalFormatted(Idealland.MODID+"."+NAME,
                I18n.translateToLocalFormatted(tier.getTransKey()));
    }
}
