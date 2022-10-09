package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.item.ItemBase;
import com.deeplake.adven_one.item.suit.modifiers.EnumModifier;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Random;

public class ItemGemSuit extends ItemBase implements IHasQuality, IHasModifiers {
    static final String NAME = "gem";
    final SetTier tier;
    public ItemGemSuit(SetTier tier) {
        super(String.format("%s_%s_%s",tier.getSuitName(),tier.getTier(),NAME));
        this.tier = tier;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format(Idealland.MODID+"."+NAME,
                I18n.format(tier.getTransKey()));
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!worldIn.isRemote && !canIdentify(stack))
        {
            if (needFirstTick(stack))
            {
                setQuality(stack, getRandomQuality(itemRand));
                storeAllToNBT(stack, getRandomModifierList(itemRand));
            }
        }
    }

    public HashMap<EnumModifier, Integer> getRandomModifierList(Random random)
    {
        HashMap<EnumModifier, Integer> result = new HashMap<>();

        int level = 1 + random.nextInt(3);//test level
        EnumModifier modifier = EnumModifier.values()[random.nextInt(EnumModifier.values().length)];
        result.put(modifier, modifier.getClampedLevel(level));

        return result;
    }

    public double getRandomQuality(Random random)
    {
        ModConfig.TierQualityConf conf;
        try {
            conf = ModConfig.TIER_CONF.TIER_QUALITY_CONF[tier.getTier()-1];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            conf = ModConfig.TIER_CONF.TIER_QUALITY_CONF[0];
        }

        if (conf == null)
        {
            return 1f;
        }

        return conf.MIN_GEM_QUALITY + random.nextFloat() * conf.DELTA_GEM_QUALITY;
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
}
