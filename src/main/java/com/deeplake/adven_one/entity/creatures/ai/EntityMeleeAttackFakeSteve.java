package com.deeplake.adven_one.entity.creatures.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;

public class EntityMeleeAttackFakeSteve extends EntityAIAttackMelee {
    public EntityMeleeAttackFakeSteve(EntityCreature creature, double speedIn, boolean useLongMemory) {
        super(creature, speedIn, useLongMemory);
    }

    //2.5sq = 6.25
    protected double getAttackReachSqr(EntityLivingBase attackTarget)
    {
        return 6.25;
    }
}
