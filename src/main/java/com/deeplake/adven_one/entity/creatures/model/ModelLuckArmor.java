package com.deeplake.adven_one.entity.creatures.model;// Made with Blockbench 4.4.1
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;



public class ModelLuckArmor extends ModelBiped {
	private final ModelRenderer bone2;
	private final ModelRenderer cube_r1;

	public ModelLuckArmor() {
		textureWidth = 128;
		textureHeight = 128;

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, 24.25F, -1.25F);
		setRotationAngle(bone2, -3.098F, 0.0F, -3.1416F);
		bone2.cubeList.add(new ModelBox(bone2, 0, 64, -5.25F, -33.0F, -5.5F, 11, 0, 11, 0.0F, false));
		bone2.cubeList.add(new ModelBox(bone2, 0, 75, -3.25F, -37.0F, -2.5F, 7, 4, 6, 0.0F, false));

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone2.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.1309F, 0.0F, 0.0F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 0, 64, -0.95F, -35.75F, -2.5F, 2, 2, 2, 0.0F, false));
		cube_r1.cubeList.add(new ModelBox(cube_r1, 26, 80, -1.75F, -36.5F, -8.5F, 4, 2, 3, 0.0F, false));

		bipedHead.addChild(bone2);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}