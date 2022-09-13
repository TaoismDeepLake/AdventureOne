package com.deeplake.adven_one.entity.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityEnemyBullet extends EntityIdlProjectile {
    protected EntityEnemyBullet(World worldIn)
    {
        //dont call this directly
        super(worldIn);
    }

    public EntityEnemyBullet(World worldIn, ProjectileArgs args, EntityLivingBase shooter, double accelX, double accelY, double accelZ)
    {
        super(worldIn, args, shooter, accelX, accelY, accelZ, 0.1f);
    }

    public EntityEnemyBullet(World worldIn, ProjectileArgs args, EntityLivingBase shooter, double accelX, double accelY, double accelZ, float acceleration)
    {
        super(worldIn, args, shooter, accelX, accelY, accelZ, acceleration);
    }
}
