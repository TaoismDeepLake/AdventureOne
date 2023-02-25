package com.deeplake.adven_one.entity.creatures.suit.prototype;

import com.deeplake.adven_one.entity.creatures.suit.EntitySuitMob;
import net.minecraft.world.World;

public class EntityTestSkeleton extends EntitySuitMob {
    public EntityTestSkeleton(World worldIn) {
        super(worldIn);
        melee_atk = false;
    }

}
