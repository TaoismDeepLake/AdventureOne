package com.deeplake.adven_one.item.aichips;

import com.deeplake.adven_one.entity.creatures.EntityStrongAIMob;
import com.deeplake.adven_one.util.AIDef;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemAIMelee extends ItemAIChipBase{
    public ItemAIMelee(String name) {
        super(name);
    }

    @Override
    public int applyAI(ItemStack stack, EntityPlayer playerIn, EntityStrongAIMob target, int priority, NBTTagCompound compound) {
        target.tasks.addTask(priority, new EntityAIAttackMelee(target, 1, false));
        return AIDef.ID_MELEE;
    }
}
