package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import com.deeplake.adven_one.item.ItemSwordBase;
import com.google.common.collect.Multimap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class ItemSwordSuitBase extends ItemSwordBase {
    //    public ItemSwordSuitBase(String name, ToolMaterial material) {
//        super(name, material);
//    }

    protected static final UUID ATK_DEF_MODIFIER = UUID.fromString("8b852fa8-e952-9989-5742-467809ab850c");
    public static final String NAME_IN = "Weapon modifier";

    public ItemSwordSuitBase(SetTier tier) {
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
        return String.format("%s_%d_sword", tier.getSuit().getName(), tier.getTier());
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format(String.format("%s.sword", Idealland.MODID),
                I18n.format(tier.getTransKey()));
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            map.put(ModAttributes.ATK_TIER.getName(), new AttributeModifier(ATK_DEF_MODIFIER, NAME_IN, tier.getTier(), 0));
        }

        return map;
    }
}
