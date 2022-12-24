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

import javax.annotation.Nonnull;
import java.util.List;

public class BlockSwampSink extends BlockBase {
    public BlockSwampSink(String name, Material material) {
        super(name, material);
    }

    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (entityIn instanceof EntityLivingBase)
        {
            EntityLivingBase livingBase = (EntityLivingBase) entityIn;
            int lv = HandleWalker.getSwampWalkLevel(entityIn);
            if (lv <= 0)
            {
                EntityUtil.ApplyBuff(livingBase, ModPotions.TERRAIN_HINDER, 2, 1f);
            }
        }
        else {
            //todo: maybe not a good idea
            entityIn.setInWeb();
        }
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB par5AxisAlignedBB, @Nonnull List<AxisAlignedBB> stacks, Entity par7Entity, boolean isActualState) {
        if (par7Entity != null)
        {
            if (par7Entity instanceof EntityLivingBase)
            {
                int lv = HandleWalker.getSwampWalkLevel(par7Entity);
                if (lv > 0 && !par7Entity.isSneaking()) {
                    //walk as if solid, unless sneaking
                    super.addCollisionBoxToList(state, world, pos, par5AxisAlignedBB, stacks, par7Entity, isActualState);
                }
            }
        }
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
