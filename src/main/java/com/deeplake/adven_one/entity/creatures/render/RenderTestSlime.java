package com.deeplake.adven_one.entity.creatures.render;

import com.deeplake.adven_one.entity.creatures.EntityModUnit;
import com.deeplake.adven_one.entity.creatures.render.layer.LayerModSlime;
import com.deeplake.adven_one.util.Reference;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTestSlime extends RenderLiving<EntityModUnit> {

    private ResourceLocation RES_LOC;
    private static final ResourceLocation SLIME_TEXTURES = new ResourceLocation("textures/entity/slime/slime.png");

    public RenderTestSlime(RenderManager p_i47193_1_, String TexturePath)
    {
        super(p_i47193_1_, new ModelSlime(16), 0.25F);
        this.addLayer(new LayerModSlime(this));
        RES_LOC = new ResourceLocation(Reference.MOD_ID + ":textures/entity/"+TexturePath+".png");
    }

//    /**
//     * Renders the desired {@code T} type Entity.
//     */
//    public void doRender(EntityModUnit entity, double x, double y, double z, float entityYaw, float partialTicks)
//    {
//        this.shadowSize = 0.25F * (float)entity.getSlimeSize();
//        super.doRender(entity, x, y, z, entityYaw, partialTicks);
//    }

//    /**
//     * Allows the render to do state modifications necessary before the model is rendered.
//     */
//    protected void preRenderCallback(EntityModUnit entitylivingbaseIn, float partialTickTime)
//    {
//        float f = 0.999F;
//        GlStateManager.scale(0.999F, 0.999F, 0.999F);
//        float f1 = (float)entitylivingbaseIn.getSlimeSize();
//        float f2 = (entitylivingbaseIn.prevSquishFactor + (entitylivingbaseIn.squishFactor - entitylivingbaseIn.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
//        float f3 = 1.0F / (f2 + 1.0F);
//        GlStateManager.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
//    }
    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityModUnit entity)
    {
        return RES_LOC;
    }
}
