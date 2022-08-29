package com.deeplake.adven_one.blocks.blockBasic;

import com.deeplake.adven_one.init.RegistryHandler;
import com.deeplake.adven_one.util.CommonFunctions;
import com.deeplake.adven_one.util.IHasModel;
import com.deeplake.adven_one.util.PlayerUtil;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockOreBase extends BlockOre implements IHasModel {
    boolean isSolid = true;
    boolean canPickup = false;
    Item dropDefault;

    public BlockOreBase(String name) {
        super();
        CommonFunctions.init(this, name);
        addToItems();
    }

    public BlockOreBase(String name, Item dropDefault) {
        super();
        CommonFunctions.init(this, name);
        addToItems();
        this.dropDefault = dropDefault;
    }

    public void addToItems() {
        RegistryHandler.addToItems(this);
    }

    public BlockOreBase setHarvestThis(String toolClass, int level) {
        setHarvestLevel(toolClass, level);
        return this;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public BlockOreBase setSolid(boolean isSolid) {
        this.isSolid = isSolid;
        return this;
    }

    public BlockOreBase setPickable(boolean val) {
        this.canPickup = val;
        return this;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (canPickup) {
            if (!worldIn.isRemote) {
                PlayerUtil.giveToPlayer(playerIn, new ItemStack(this, 1, getMetaFromState(state)));
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            }

            return true;
        } else {
            return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
        }
    }

    public boolean causesSuffocation(IBlockState state) {
        return isSolid;
    }

    @Deprecated
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        if (isSolid) {
            return blockState.getBoundingBox(worldIn, pos);
        } else {
            return null;
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (dropDefault != null) {
            return dropDefault;
        }
        //If you call this with a non-this-block state, will cause dead-loop if unhandled!
        return super.getItemDropped(state, rand, fortune);
    }

    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        if (fortune > random.nextInt(10))
        {
            return this.quantityDropped(random) + 1;
        }
        else
        {
            return this.quantityDropped(random);
        }
    }
}
