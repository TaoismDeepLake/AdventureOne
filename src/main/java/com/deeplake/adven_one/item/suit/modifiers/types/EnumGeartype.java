package com.deeplake.adven_one.item.suit.modifiers.types;

import net.minecraft.inventory.EntityEquipmentSlot;

public enum EnumGeartype {
    ALL(true,true,true,true,true,true),
    SWORD(false, true,false,false,false,false),
    PICKAXE(true, false,false,false,false,false),
    ARMOR_HELMET(false, false,false,false,false,true),
    ARMOR_CHEST(false, false,false,false,false,true),
    ARMOR_LEGGINGS(false, false,false,false,false,true),
    ARMOR_BOOTS(false, false,false,false,false,true);

    //todo: bow & wand

    boolean isTool;
    boolean isWeapon;
    boolean isMelee;
    boolean isRanged;
    boolean isMagic;

    boolean isArmor;

    EnumGeartype(boolean isTool, boolean isWeapon, boolean isMelee, boolean isRanged, boolean isMagic, boolean isArmor) {
        this.isTool = isTool;
        this.isWeapon = isWeapon;
        this.isMelee = isMelee;
        this.isRanged = isRanged;
        this.isMagic = isMagic;
        this.isArmor = isArmor;
    }

    public EnumGeartype getFromArmorSlot(EntityEquipmentSlot slot)
    {
        switch (slot)
        {
            case FEET:
                return ARMOR_BOOTS;
            case LEGS:
                return ARMOR_LEGGINGS;
            case CHEST:
                return ARMOR_CHEST;
            case HEAD:
                return ARMOR_HELMET;
            default:
                throw new IllegalStateException("Unexpected value: " + slot);
        }
    }

    public boolean isTool() {
        return isTool;
    }

    public boolean isWeapon() {
        return isWeapon;
    }

    public boolean isMelee() {
        return isMelee;
    }

    public boolean isRanged() {
        return isRanged;
    }

    public boolean isMagic() {
        return isMagic;
    }

    public boolean isArmor() {
        return isArmor;
    }
}
