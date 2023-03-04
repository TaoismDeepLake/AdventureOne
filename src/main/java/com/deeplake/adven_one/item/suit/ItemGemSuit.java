package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.item.ItemAlchemyMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

import java.util.Random;

public class ItemGemSuit extends ItemAlchemyMaterial {
    static final String NAME = "gem";
    final SetTier tier;

    public ItemGemSuit(SetTier tier) {
        super(String.format("%s_%s_%s",tier.getSuitName(),tier.getTier(),NAME));
        this.tier = tier;
        if (tier.getTier() > 1)
        {
            poolIndex = 2;
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.translateToLocalFormatted(Idealland.MODID+"."+NAME,
                I18n.translateToLocalFormatted(tier.getTransKey()));
    }

    @Override
    public double getRandomQuality(Random random)
    {
        ModConfig.TierQualityConf conf;
        try {
            conf = ModConfig.TIER_CONF.TIER_QUALITY_CONF[tier.getTier()-1];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            conf = ModConfig.TIER_CONF.TIER_QUALITY_CONF[0];
        }

        if (conf == null)
        {
            return 1f;
        }

        return conf.MIN_GEM_QUALITY + random.nextFloat() * conf.DELTA_GEM_QUALITY;
    }
}
