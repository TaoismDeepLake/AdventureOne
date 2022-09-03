package com.deeplake.adven_one.entity.creatures.suit;

import com.deeplake.adven_one.entity.creatures.EntityGeneralMob;
import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntitySuitMob extends EntityGeneralMob {
    public EntitySuitMob(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        setAttr(32, 0.3, 4, 0, 20);
    }

    public void setTierAll(float tierAll)
    {
        setAtkTier(tierAll);
        setDefTier(tierAll);
    }

    public void setAtkTier(float tier)
    {
        getEntityAttribute(ModAttributes.ATK_TIER).setBaseValue(tier);
        if (tier >= 3f)
        {
            setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
        } else if (tier >= 2f)
        {
            setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
        } else if (tier >= 1f)
        {
            setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
        }
    }

    public void setDefTier(float tier)
    {
        getEntityAttribute(ModAttributes.DEF_TIER).setBaseValue(tier);
        if (tier >= 3f)
        {
            setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
        } else if (tier >= 2f)
        {
            setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
        } else if (tier >= 1f)
        {
            setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
        }
    }
}
