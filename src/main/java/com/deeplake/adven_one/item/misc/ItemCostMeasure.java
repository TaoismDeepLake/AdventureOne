package com.deeplake.adven_one.item.misc;

import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import com.deeplake.adven_one.item.ItemBase;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTDef;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCostMeasure extends ItemBase {
    public ItemCostMeasure(String name) {
        super(name);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!worldIn.isRemote && entityIn instanceof EntityLivingBase)
        {
            EntityLivingBase livingBase = (EntityLivingBase) entityIn;
            int used = 1 + (int) -livingBase.getEntityAttribute(ModAttributes.COST).getAttributeValue();
            int max = 1 + (int) livingBase.getEntityAttribute(ModAttributes.COST_MAX).getAttributeValue();
            IDLNBTUtil.setIntOptimized(stack, IDLNBTDef.STATE, max);
            setDamage(stack, used);
        }
    }

    //not working. dunno why
    @Override
    public int getMaxDamage(ItemStack stack) {
        return IDLNBTUtil.GetInt(stack, IDLNBTDef.STATE);
    }
}
