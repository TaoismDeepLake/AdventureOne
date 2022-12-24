package com.deeplake.adven_one.item.suit.modifiers;

import com.deeplake.adven_one.item.suit.IHasModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class HandleWalker {
    public static int getForestWalkLevel(Entity entity)
    {
        return getWalkerLevel(entity, EnumModifier.FOREST_WALK);
    }

    public static int getSwampWalkLevel(Entity entity)
    {
        return getWalkerLevel(entity, EnumModifier.SWAMP_WALK);
    }

    public static int getMountainWalkLevel(Entity entity)
    {
        return getWalkerLevel(entity, EnumModifier.MOUNTAIN_WALK);
    }

    public static int getWalkerLevel(Entity entity, EnumModifier modifier)
    {
        if (entity instanceof EntityLivingBase)
        {
            int result = 0;
            EntityLivingBase attacker = (EntityLivingBase) entity;
            ItemStack foot = attacker.getItemStackFromSlot(EntityEquipmentSlot.FEET);
            if (foot.getItem() instanceof IHasModifiers)
            {
                IHasModifiers modified = (IHasModifiers) foot.getItem();
                HashMap<EnumModifier, Integer> map = modified.getAllFromNBT(foot);

                int lv = map.getOrDefault(EnumModifier.PATH_FINDER, 0);
                if (lv > result)
                {
                    result = lv;
                }

                lv = map.getOrDefault(modifier, 0);
                if (lv > result)
                {
                    result = lv;
                }
            }

            return result;
        }
        else
        {
            return 0;
        }
    }
}
