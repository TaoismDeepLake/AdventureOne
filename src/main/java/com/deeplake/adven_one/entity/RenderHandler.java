package com.deeplake.adven_one.entity;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.entity.creatures.boss.EntityBossSteve;
import com.deeplake.adven_one.entity.creatures.render.*;
import com.deeplake.adven_one.entity.creatures.suit.EntitySuitMob;
import com.deeplake.adven_one.entity.creatures.suit.prototype.EntityTestCreeper;
import com.deeplake.adven_one.entity.creatures.suit.prototype.EntityTestSkeleton;
import com.deeplake.adven_one.entity.creatures.suit.prototype.EntityTestSlime;
import com.deeplake.adven_one.entity.creatures.suit.prototype.EntityTestZombie;
import com.deeplake.adven_one.entity.projectiles.EntityIdlProjectile;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {

    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntitySuitMob.class, RenderMoroonHumanoid::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBossSteve.class, RenderModUnit::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityTestSkeleton.class, renderManager -> new RenderTestSkeleton(renderManager, "suit_test_3_skeleton"));
        RenderingRegistry.registerEntityRenderingHandler(EntityTestZombie.class, renderManager -> new RenderTestZombie(renderManager, "suit_test_3_zombie"));
        RenderingRegistry.registerEntityRenderingHandler(EntityTestCreeper.class, renderManager -> new RenderTestCreeper(renderManager, "suit_test_3_creeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityTestSlime.class, renderManager -> new RenderTestSlime(renderManager, "suit_test_3_slime"));

        RenderingRegistry.registerEntityRenderingHandler(EntityIdlProjectile.class, renderManager -> new RenderBullet<>(renderManager, new ResourceLocation(Idealland.MODID,
                "textures/entity/projectiles/bullet_norm.png")));
    }
}
