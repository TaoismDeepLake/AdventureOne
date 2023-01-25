package com.deeplake.adven_one.blocks.blockSuit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.blocks.BlockBase;
import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.util.IHasModel;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.Random;

public class BlockGrassSuitBase extends BlockBase implements IHasModel, IBlockSuit{
    static final String NAME = "grass";
    EnumSuit suit;
    public BlockGrassSuitBase(EnumSuit suit)
    {
        super(String.format("%s_"+NAME,suit.getName()), Material.GROUND);

        setHardness(0.5F);
        setHarvestLevel("shovel", 0);
        setSoundType(SoundType.PLANT);
        this.lightOpacity = 256;
        this.suit = suit;
    }

    @Override
    public String getLocalizedName() {
        return I18n.translateToLocalFormatted(Idealland.MODID+"."+NAME,
                I18n.translateToLocalFormatted(Idealland.MODID+"."+suit.getName()));
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            if (!worldIn.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2)
            {
                worldIn.setBlockState(pos, suit.getDirt().getDefaultState());
            }
            else
            {
                if (worldIn.getLightFromNeighbors(pos.up()) >= 9)
                {
                    for (int i = 0; i < 4; ++i)
                    {
                        BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

                        if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos))
                        {
                            return;
                        }

                        IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
                        IBlockState iblockstate1 = worldIn.getBlockState(blockpos);

                        if (iblockstate1.getBlock() == suit.getDirt() && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity(worldIn, pos.up()) <= 2)
                        {
                            worldIn.setBlockState(blockpos, suit.getGrass().getDefaultState());
                        }
                    }
                }
            }
        }
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(suit.getDirt());
    }
}
