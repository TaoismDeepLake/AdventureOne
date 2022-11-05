package com.deeplake.adven_one.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class AIDef {

    public static final String SPEED = "speed";
    public static final String COOLDOWN = "cd";
    public static final String RANGE = "range";
    public static final String AI_ID = "ai_id";
    public static final String TYPE = "type";
    public static final String PRIORITY = "priority";
    public static final int ID_SWIM = 1;
    public static final int ID_MELEE = 2;
    public static final int ID_BOW = 3;
    public static final int ID_RANGED = 4;
    public static final int ID_WANDER_LAND = 5;
    public static final int ID_WATCH = 6;
    public static final int ID_IDLE = 7;
    public static final int ID_MOVE_VILLAGE = 8;
    public static final int ID_EATGRASS = 9;
    public static final int ID_FLEE_SUN = 10;
    public static final int ID_LEAP = 11;
    public static final int ID_MOVE_TARGET = 12;
    public static final int ID_OPEN_DOOR = 14;
    public static final int ID_AVOID = 15;
    public static final int INT_TEMPT = 16;
    public static final int ID_PANIC = 17;
    public static final int ID_INDOOR = 18;
    public static final int ID_BREAK_DOOR = 19;

    //todo: figure out how mob spawners store this.
    public static Class<? extends Entity> getEntityFromID(int id)
    {
        switch (id)
        {
            case 0:
                return EntityPlayer.class;

            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
    }
}
