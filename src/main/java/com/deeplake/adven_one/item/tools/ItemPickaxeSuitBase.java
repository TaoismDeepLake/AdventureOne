package com.deeplake.adven_one.item.tools;

import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.item.ItemPickaxeBase;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemPickaxeSuitBase extends ItemPickaxeBase {
//    public ItemPickaxeSuitBase(String name, ToolMaterial material) {
//        super(name, material);
//    }

    public ItemPickaxeSuitBase(SetTier tier) {
        super(getName(tier), tier.getToolMaterial());
        this.tier = tier;
    }
    SetTier tier;

    static String getName(SetTier tier)
    {
        return String.format("%s_%d_pickaxe", tier.getSuit().getName(), tier.getTier());
    }
}
