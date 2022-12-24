package com.deeplake.adven_one.blocks.terrain;

import com.deeplake.adven_one.blocks.BlockBase;
import com.deeplake.adven_one.item.suit.modifiers.HandleWalker;
import com.deeplake.adven_one.potion.ModPotions;
import com.deeplake.adven_one.util.EntityUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockLeavesWooded extends BlockBase {
    public BlockLeavesWooded(String name, Material material) {
        super(name, material);
    }

    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (entityIn instanceof EntityLivingBase)
        {
            EntityLivingBase livingBase = (EntityLivingBase) entityIn;
            int lv = HandleWalker.getForestWalkLevel(entityIn);
            if (lv <= 0)
            {
                EntityUtil.ApplyBuff(livingBase, ModPotions.TERRAIN_HINDER, 1, 2f);
            }
        }
        else {
            //todo: maybe not a good idea
            entityIn.setInWeb();
        }
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
