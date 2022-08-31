package com.deeplake.adven_one.item.weapon;

import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.item.ItemSwordBase;

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
}
