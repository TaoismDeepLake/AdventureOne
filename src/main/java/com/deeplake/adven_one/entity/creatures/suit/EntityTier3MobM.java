package com.deeplake.adven_one.entity.creatures.suit;

import net.minecraft.world.World;

public class EntityTier3MobM extends EntitySuitMob{
    public EntityTier3MobM(World worldIn) {
        super(worldIn);
        setAtkTier(3);
        setDefTier(2);
    }
}
