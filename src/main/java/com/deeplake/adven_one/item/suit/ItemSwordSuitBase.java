package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.item.ItemSwordBase;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;
import java.util.UUID;

public class ItemSwordSuitBase extends ItemSwordBase implements IHasQuality {
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
        Multimap<String, AttributeModifier> map = HashMultimap.<String, AttributeModifier>create();;
        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            double quality = getQuality(stack);
            map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, NAME_IN, getSwordDamage(tier.getTier()) * quality, 0));
            map.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, NAME_IN, -2.4000000953674316D, 0));
            map.put(ModAttributes.ATK_TIER.getName(), new AttributeModifier(ATK_DEF_MODIFIER, NAME_IN, tier.getTier(), 0));
        }

        return map;
    }

    public static double getSwordDamage(int tier)
    {
        switch (tier)
        {
            case 1:
                return ModConfig.TIER_CONF.SWORD_ATK_T1;
            case 2:
                return ModConfig.TIER_CONF.SWORD_ATK_T2;
            case 3:
                return ModConfig.TIER_CONF.SWORD_ATK_T3;
            default:
                return ModConfig.TIER_CONF.SWORD_ATK_T1 * tier;
        }
    }

    public static double getRandomQuality(Random random)
    {
        return ModConfig.QUALITY_CONF.MIN_Q_SWORD + random.nextFloat() * ModConfig.QUALITY_CONF.DELTA_Q_SWORD;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (needFirstTick(stack))
        {
            setQuality(stack, getRandomQuality(itemRand));
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return getRarityByQuality(stack);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

//    @Override
//    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
//        super.onCreated(stack, worldIn, playerIn);
//    }
}
