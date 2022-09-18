package com.deeplake.adven_one.entity.creatures.suit;

import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.entity.creatures.EntityMobRanged;
import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntitySuitMob extends EntityMobRanged {
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

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);
        if (melee_atk)
        {
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.WOODEN_AXE));
        }else {
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        }
    }

    public void dropTier3Gem(boolean wasRecentlyHit, int lootingModifier) {
        if (wasRecentlyHit)
        {
            EnumSuit suit = EnumSuit.getSuit(world, getPosition());
            SetTier tier = suit.getTierMap().get(3);
            if (tier != null)
            {
                Item gem = tier.getGem();
                if (gem != null)
                {
                    boolean extra = 1 + rand.nextInt(lootingModifier + 2) > 1;
                    dropItem(gem, extra ? 1 : 2);
                }
            }
        }
    }

    public void dropTier4Gem(boolean wasRecentlyHit, int lootingModifier) {
        if (wasRecentlyHit)
        {
            EnumSuit suit = EnumSuit.SET_CELESTIAL;
            SetTier tier = suit.getTierMap().get(4);
            if (tier != null)
            {
                Item gem = tier.getGem();
                if (gem != null)
                {
                    boolean extra = 1 + rand.nextInt(lootingModifier + 2) > 1;
                    dropItem(gem, extra ? 1 : 2);
                }
            }
        }
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
        dropItem(Items.BREAD, 1);
        if (rand.nextInt(10) == 0)
        {
            dropItem(Items.CARROT, 1);
        }

        if (rand.nextInt(10) == 0)
        {
            dropItem(Items.POTATO, 1);
        }
    }
}
