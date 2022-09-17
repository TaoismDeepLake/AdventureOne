package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.item.ItemPickaxeBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemPickaxeSuitBase extends ItemPickaxeBase {
//    public ItemPickaxeSuitBase(String name, ToolMaterial material) {
//        super(name, material);
//    }

    public ItemPickaxeSuitBase(SetTier tier) {
        super(getName(tier), tier.getToolMaterial());
        this.tier = tier;
    }
    SetTier tier;

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        //do nothing
    }

    static String getName(SetTier tier)
    {
        return String.format("%s_%d_pickaxe", tier.getSuit().getName(), tier.getTier());
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format(String.format("%s.pickaxe", Idealland.MODID),
                I18n.format(tier.getTransKey()));
    }
}
