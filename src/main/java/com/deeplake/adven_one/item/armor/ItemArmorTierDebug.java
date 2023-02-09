package com.deeplake.adven_one.item.armor;

import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import com.deeplake.adven_one.entity.creatures.model.ModelTestArmor;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.init.ModCreativeTabsList;
import com.deeplake.adven_one.item.ItemArmorBase;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTDef;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.Multimap;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static com.deeplake.adven_one.item.suit.ItemArmorSuitBase.NAME_IN;

public class ItemArmorTierDebug extends ItemArmorBase {
    public ItemArmorTierDebug(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(name, materialIn, renderIndexIn, equipmentSlotIn);
        setHasSubtypes(true);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if (playerIn.isCreative())
        {
            target.setItemStackToSlot(getEquipmentSlot(), stack.copy());
        }
        return true;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        super.getSubItems(tab, items);
        if (ModConfig.DEBUG_CONF.SHOW_DEBUG_ARMOR && tab == ModCreativeTabsList.IDL_MISC)
        {
            for (float tier = 0.25f; tier <= 4f; tier += 0.25f)
            {
                ItemStack stack = new ItemStack(this);
                IDLNBTUtil.SetDouble(stack, IDLNBTDef.STATE, tier);
                items.add(stack);
            }
        }
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
        Multimap<String, AttributeModifier> map = super.getAttributeModifiers(equipmentSlot, stack);
        if (equipmentSlot == this.armorType)
        {
            map.put(ModAttributes.DEF_TIER.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()],
                    NAME_IN,
                    IDLNBTUtil.GetDouble(stack, IDLNBTDef.STATE, 0f), 0));
        }

        return map;
    }

    @SideOnly(Side.CLIENT)
    @Nullable
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        if (itemStack != ItemStack.EMPTY)
        {
            if (itemStack.getItem() instanceof ItemArmorTierDebug)
            {
                ModelTestArmor model = new ModelTestArmor();
                model.bipedHead.showModel = armorSlot.equals(EntityEquipmentSlot.HEAD);
                model.isChild = _default.isChild;
                model.isRiding = _default.isRiding;
                model.isSneak = _default.isSneak;
                model.rightArmPose = _default.rightArmPose;
                model.leftArmPose = _default.leftArmPose;

                return model;
            }
        }
        return null;
    }
}
