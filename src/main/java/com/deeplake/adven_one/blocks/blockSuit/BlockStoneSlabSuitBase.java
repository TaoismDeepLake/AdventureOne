package com.deeplake.adven_one.blocks.blockSuit;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.blocks.ModBlocks;
import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.init.ModCreativeTabsList;
import com.deeplake.adven_one.item.ItemSlabBase;
import com.deeplake.adven_one.item.ModItems;
import com.deeplake.adven_one.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStoneSlabSuitBase extends BlockSlab implements IHasModel, IBlockSuit{
    private final boolean isDouble;
    public Block dropBlock;
    private final BlockSlab doubleSlab;
    static final String NAME = "stone_slab";
    EnumSuit suit;

    public static final PropertyEnum<BlockStoneSlabSuitBase.Variant> VARIANT = PropertyEnum.create("variant", BlockStoneSlabSuitBase.Variant.class);

    public BlockStoneSlabSuitBase(EnumSuit suit, boolean isDouble, BlockSlab doubleSlab)
    {
        super(Material.ROCK, MapColor.STONE);

        this.doubleSlab = doubleSlab;
        this.isDouble = isDouble;

        String name = String.format("%s_"+NAME,suit.getName());
        if (this.isDouble) { name = String.format("%s_"+NAME+"_double",suit.getName()); }

        this.setRegistryName(name);
        this.setUnlocalizedName(name);

        setHardness(2.0F);
        setResistance(10.0F);
        setSoundType(SoundType.STONE);
        this.suit = suit;

        setCreativeTab(ModCreativeTabsList.IDL_MISC);

        ModBlocks.BLOCKS.add(this);

        ItemBlock itemBlock = new ItemSlabBase(this, this, doubleSlab);
        IBlockState iblockstate = this.blockState.getBaseState();
        if (!this.isDouble())
        {
            this.dropBlock = this;
            iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
            ModItems.ITEMS.add(itemBlock.setRegistryName(String.format("%s_"+NAME,suit.getName())));
        }

        setDefaultState(iblockstate.withProperty(VARIANT, BlockStoneSlabSuitBase.Variant.DEFAULT));


        useNeighborBrightness = true;
    }

    @Override
    public void registerModels() {
        if(!this.isDouble()) Idealland.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }


    public BlockStoneSlabSuitBase setDropped(Block dropBlockIn){
        this.dropBlock = dropBlockIn;
        return this;
    }

    @Override
    protected BlockStateContainer createBlockState() {
//      return new BlockStateContainer(this, HALF);
        return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, HALF, VARIANT);
    }

    public String getUnlocalizedName(int meta)
    {
        return super.getUnlocalizedName();
    }

    @Override
    public String getLocalizedName() {
        return I18n.format(Idealland.MODID+"."+NAME,
                I18n.format(Idealland.MODID+"."+suit.getName()));
    }

    public IProperty<?> getVariantProperty()
    {
        return VARIANT;
    }

    @Override
    public boolean isDouble() { return isDouble; }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return BlockStoneSlabSuitBase.Variant.DEFAULT;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
//        return isDouble() ? 0 : state.getValue(HALF).ordinal() + 1;
        int i = 0;

        if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
        {
            i |= 8;
        }

        return i;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
//      return !isDouble() ? getDefaultState().withProperty(HALF, EnumBlockHalf.values()[meta % EnumBlockHalf.values().length]) : getDefaultState();

        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, BlockStoneSlabSuitBase.Variant.DEFAULT);
        if (!this.isDouble())
        {
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }
        return iblockstate;
    }


    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(dropBlock);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        if(this.isDouble()){
            drops.add(new ItemStack(dropBlock, 2));
        }
        else {
            drops.add(new ItemStack(dropBlock, 1));
        }
    }

    public enum Variant implements IStringSerializable
    {
        DEFAULT;

        public String getName()
        {
            return "default";
        }
    }
}
