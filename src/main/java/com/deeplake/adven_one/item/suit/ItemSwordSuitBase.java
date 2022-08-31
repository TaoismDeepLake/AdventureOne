package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.item.ItemSwordBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class ItemSwordSuitBase extends ItemSwordBase {
//    public ItemSwordSuitBase(String name, ToolMaterial material) {
//        super(name, material);
//    }

    public ItemSwordSuitBase(SetTier tier) {
        super(getName(tier), tier.getToolMaterial());
        this.tier = tier;
    }
    SetTier tier;

    static String getName(SetTier tier)
    {
        return String.format("%s_%d_sword", tier.getSuit().getName(), tier.getTier());
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format(String.format("%s.sword", Idealland.MODID),
                I18n.format(tier.getTransKey()));
    }
}
