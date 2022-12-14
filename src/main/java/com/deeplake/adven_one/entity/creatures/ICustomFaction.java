package com.deeplake.adven_one.entity.creatures;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.util.EntityUtil;
import net.minecraft.entity.Entity;

import static com.deeplake.adven_one.entity.creatures.EntityModUnit.FACTION_TEAM;

public interface ICustomFaction {
    default EntityUtil.EnumFaction getFaction() {
        if (this instanceof EntityModUnit) {
            return EntityUtil.EnumFaction.fromIndex(((Entity) this).getDataManager().get(FACTION_TEAM));
        }
        return EntityUtil.EnumFaction.CRITTER;
    }

    default void setFaction(Byte index) {
        setFaction(EntityUtil.EnumFaction.fromIndex(index));
    }

    default void setFaction(EntityUtil.EnumFaction faction) {
        if (this instanceof EntityModUnit) {
            ((Entity) this).getDataManager().set(FACTION_TEAM, faction.index);

            ((EntityModUnit) this).resetAttackFaction();
        } else {
            Idealland.LogWarning("Trying to set faction for wrong type");
        }
    }
}
