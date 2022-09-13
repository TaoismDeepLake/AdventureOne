package com.deeplake.adven_one.entity.creatures.suit;

import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.designs.SetTier;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityTier2Mob extends EntitySuitMob{
    public EntityTier2Mob(World worldIn) {
        super(worldIn);
        setTierAll(2);
        melee_atk = true;
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
        dropTier2Gem(wasRecentlyHit, lootingModifier);
    }
}
