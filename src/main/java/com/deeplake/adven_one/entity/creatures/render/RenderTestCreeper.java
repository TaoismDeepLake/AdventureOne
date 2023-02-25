package com.deeplake.adven_one.entity.creatures.render;

import com.deeplake.adven_one.entity.creatures.EntityModUnit;
import com.deeplake.adven_one.util.Reference;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTestCreeper extends RenderLiving<EntityModUnit>{
    private ResourceLocation RES_LOC;
    private static final ResourceLocation DEFAULT_RES_LOC = new ResourceLocation(  ":textures/entity/steve.png");


    public RenderTestCreeper(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelCreeper(), 0.5F);
//        this.addLayer(new LayerCreeperCharge(this));
        RES_LOC = DEFAULT_RES_LOC;
    }

    public RenderTestCreeper(RenderManager renderManagerIn, String TexturePath)
    {
        super(renderManagerIn, new ModelCreeper(), 0.5F);
//        this.addLayer(new LayerCreeperCharge(this));
        RES_LOC = new ResourceLocation(Reference.MOD_ID + ":textures/entity/"+TexturePath+".png");
    }

//    /**
//     * Allows the render to do state modifications necessary before the model is rendered.
//     */
//    protected void preRenderCallback(EntityModUnit entitylivingbaseIn, float partialTickTime)
//    {
//        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
//        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
//        f = MathHelper.clamp(f, 0.0F, 1.0F);
//        f = f * f;
//        f = f * f;
//        float f2 = (1.0F + f * 0.4F) * f1;
//        float f3 = (1.0F + f * 0.1F) / f1;
//        GlStateManager.scale(f2, f3, f2);
//    }
//
//    /**
//     * Gets an RGBA int color multiplier to apply.
//     */
//    protected int getColorMultiplier(EntityModUnit entitylivingbaseIn, float lightBrightness, float partialTickTime)
//    {
//        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
//
//        if ((int)(f * 10.0F) % 2 == 0)
//        {
//            return 0;
//        }
//        else
//        {
//            int i = (int)(f * 0.2F * 255.0F);
//            i = MathHelper.clamp(i, 0, 255);
//            return i << 24 | 822083583;
//        }
//    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityModUnit entity)
    {
        return RES_LOC;
    }
}