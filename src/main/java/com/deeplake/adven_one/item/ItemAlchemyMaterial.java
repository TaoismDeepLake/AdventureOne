package com.deeplake.adven_one.item;

import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.item.suit.IHasModifiers;
import com.deeplake.adven_one.item.suit.IHasQuality;
import com.deeplake.adven_one.item.suit.modifiers.EnumFeature;
import com.deeplake.adven_one.item.suit.modifiers.EnumModifier;
import com.deeplake.adven_one.item.suit.modifiers.ModifierPool;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ItemAlchemyMaterial extends ItemBase implements IHasQuality, IHasModifiers {
    public int poolIndex = 1;
    public ItemAlchemyMaterial(String name) {
        super(name);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!worldIn.isRemote && !canIdentify(stack))
        {
            if (needFirstTick(stack))
            {
                setQuality(stack, getRandomQuality(itemRand));
                storeAllModiToNBT(stack, getRandomModifierList(itemRand));
                storeAllFeatureToNBT(stack, getRandomFeatureList(itemRand));
            }
        }
    }

    public HashMap<EnumFeature, Integer> getRandomFeatureList(Random random)
    {
        HashMap<EnumFeature, Integer> result = new HashMap<>();
        int extraCount = 0;
        while (random.nextDouble() < ModConfig.FEATURE_CONF.EXTRA_CHANCE && extraCount < 16)
        {
            extraCount++;
        }
        if (extraCount>0)
        {
            result.put(EnumFeature.EXTRA_COUNT, 1+random.nextInt(3));
        }
        if (random.nextDouble() < ModConfig.FEATURE_CONF.MAX_CHANCE)
        {
            result.put(EnumFeature.MAX_COUNT,1);
        }
        return result;
    }

    public HashMap<EnumModifier, Integer> getRandomModifierList(Random random)
    {
        HashMap<EnumModifier, Integer> result = new HashMap<>();

        List<ModifierPool.ModifierGroup> modifierGroups = ModifierPool.modifierLists.get(poolIndex);
        if (modifierGroups == null || modifierGroups.size() == 0)
        {
            return result;
        }

        ModifierPool.ModifierGroup group = WeightedRandom.getRandomItem(random, modifierGroups);
        int level = group.minLevel;
        if (level != group.maxLevel)
        {
            level += random.nextInt(group.maxLevel-group.minLevel+1);//test level
        }
        EnumModifier modifier = group.modifier;
        result.put(modifier, modifier.getClampedLevel(level));

        return result;
    }



    @Override
    public boolean canIdentify(ItemStack stack) {
        return !stack.hasTagCompound();
    }

    @Override
    public double getQuality(ItemStack stack) {
        if (canIdentify(stack))
        {
            return ModConfig.TIER_CONF.UNIDENTIFIED_QUALITY;
        }
        return IHasQuality.super.getQuality(stack);
    }

    public double getRandomQuality(Random random)
    {
        ModConfig.TierQualityConf conf;
//        try {
//            conf = ModConfig.TIER_CONF.TIER_QUALITY_CONF[tier.getTier()-1];
//        }
//        catch (ArrayIndexOutOfBoundsException e)
//        {
            conf = ModConfig.TIER_CONF.TIER_QUALITY_CONF[0];
//        }

        if (conf == null)
        {
            return 1f;
        }

        return conf.MIN_GEM_QUALITY + random.nextFloat() * conf.DELTA_GEM_QUALITY;
    }
}
