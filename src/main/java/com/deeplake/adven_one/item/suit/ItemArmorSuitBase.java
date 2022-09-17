package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import com.deeplake.adven_one.item.ItemArmorBase;
import com.google.common.collect.Multimap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ItemArmorSuitBase extends ItemArmorBase {
    public static final String NAME_IN = "Armor modifier";
    //    public ItemArmorSuitBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
//        super(name, materialIn, renderIndexIn, equipmentSlotIn);
//    }

    public ItemArmorSuitBase(SetTier tier, EntityEquipmentSlot equipmentSlotIn) {
        super(getName(tier, equipmentSlotIn), tier.getArmorMaterial(), 0, equipmentSlotIn);
        this.tier = tier;

    }
    SetTier tier;

    @Override
    public boolean isDamageable() {
        return false;
    }

    static String getName(SetTier tier, EntityEquipmentSlot equipmentSlotIn)
    {
        return String.format("%s_%d_armor%d", tier.getSuit().getName(), tier.getTier(), 3 - equipmentSlotIn.getIndex());
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format(String.format("%s.armor%d",Idealland.MODID, 3 - armorType.getIndex()),
                I18n.format(tier.getTransKey()));
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
        Multimap<String, AttributeModifier> map = super.getAttributeModifiers(equipmentSlot, stack);
        if (equipmentSlot == this.armorType)
        {
            map.put(ModAttributes.DEF_TIER.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[3 - equipmentSlot.getIndex()], NAME_IN, tier.getTier()*0.25, 0));
        }

        return map;
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return null;
    }
}
