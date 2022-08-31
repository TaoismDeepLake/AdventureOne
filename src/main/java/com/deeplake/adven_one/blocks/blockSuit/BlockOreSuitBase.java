package com.deeplake.adven_one.blocks.blockSuit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.blocks.blockBasic.BlockOreBase;
import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.designs.SetTier;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;

public class BlockOreSuitBase extends BlockOreBase implements IBlockSuit {
    static final String NAME = "ore";
    SetTier tier;
    public BlockOreSuitBase(SetTier tier) {
        super(String.format("%s_%d_%s", tier.getSuitName(), tier.getTier(), NAME), tier.getGem());
        this.tier = tier;
    }

    @Override
    public String getLocalizedName() {
        return I18n.format(Idealland.MODID+"."+NAME,
                I18n.format(tier.getTransKey()));
    }
}
