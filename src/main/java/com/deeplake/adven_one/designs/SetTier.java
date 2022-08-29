package com.deeplake.adven_one.designs;

import com.deeplake.adven_one.blocks.BlockBase;
import com.deeplake.adven_one.blocks.blockBasic.BlockOreBase;
import com.deeplake.adven_one.item.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;

public class SetTier {
    int tier;
    Item gem;
    Block gem_ore;
    Item sword;
    Item pick;

    Item armor0;
    Item armor1;
    Item armor2;
    Item armor3;

//    public SetTier(int tier, Item gem, Item sword, Item pick, Item armor0, Item armor1, Item armor2, Item armor3) {
//        this.tier = tier;
//        this.gem = gem;
//        this.sword = sword;
//        this.pick = pick;
//        this.armor0 = armor0;
//        this.armor1 = armor1;
//        this.armor2 = armor2;
//        this.armor3 = armor3;
//    }

    public SetTier(int tier, String name) {
        this.tier = tier;
        gem = new ItemBase(String.format("%s_%d_gem", name, tier));
        sword = new ItemBase(String.format("%s_%d_sword", name, tier));
        pick = new ItemBase(String.format("%s_%d_pickaxe", name, tier));
        armor0 = new ItemBase(String.format("%s_%d_armor0", name, tier));
        armor1 = new ItemBase(String.format("%s_%d_armor1", name, tier));
        armor2 = new ItemBase(String.format("%s_%d_armor2", name, tier));
        armor3 = new ItemBase(String.format("%s_%d_armor3", name, tier));
        gem_ore = new BlockOreBase(String.format("%s_%d_ore", name, tier), gem);
    }

    public int getTier() {
        return tier;
    }

    public Item getGem() {
        return gem;
    }

    public Item getSword() {
        return sword;
    }

    public Item getPick() {
        return pick;
    }

    public Item getHead() {
        return armor0;
    }

    public Item getChest() {
        return armor1;
    }

    public Item getLeg() {
        return armor2;
    }

    public Item getFeet() {
        return armor3;
    }

    public Item getArmor(EntityEquipmentSlot slot)
    {
        switch (slot)
        {
//            case MAINHAND:
//                break;
//            case OFFHAND:
//                break;
            case FEET:
                return armor3;
            case LEGS:
                return armor2;
            case CHEST:
                return armor1;
            case HEAD:
                return armor0;
            default:
                throw new IllegalStateException("Unexpected value: " + slot);
        }
    }
}
