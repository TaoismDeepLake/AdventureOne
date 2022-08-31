package com.deeplake.adven_one.item;

import com.deeplake.adven_one.init.ModCreativeTabsList;
import com.deeplake.adven_one.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockBase extends ItemBlock implements IHasModel {
    public ItemBlockBase(Block block) {
        super(block);
//        setUnlocalizedName(name);
//        setRegistryName(name);
//        setCreativeTab(ModCreativeTabsList.IDL_MISC);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return block.getLocalizedName();
    }
}
