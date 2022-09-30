package com.deeplake.adven_one.item.suit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.item.ItemBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class ItemGemSuit extends ItemBase implements IHasQuality {
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
        if (!worldIn.isRemote && stack.hasTagCompound())
        {
            if (needFirstTick(stack))
            {
                setQuality(stack, getRandomQuality(itemRand));
            }
        }
    }

    public double getRandomQuality(Random random)
    {
        ModConfig.EquipAttrConf conf;
        try {
            conf = ModConfig.TIER_CONF.EQUIP_ATTR_CONF[tier.getTier()-1];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            conf = ModConfig.TIER_CONF.EQUIP_ATTR_CONF[0];
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
}
