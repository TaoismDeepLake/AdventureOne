package com.deeplake.adven_one.entity.creatures.render;

import com.deeplake.adven_one.entity.creatures.EntityModUnit;
import com.deeplake.adven_one.entity.creatures.model.ModelModSkeleton;
import com.deeplake.adven_one.util.Reference;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTestSkeleton extends RenderLiving<EntityModUnit>{
    private ResourceLocation RES_LOC;
    private static final ResourceLocation DEFAULT_RES_LOC = new ResourceLocation(  ":textures/entity/steve.png");

    public RenderTestSkeleton(RenderManager renderManagerIn, String TexturePath)
    {
        super(renderManagerIn, new ModelModSkeleton(), 0.5F);
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelSkeleton(0.5F, true);
                this.modelArmor = new ModelSkeleton(1.0F, true);
            }
        });
        RES_LOC = new ResourceLocation(Reference.MOD_ID + ":textures/entity/"+TexturePath+".png");
    }
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityModUnit entity)
    {
        return RES_LOC;
    }
}