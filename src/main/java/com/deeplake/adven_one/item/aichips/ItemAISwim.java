package com.deeplake.adven_one.item.aichips;

import com.deeplake.adven_one.entity.creatures.EntityStrongAIMob;
import com.deeplake.adven_one.util.AIDef;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemAISwim extends ItemAIChipBase{
    public ItemAISwim(String name) {
        super(name);
    }

    //returns ID
    @Override
    public int applyAI(ItemStack stack, EntityPlayer playerIn, EntityStrongAIMob target, int priority, NBTTagCompound compound) {
        target.tasks.addTask(priority, new EntityAISwimming(target));
        return AIDef.ID_SWIM;
    }
}
