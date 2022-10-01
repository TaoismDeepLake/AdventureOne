package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.item.ItemPickaxeBase;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;
import java.util.UUID;

import static com.deeplake.adven_one.item.suit.ItemSwordSuitBase.NAME_IN;

public class ItemPickaxeSuitBase extends ItemPickaxeBase implements IHasQuality, IHasModifers{
    protected static final UUID EFFCIENCY_MODIFIER = UUID.fromString("0468b7eb-3fb0-a205-2e07-fbb2c7759863");

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
        Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            map.put(ModAttributes.EFFECIENCY.getName(), new AttributeModifier(EFFCIENCY_MODIFIER, NAME_IN, getEffeciency(stack), 0));
        }

        return map;
    }

    public double getEffeciency(ItemStack stack)
    {
        double quality = getQuality(stack);
        return efficiency * quality;
    }

}
