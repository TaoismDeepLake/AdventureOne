package com.deeplake.adven_one.entity.creatures.render;

import net.minecraft.client.renderer.entity.RenderManager;

public class RenderTestZombie extends RenderHumanoid{

    public RenderTestZombie(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    public RenderTestZombie(RenderManager renderManagerIn, String TexturePath) {
        super(renderManagerIn, TexturePath);
    }
}
