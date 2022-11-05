package com.deeplake.adven_one.item.aichips;

import com.deeplake.adven_one.entity.creatures.EntityStrongAIMob;
import com.deeplake.adven_one.item.ItemBase;
import com.deeplake.adven_one.util.AIDef;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

import java.util.ArrayList;

import static com.deeplake.adven_one.entity.creatures.EntityStrongAIMob.AI_PRIOR_LIST;

public abstract class ItemAIChipBase extends ItemBase {
    public ItemAIChipBase(String name) {
        super(name);
    }

    public abstract int applyAI(ItemStack stack, EntityPlayer playerIn, EntityStrongAIMob target, int priority, NBTTagCompound compound);


    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if (target instanceof EntityStrongAIMob)
        {
            if (!playerIn.world.isRemote)
            {
                EntityStrongAIMob mob = (EntityStrongAIMob) target;
                int priority = getPriority(stack);

                ArrayList<Integer> priorities = new ArrayList<>();
                int[] rawArray = target.getEntityData().getIntArray(AI_PRIOR_LIST);
                boolean exist = false;
                for (int p : rawArray) {
                    priorities.add(p);
                    if (p == priority)
                    {
                        exist = true;
                    }
                }

                if (!exist)
                {
                    priorities.add(priority);
                    target.getEntityData().setIntArray(AI_PRIOR_LIST,
                            priorities.stream().mapToInt(Integer::intValue).toArray());
                }
                NBTTagCompound compound = new NBTTagCompound();
                int id = applyAI(stack,playerIn, (EntityStrongAIMob) target, priority, compound);
                compound.setInteger(AIDef.AI_ID, id);
                target.getEntityData().setTag(String.valueOf(priority), compound);
                stack.shrink(1);
            }
            return true;
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    protected int getPriority(ItemStack stack)
    {
        return IDLNBTUtil.GetInt(stack, AIDef.PRIORITY);
    }
}
