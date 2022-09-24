package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import com.deeplake.adven_one.entity.creatures.model.ModelLuckArmor;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.item.ItemArmorBase;
import com.deeplake.adven_one.item.armor.ItemArmorTierDebug;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Random;

public class ItemArmorSuitBase extends ItemArmorBase implements IHasQuality{
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
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        double quality = getQuality(stack);

        if (slot == this.armorType)
        {
            multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[slot.getIndex()], "Armor modifier", (double)this.damageReduceAmount * quality, 0));
            multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[slot.getIndex()], "Armor toughness", (double)this.toughness * quality, 0));
            multimap.put(ModAttributes.DEF_TIER.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[slot.getIndex()], NAME_IN, tier.getTier()*0.25, 0));
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

    @Nullable
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        if (itemStack != ItemStack.EMPTY)
        {
            ModelBiped biped = null;
            switch (tier.getSuit())
            {
                case SET_ONE:
                    break;
                case SET_TWO:
                    break;
                case SET_CELESTIAL:
                    break;
                case SET_LUCK:
                    biped = new ModelLuckArmor();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + tier.getSuit());
            }
            if (biped != null)
            {
//                biped.bipedHead.showModel = armorSlot.equals(EntityEquipmentSlot.HEAD);
//
//                biped.bipedBody.showModel = armorSlot.equals(EntityEquipmentSlot.CHEST);
//                biped.bipedLeftArm.showModel = armorSlot.equals(EntityEquipmentSlot.CHEST);
//                biped.bipedRightArm.showModel = armorSlot.equals(EntityEquipmentSlot.CHEST);
//
//                biped.bipedLeftLeg.showModel = armorSlot.equals(EntityEquipmentSlot.LEGS);
//                biped.bipedRightLeg.showModel = armorSlot.equals(EntityEquipmentSlot.LEGS);

                biped.isChild = _default.isChild;
                biped.isRiding = _default.isRiding;
                biped.isSneak = _default.isSneak;
                biped.rightArmPose = _default.rightArmPose;
                biped.leftArmPose = _default.leftArmPose;

                return biped;
            }
        }
        return null;
    }
}
