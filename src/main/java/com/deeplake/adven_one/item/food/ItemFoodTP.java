package com.deeplake.adven_one.item.food;

import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.util.Teleport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFoodTP extends ItemFoodBase
{
    public ItemFoodTP(String name, int amount, float saturation, boolean isWolfFood) {
        super(name, amount, saturation, isWolfFood);
        setAlwaysEdible();
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer sender) {
        super.onFoodEaten(stack, worldIn, sender);
        if (!worldIn.isRemote)
        {
            int providerDimension = sender.getEntityWorld().provider.getDimension();
            if (providerDimension != ModConfig.WORLD_GEN_CONF.DIM_ONE_ID)
            {
                Teleport.teleportToDim(sender, 0, sender.posX, sender.posY, sender.posZ);
            }
            else {
                Teleport.teleportToDim(sender, ModConfig.WORLD_GEN_CONF.DIM_ONE_ID, sender.posX, sender.posY, sender.posZ);
            }
        }
    }
}
