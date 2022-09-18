package com.deeplake.adven_one.entity.creatures.suit;

import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityTier3MobM extends EntitySuitMob{
    public EntityTier3MobM(World worldIn) {
        super(worldIn);
        setAtkTier(3);
        setDefTier(2);
        tier = 3;
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
        dropTier4Gem(wasRecentlyHit, lootingModifier);
    }
}
