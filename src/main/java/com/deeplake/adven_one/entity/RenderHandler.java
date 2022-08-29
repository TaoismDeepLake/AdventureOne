package com.deeplake.adven_one.entity;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.entity.creatures.moroon.EntityMoroonUnitBase;
import com.deeplake.adven_one.entity.creatures.render.RenderBullet;
import com.deeplake.adven_one.entity.creatures.render.RenderMoroonHumanoid;
import com.deeplake.adven_one.entity.projectiles.EntityIdlProjectile;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {

    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityMoroonUnitBase.class, RenderMoroonHumanoid::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityIdlProjectile.class, renderManager -> new RenderBullet<>(renderManager, new ResourceLocation(Idealland.MODID,
                "textures/entity/projectiles/bullet_norm.png")));
    }
}
