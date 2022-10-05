package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.item.ItemArmorBase;
import com.deeplake.adven_one.item.suit.modifiers.EnumModifier;
import com.deeplake.adven_one.item.suit.modifiers.IHasType;
import com.deeplake.adven_one.item.suit.modifiers.types.EnumGeartype;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class ItemArmorSuitBase extends ItemArmorBase implements IHasQuality, IHasModifiers, IHasType, IHasCost {
    public static final String NAME_IN = "Armor modifier";

    public ItemArmorSuitBase(SetTier tier, EntityEquipmentSlot equipmentSlotIn) {
        super(getName(tier, equipmentSlotIn), tier.getArmorMaterial(), 0, equipmentSlotIn);
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

    static String getName(SetTier tier, EntityEquipmentSlot equipmentSlotIn)
    {
        return String.format("%s_%d_armor%d", tier.getSuit().getName(), tier.getTier(), 3 - equipmentSlotIn.getIndex());
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format(String.format("%s.armor%d",Idealland.MODID, 3 - armorType.getIndex()),
                I18n.format(tier.getTransKey()));
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return null;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (needFirstTick(stack))
        {
            setQuality(stack, getRandomQuality(itemRand));
        }
    }

    public static double getRandomQuality(Random random)
    {
        return ModConfig.QUALITY_CONF.MIN_Q_ARMOR + random.nextFloat() * ModConfig.QUALITY_CONF.DELTA_Q_ARMOR;
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();

        double quality = getQuality(stack);

        if (slot == this.armorType)
        {
            multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[slot.getIndex()], "Armor modifier", (double)this.damageReduceAmount * quality, 0));
            multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[slot.getIndex()], "Armor toughness", (double)this.toughness * quality, 0));
            multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[slot.getIndex()], "Armor HP", getHP(stack), 0));
            multimap.put(ModAttributes.DEF_TIER.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[slot.getIndex()], NAME_IN, tier.getTier()*0.25, 0));
            multimap.put(ModAttributes.COST.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[slot.getIndex()], NAME_IN, -getCost(stack), 0));
            multimap.putAll(sharedAttributeModifiers(stack));
        }

        return multimap;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return getRarityByQuality(stack);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public double getHP(ItemStack stack)
    {
        double result = 0;
        HashMap<EnumModifier, Integer> attrMap = getAllFromNBT(stack);
        int level = attrMap.getOrDefault(EnumModifier.HARDNESS, 0);
        result += level * ModConfig.MODIFIER_CONF.HP_FIXED_GROUP.VALUE_E;

        level = attrMap.getOrDefault(EnumModifier.HP_UP, 0);
        result += level * ModConfig.MODIFIER_CONF.HP_FIXED_GROUP.VALUE_C;

        level = attrMap.getOrDefault(EnumModifier.OVERLOAD_ARMOR, 0);
        result += level * ModConfig.MODIFIER_CONF.HP_FIXED_GROUP.VALUE_A;

        return result;
    }

    @Override
    public EnumGeartype getType(ItemStack stack) {
        switch (Objects.requireNonNull(getEquipmentSlot(stack)))
        {
            case FEET:
                return EnumGeartype.ARMOR_BOOTS;
            case LEGS:
                return EnumGeartype.ARMOR_LEGGINGS;
            case CHEST:
                return EnumGeartype.ARMOR_CHEST;
            case HEAD:
                return EnumGeartype.ARMOR_HELMET;
            default:
                throw new IllegalStateException("Unexpected value: " + getEquipmentSlot(stack));
        }
    }

    @Nullable
    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        return armorType;
    }

    public int getCost(ItemStack stack)
    {
        int tierVal = tier.getTier();
        try {
            ModConfig.CostConfigByTier costConfig = ModConfig.TIER_CONF.COST_TIER[tierVal];
            int baseCost = 0;
            switch (Objects.requireNonNull(getEquipmentSlot(stack)))
            {
                case FEET:
                    baseCost = costConfig.FEET_COST;
                    break;
                case LEGS:
                    baseCost = costConfig.LEG_COST;
                    break;
                case CHEST:
                    baseCost = costConfig.CHEST_COST;
                    break;
                case HEAD:
                    baseCost = costConfig.HEAD_COST;
                    break;
            }

            baseCost *= costConfig.FACTOR;

            HashMap<EnumModifier, Integer> attrMap = getAllFromNBT(stack);
            if (attrMap == null)
            {
                Idealland.LogWarning("Error: Null list");
            }
            else {
                int level = attrMap.getOrDefault(EnumModifier.COST_SAVE, 0);
                baseCost -= level * ModConfig.MODIFIER_CONF.COST_REDUCE_FIXED_GROUP.VALUE_E;

                level = attrMap.getOrDefault(EnumModifier.COST_SAVE_ARMOR, 0);
                baseCost -= level * ModConfig.MODIFIER_CONF.COST_REDUCE_FIXED_GROUP.VALUE_D;

                level = attrMap.getOrDefault(EnumModifier.OVERLOAD_ARMOR, 0);
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

}
