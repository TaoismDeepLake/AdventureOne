package com.deeplake.adven_one.entity.creatures.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAttackMelee;

public class EntityModAIZombieAttack extends EntityAIAttackMelee {
    public EntityModAIZombieAttack(EntityCreature creature, double speedIn, boolean useLongMemory) {
        super(creature, speedIn, useLongMemory);
    }
    private int raiseArmTicks;
    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        super.startExecuting();
        this.raiseArmTicks = 0;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        super.resetTask();
//        this.attacker.setArmsRaised(false);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        super.updateTask();
        ++this.raiseArmTicks;

//        if (this.raiseArmTicks >= 5 && this.attackTick < 10)
//        {
//            this.attacker.setArmsRaised(true);
//        }
//        else
//        {
//            this.attacker.setArmsRaised(false);
//        }
    }
}
