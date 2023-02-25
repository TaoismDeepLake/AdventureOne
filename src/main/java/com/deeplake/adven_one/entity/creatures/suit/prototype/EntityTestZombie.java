package com.deeplake.adven_one.entity.creatures.suit.prototype;

import com.deeplake.adven_one.entity.creatures.suit.EntitySuitMob;
import net.minecraft.world.World;

public class EntityTestZombie extends EntitySuitMob {
    public EntityTestZombie(World worldIn) {
        super(worldIn);
        melee_atk = true;
    }

//    protected void firstTickAI() {
//        super.firstTickAI();
//        this.tasks.addTask(2, new EntityAIZombieAttack(this, 1.0D, true));
//    }
}
