package com.deeplake.adven_one.blocks.blockSuit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.blocks.BlockBase;
import com.deeplake.adven_one.blocks.ModBlocks;
import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.init.ModCreativeTabsList;
import com.deeplake.adven_one.item.ItemBlockBase;
import com.deeplake.adven_one.item.ModItems;
import com.deeplake.adven_one.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockTallGrassSuitBase extends BlockBush implements IHasModel, IBlockSuit, net.minecraftforge.common.IShearable{
    static final String NAME = "tall_grass";
    EnumSuit suit;

    protected static final AxisAlignedBB TALL_GRASS_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.8D, 0.9D);


    public BlockTallGrassSuitBase(EnumSuit suit)
    {
        super(Material.VINE);
        setSoundType(SoundType.PLANT);

        this.setRegistryName(String.format("%s_"+NAME,suit.getName()));
        this.setUnlocalizedName(String.format("%s_"+NAME,suit.getName()));

        ModBlocks.BLOCKS.add(this);
        if (this instanceof IBlockSuit)
        {
            ModItems.ITEMS.add(new ItemBlockBase(this).setRegistryName(this.getRegistryName()));
        }
        else {
            ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
        }

        this.setCreativeTab(ModCreativeTabsList.IDL_MISC);
        this.suit = suit;
    }

    @Override
    public String getLocalizedName() {
        return I18n.format(Idealland.MODID+"."+NAME,
                I18n.format(Idealland.MODID+"."+suit.getName()));
    }

    @Override
    public void registerModels() {
        Idealland.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        IBlockState soil = worldIn.getBlockState(pos.down());
        return soil.getBlock() == suit.getGrass();
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        return this.canSustainBush(worldIn.getBlockState(pos.down()));
    }

    protected boolean canSustainBush(IBlockState state)
    {
        return state.getBlock() == suit.getGrass();
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return TALL_GRASS_AABB;
    }

    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        if(rand.nextFloat() < 0.4)
        {
            return ModItems.FIBRE;
        }
        else return null;
    }

    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (!worldIn.isRemote && stack.getItem() == Items.SHEARS)
        {
            player.addStat(StatList.getBlockStats(this));
            spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(suit.getTallGrass()), 1));
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this, 1);
    }

    public Block.EnumOffsetType getOffsetType()
    {
        return Block.EnumOffsetType.XYZ;
    }

    @Override public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos){ return true; }
    @Override
    public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        return NonNullList.withSize(1, new ItemStack(Item.getItemFromBlock(suit.getTallGrass()), 1));
    }
}
