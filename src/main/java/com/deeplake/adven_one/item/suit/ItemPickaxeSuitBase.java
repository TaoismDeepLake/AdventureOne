package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.item.ItemPickaxeBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class ItemPickaxeSuitBase extends ItemPickaxeBase implements IHasQuality{
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

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return (float) (super.getDestroySpeed(stack, state) * getQuality(stack));
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
}
