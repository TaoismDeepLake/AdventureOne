package com.deeplake.adven_one.blocks.blockSuit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.blocks.BlockBase;
import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.item.suit.modifiers.HandleWalker;
import com.deeplake.adven_one.util.IHasModel;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockLogSuitBase extends BlockBase implements IHasModel, IBlockSuit {
    public static final PropertyEnum<BlockLog.EnumAxis> LOG_AXIS = PropertyEnum.<BlockLog.EnumAxis>create("axis", BlockLog.EnumAxis.class);
    static final String NAME = "log";
    EnumSuit suit;
    public BlockLogSuitBase(EnumSuit suit)
    {
        super(String.format("%s_"+NAME,suit.getName()), Material.WOOD);

        setHardness(2.0F);
        setResistance(5.0F);
        setSoundType(SoundType.WOOD);
        this.suit = suit;
        this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
    }

    @Override
    public String getLocalizedName() {
        return I18n.format(Idealland.MODID+"."+NAME,
                I18n.format(Idealland.MODID+"."+suit.getName()));
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta).withProperty(LOG_AXIS, BlockLog.EnumAxis.fromFacingAxis(facing.getAxis()));
    }

    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:

                switch (state.getValue(LOG_AXIS))
                {
                    case X:
                        return state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                    case Z:
                        return state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                    default:
                        return state;
                }

            default:
                return state;
        }
    }

    @Override public boolean canSustainLeaves(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }
    @Override public boolean isWood(net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }

    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState();

        switch (meta & 12)
        {
            case 0:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                break;
            case 8:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                break;
            default:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
        }

        return iblockstate;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @SuppressWarnings("incomplete-switch")
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        switch (state.getValue(LOG_AXIS))
        {
            case X:
                i |= 4;
                break;
            case Z:
                i |= 8;
                break;
            case NONE:
                i |= 12;
        }

        return i;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, LOG_AXIS);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB par5AxisAlignedBB, @Nonnull List<AxisAlignedBB> stacks, Entity par7Entity, boolean isActualState) {
        if (par7Entity != null)
        {
            //allows passing through log blocks
            if (par7Entity instanceof EntityLivingBase)
            {
                int lv = HandleWalker.getForestWalkLevel(par7Entity);
                if (lv <= 0) {
                    //walk as if solid, unless sneaking
                    super.addCollisionBoxToList(state, world, pos, par5AxisAlignedBB, stacks, par7Entity, isActualState);
                }
            }
        }
    }
}
