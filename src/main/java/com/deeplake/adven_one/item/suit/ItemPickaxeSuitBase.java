package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.item.ItemPickaxeBase;
import com.deeplake.adven_one.item.suit.modifiers.EnumModifier;
import com.deeplake.adven_one.item.suit.modifiers.IHasType;
import com.deeplake.adven_one.item.suit.modifiers.types.EnumGeartype;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class ItemPickaxeSuitBase extends ItemPickaxeBase implements IHasQuality, IHasModifiers, IHasType, IHasCost {
    protected static final UUID EFFCIENCY_MODIFIER = UUID.fromString("0468b7eb-3fb0-a205-2e07-fbb2c7759863");

    public ItemPickaxeSuitBase(SetTier tier) {
        super(getName(tier), tier.getToolMaterial());
        this.tier = tier;
//        logNBT = true;
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
        return I18n.translateToLocalFormatted(String.format("%s.pickaxe", Idealland.MODID),
                I18n.translateToLocalFormatted(tier.getTransKey()));
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        Material material = state.getMaterial();
        if (material != Material.IRON && material != Material.ANVIL && material != Material.ROCK)
        {
            return super.getDestroySpeed(stack, state);
        } else {
            return (float) getEffeciency(stack);
        }
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
        return ModConfig.QUALITY_CONF.MIN_Q_PICKAXE + random.nextFloat() * ModConfig.QUALITY_CONF.DELTA_Q_PICKAXE;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return getRarityByQuality(stack);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, NAME_IN, getAttack(stack), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, NAME_IN, this.attackSpeed, 0));
            multimap.put(ModAttributes.EFFICIENCY.getName(), new AttributeModifier(EFFCIENCY_MODIFIER, NAME_IN, getEffeciency(stack), 0));
            multimap.put(ModAttributes.COST.getName(), new AttributeModifier(EFFCIENCY_MODIFIER, NAME_IN, -getCost(stack), 0));
            multimap.putAll(sharedAttributeModifiers(stack));
        }

        return multimap;
    }

    public double getAttack(ItemStack stack)
    {
        double result = this.attackDamage;
        HashMap<EnumModifier, Integer> attrMap = getAllFromNBT(stack);
        int level = attrMap.getOrDefault(EnumModifier.HARDNESS, 0);
        result += level * ModConfig.MODIFIER_CONF.ATK_FIXED_GROUP.VALUE_E;

        level = attrMap.getOrDefault(EnumModifier.ATK_UP, 0);
        result += level * ModConfig.MODIFIER_CONF.ATK_FIXED_GROUP.VALUE_D;

        return result;
    }

    public double getEffeciency(ItemStack stack)
    {
        double result = efficiency * getQuality(stack);
        HashMap<EnumModifier, Integer> attrMap = getAllFromNBT(stack);
        int level = attrMap.getOrDefault(EnumModifier.HARDNESS, 0);
        result += level * ModConfig.MODIFIER_CONF.EFFICIENCY_FIXED_GROUP.VALUE_E;

        level = attrMap.getOrDefault(EnumModifier.EFFICIENCY_UP, 0);
        result += level * ModConfig.MODIFIER_CONF.EFFICIENCY_FIXED_GROUP.VALUE_C;

        level = attrMap.getOrDefault(EnumModifier.OVERLOAD_PICK, 0);
        result += level * ModConfig.MODIFIER_CONF.EFFICIENCY_FIXED_GROUP.VALUE_A;

        return result;
    }

    @Override
    public EnumGeartype getType(ItemStack stack) {
        return EnumGeartype.PICKAXE;
    }

    public int getCost(ItemStack stack)
    {
        int tierVal = tier.getTier();
        try {
            ModConfig.CostConfigByTier costConfig = ModConfig.TIER_CONF.COST_TIER[tierVal];
            int baseCost = costConfig.PICK_COST * costConfig.FACTOR;

            HashMap<EnumModifier, Integer> attrMap = getAllFromNBT(stack);
            if (attrMap == null)
            {
                Idealland.LogWarning("Error: Null list");
            }
            else {
                int level = attrMap.getOrDefault(EnumModifier.COST_SAVE, 0);
                baseCost -= level * ModConfig.MODIFIER_CONF.COST_REDUCE_FIXED_GROUP.VALUE_E;

                level = attrMap.getOrDefault(EnumModifier.COST_SAVE_PICK, 0);
                baseCost -= level * ModConfig.MODIFIER_CONF.COST_REDUCE_FIXED_GROUP.VALUE_D;

                level = attrMap.getOrDefault(EnumModifier.OVERLOAD_PICK, 0);
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
