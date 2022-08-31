package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.item.ItemArmorBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemArmorSuitBase extends ItemArmorBase {
//    public ItemArmorSuitBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
//        super(name, materialIn, renderIndexIn, equipmentSlotIn);
//    }

    public ItemArmorSuitBase(SetTier tier, EntityEquipmentSlot equipmentSlotIn) {
        super(getName(tier, equipmentSlotIn), tier.getArmorMaterial(), 0, equipmentSlotIn);
        this.tier = tier;
    }
    SetTier tier;

    static String getName(SetTier tier, EntityEquipmentSlot equipmentSlotIn)
    {
        return String.format("%s_%d_armor%d", tier.getSuit().getName(), tier.getTier(), equipmentSlotIn.getIndex());
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format(String.format("%s.armor%d",Idealland.MODID, armorType.getIndex()),
                I18n.format(tier.getTransKey()));
    }
}
