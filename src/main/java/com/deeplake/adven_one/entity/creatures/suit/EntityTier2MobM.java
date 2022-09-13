package com.deeplake.adven_one.entity.creatures.suit;

import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityTier2MobM extends EntitySuitMob{
    public EntityTier2MobM(World worldIn) {
        super(worldIn);
        setAtkTier(2);
        setDefTier(1);
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
        dropTier2Gem(wasRecentlyHit, lootingModifier);
    }
}
