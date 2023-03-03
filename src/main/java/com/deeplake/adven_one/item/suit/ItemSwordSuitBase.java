package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.item.ItemSwordBase;
import com.deeplake.adven_one.item.suit.modifiers.EnumModifier;
import com.deeplake.adven_one.item.suit.modifiers.IHasType;
import com.deeplake.adven_one.item.suit.modifiers.types.EnumGeartype;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class ItemSwordSuitBase extends ItemSwordBase implements IHasQuality, IHasModifiers, IHasType, IHasCost {
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
        return I18n.translateToLocalFormatted(String.format("%s.sword", Idealland.MODID),
                I18n.translateToLocalFormatted(tier.getTransKey()));
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> map = HashMultimap.create();
        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, NAME_IN, getAttack(stack), 0));
            map.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, NAME_IN, -2.4000000953674316D, 0));
            map.put(ModAttributes.ATK_TIER.getName(), new AttributeModifier(ATK_DEF_MODIFIER, NAME_IN, tier.getTier(), 0));
            map.put(ModAttributes.COST.getName(), new AttributeModifier(ATK_DEF_MODIFIER, NAME_IN, -getCost(stack), 0));
            map.putAll(sharedAttributeModifiers(stack));
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

    public double getAttack(ItemStack stack)
    {
        double result = getSwordDamage(tier.getTier()) * getQuality(stack);
        HashMap<EnumModifier, Integer> attrMap = getAllModiFromNBT(stack);
        if (attrMap == null)
        {
            Idealland.LogWarning("Error: Null list");
        }
        else {
            int level = attrMap.getOrDefault(EnumModifier.HARDNESS, 0);
            result += level * ModConfig.MODIFIER_CONF.ATK_FIXED_GROUP.VALUE_E;

            level = attrMap.getOrDefault(EnumModifier.ATK_UP, 0);
            result += level * ModConfig.MODIFIER_CONF.ATK_FIXED_GROUP.VALUE_D;

            level = attrMap.getOrDefault(EnumModifier.WEAPON_UP, 0);
            result += level * ModConfig.MODIFIER_CONF.ATK_FIXED_GROUP.VALUE_C;

            level = attrMap.getOrDefault(EnumModifier.OVERLOAD_SWORD, 0);
            result += level * ModConfig.MODIFIER_CONF.ATK_FIXED_GROUP.VALUE_A;
        }

        return result;
    }

    @Override
    public EnumGeartype getType(ItemStack stack) {
        return EnumGeartype.SWORD;
    }

    public int getCost(ItemStack stack)
    {
        int tierVal = tier.getTier();
        try {
            ModConfig.CostConfigByTier costConfig = ModConfig.TIER_CONF.COST_TIER[tierVal];
            int baseCost = costConfig.SWORD_COST * costConfig.FACTOR;

            HashMap<EnumModifier, Integer> attrMap = getAllModiFromNBT(stack);
            if (attrMap == null)
            {
                Idealland.LogWarning("Error: Null list");
            }
            else {
                int level = attrMap.getOrDefault(EnumModifier.COST_SAVE, 0);
                baseCost -= level * ModConfig.MODIFIER_CONF.COST_REDUCE_FIXED_GROUP.VALUE_E;

                level = attrMap.getOrDefault(EnumModifier.COST_SAVE_SWORD, 0);
                baseCost -= level * ModConfig.MODIFIER_CONF.COST_REDUCE_FIXED_GROUP.VALUE_D;

                level = attrMap.getOrDefault(EnumModifier.OVERLOAD_SWORD, 0);
                baseCost += level * ModConfig.MODIFIER_CONF.COST_UP_FIXED_GROUP.VALUE_C;
            }

            if (baseCost < 0)
            {
                baseCost = 0;
            }

            return baseCost;
        }catch (ArrayIndexOutOfBoundsException e)
        {
            return 0;
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tier.getTier() == 4)
        {
            return;
        }
        if (tier.getSuit() == EnumSuit.SET_LUCK)
        {
            return;
        }
        super.getSubItems(tab, items);
    }
}
