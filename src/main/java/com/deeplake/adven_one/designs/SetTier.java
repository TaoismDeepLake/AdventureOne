package com.deeplake.adven_one.designs;

import com.deeplake.adven_one.blocks.blockBasic.BlockOreBase;
import com.deeplake.adven_one.item.ItemArmorBase;
import com.deeplake.adven_one.item.ItemBase;
import com.deeplake.adven_one.item.ItemPickaxeBase;
import com.deeplake.adven_one.item.ItemSwordBase;
import com.deeplake.adven_one.item.armor.ItemArmorSuitBase;
import com.deeplake.adven_one.item.tools.ItemPickaxeSuitBase;
import com.deeplake.adven_one.item.weapon.ItemSwordSuitBase;
import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class SetTier {
    public static final int[] REDUCTION_AMOUNTS_DIAMOND = {3, 6, 8, 3};
    int tier;
    Item gem;
    Block gem_ore;
    Item sword;
    Item pick;

    Item armor0;
    Item armor1;
    Item armor2;
    Item armor3;

    Item.ToolMaterial toolMaterial;
    ItemArmor.ArmorMaterial armorMaterial;

    EnumSuit suit;
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

    public SetTier(int tier, String name, EnumSuit enumSuit) {
        this.tier = tier;
        this.suit = enumSuit;
        gem = new ItemBase(String.format("%s_%d_gem", name, tier));
        gem_ore = new BlockOreBase(String.format("%s_%d_ore", name, tier), gem);
//        sword = new ItemSwordBase(String.format("%s_%d_sword", name, tier), Item.ToolMaterial.DIAMOND);
//        pick = new ItemPickaxeBase(String.format("%s_%d_pickaxe", name, tier), Item.ToolMaterial.DIAMOND);
        toolMaterial = EnumHelper.addToolMaterial(name, tier, 100, 2*tier, 0.0f, 5 * tier).setRepairItem(new ItemStack(gem));
        armorMaterial = EnumHelper.addArmorMaterial(name, name, tier * 5, REDUCTION_AMOUNTS_DIAMOND, 6 + 2 * tier, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0).setRepairItem(new ItemStack(gem));
        sword = new ItemSwordSuitBase(this);
        pick = new ItemPickaxeSuitBase(this);

//        armor0 = new ItemArmorBase(String.format("%s_%d_armor0", name, tier), ItemArmor.ArmorMaterial.CHAIN, 0,EntityEquipmentSlot.HEAD);
//        armor1 = new ItemArmorBase(String.format("%s_%d_armor1", name, tier), ItemArmor.ArmorMaterial.CHAIN, 0,EntityEquipmentSlot.HEAD);
//        armor2 = new ItemArmorBase(String.format("%s_%d_armor2", name, tier), ItemArmor.ArmorMaterial.CHAIN, 0,EntityEquipmentSlot.HEAD);
//        armor3 = new ItemArmorBase(String.format("%s_%d_armor3", name, tier), ItemArmor.ArmorMaterial.CHAIN, 0,EntityEquipmentSlot.HEAD);
        armor0 = new ItemArmorSuitBase(this, EntityEquipmentSlot.HEAD);
        armor1 = new ItemArmorSuitBase(this, EntityEquipmentSlot.CHEST);
        armor2 = new ItemArmorSuitBase(this, EntityEquipmentSlot.LEGS);
        armor3 = new ItemArmorSuitBase(this, EntityEquipmentSlot.FEET);
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

    public Item.ToolMaterial getToolMaterial() {
        return toolMaterial;
    }

    public ItemArmor.ArmorMaterial getArmorMaterial() {
        return armorMaterial;
    }

    public EnumSuit getSuit() {
        return suit;
    }
}
