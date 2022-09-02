package com.deeplake.adven_one.item.armor;

import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.item.ItemArmorBase;
import com.deeplake.adven_one.util.CommonFunctions;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTDef;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.Multimap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

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
        if (ModConfig.DEBUG_CONF.SHOW_DEBUG_ARMOR)
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
}
