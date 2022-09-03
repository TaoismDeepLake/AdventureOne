package com.deeplake.adven_one.entity.creatures.suit;

import net.minecraft.world.World;

public class EntityTier2MobM extends EntitySuitMob{
    public EntityTier2MobM(World worldIn) {
        super(worldIn);
        setAtkTier(2);
        setDefTier(1);
    }
}
