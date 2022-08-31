package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.item.ItemBase;
import com.sun.jna.WString;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class ItemGemSuit extends ItemBase {
    static final String NAME = "gem";
    final SetTier tier;
    public ItemGemSuit(SetTier tier) {
        super(String.format("%s_%s_%s",tier.getSuitName(),tier.getTier(),NAME));
        this.tier = tier;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format(Idealland.MODID+"."+NAME,
                I18n.format(tier.getTransKey()));
    }
}
