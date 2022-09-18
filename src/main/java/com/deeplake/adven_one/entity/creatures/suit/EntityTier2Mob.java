package com.deeplake.adven_one.entity.creatures.suit;

import com.deeplake.adven_one.init.ModConfig;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityTier2Mob extends EntitySuitMob{

    public EntityTier2Mob(World worldIn) {
        super(worldIn);
        setTierAll(2);
        melee_atk = true;
        tier = 2;
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
        dropTier3Gem(wasRecentlyHit, lootingModifier);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

    }


}
