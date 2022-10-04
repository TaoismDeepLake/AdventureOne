package com.deeplake.adven_one.item.food;

import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import com.deeplake.adven_one.util.EntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.UUID;

public class ItemFoodCost extends ItemFoodBase{
    static final UUID FOOD_COST = UUID.fromString("3ffdd93b-f040-2065-8eab-b048a5f01829");
    final int cost;
    public ItemFoodCost(String name, int cost) {
        super(name, 1, 0.6f, false);
        setAlwaysEdible();
        this.cost = cost;
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        super.onFoodEaten(stack, worldIn, player);
        EntityUtil.boostAttr(player, ModAttributes.COST, cost, FOOD_COST);
    }
}
