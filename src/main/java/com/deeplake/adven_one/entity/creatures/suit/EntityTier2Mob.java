package com.deeplake.adven_one.entity.creatures.suit;

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
        dropTier3Gem(wasRecentlyHit, lootingModifier);
    }
}
