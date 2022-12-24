package com.deeplake.adven_one.blocks.terrain;

import com.deeplake.adven_one.blocks.BlockBase;
import com.deeplake.adven_one.item.suit.modifiers.HandleWalker;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockMountainStone extends BlockBase {
    public BlockMountainStone(String name, Material material) {
        super(name, material);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB par5AxisAlignedBB, @Nonnull List<AxisAlignedBB> stacks, Entity par7Entity, boolean isActualState) {
        if (par7Entity != null)
        {
            if (par7Entity instanceof EntityLivingBase)
            {
                int lv = HandleWalker.getMountainWalkLevel(par7Entity);
                if (lv <= 0) {
                    //with walker, pass as if nothing
                    super.addCollisionBoxToList(state, world, pos, par5AxisAlignedBB, stacks, par7Entity, isActualState);
                }
            }
        }
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
}
