package com.deeplake.adven_one.entity.creatures.suit;

import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityTier3Mob extends EntitySuitMob{
    public EntityTier3Mob(World worldIn) {
        super(worldIn);
        setTierAll(2);
        melee_atk = true;
        tier = 3;
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
        dropTier4Gem(wasRecentlyHit, lootingModifier);
    }
}
