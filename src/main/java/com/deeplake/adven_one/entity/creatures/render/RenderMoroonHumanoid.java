package com.deeplake.adven_one.entity.creatures.render;

import com.deeplake.adven_one.entity.creatures.EntityModUnit;
import com.deeplake.adven_one.util.Reference;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMoroonHumanoid extends RenderBiped<EntityModUnit> {
    private static final ResourceLocation DEFAULT_RES_LOC = new ResourceLocation(Reference.MOD_ID + ":textures/entity/moroon_humanoid.png");

    public RenderMoroonHumanoid(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelPlayer(1f, false), 0.5F);
        this.addLayer(new LayerBipedArmor(this));
    }


    public RenderMoroonHumanoid(RenderManager renderManagerIn, ModelBiped modelBipedIn, float shadowSize) {
        super(renderManagerIn, modelBipedIn, shadowSize);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityModUnit entity)
    {
        return DEFAULT_RES_LOC;
    }
}
