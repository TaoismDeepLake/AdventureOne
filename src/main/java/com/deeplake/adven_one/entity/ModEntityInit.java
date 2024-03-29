package com.deeplake.adven_one.entity;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.entity.creatures.boss.EntityBossSteve;
import com.deeplake.adven_one.entity.creatures.suit.*;
import com.deeplake.adven_one.entity.creatures.suit.prototype.EntityTestCreeper;
import com.deeplake.adven_one.entity.creatures.suit.prototype.EntityTestSkeleton;
import com.deeplake.adven_one.entity.creatures.suit.prototype.EntityTestSlime;
import com.deeplake.adven_one.entity.creatures.suit.prototype.EntityTestZombie;
import com.deeplake.adven_one.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntityInit {
    private static int ENTITY_NEXT_ID = 1;
    public static void registerEntities()
    {
        registerEntity("debug_mob", EntitySuitMob.class);
        registerEntity("default_mob_t1", EntityTier1Mob.class);
        registerEntity("default_mob_t1m", EntityTier1MobM.class);
        registerEntity("default_mob_t2", EntityTier2Mob.class);
        registerEntity("default_mob_t2m", EntityTier2MobM.class);
        registerEntity("default_mob_t3", EntityTier3Mob.class);
        registerEntity("default_mob_t3m", EntityTier3MobM.class);

        registerEntity("debug_creeper", EntityTestCreeper.class);
        registerEntity("debug_zombie", EntityTestZombie.class);
        registerEntity("debug_skeleton", EntityTestSkeleton.class);
        registerEntity("debug_slime", EntityTestSlime.class);
        //kill @e[type=adven_one:boss_steve]
        registerEntity("boss_steve", EntityBossSteve.class);

//        registerEntity("moroon_tainter", EntityMoroonTainter.class,0xff00ff, 0x000033);
//        registerEntity("idealland_whitetower_core", EntityIDLWhiteTowerCore.class, ENTITY_NEXT_ID, 128, 0xeeee00, 0xffffff);

        //the bullet
        //registerEntity("bullet", EntityIdlProjectile.class);

        //Assign Dungeons
        //DungeonHooks.addDungeonMob(EntityList.getKey(EntityMoroonTainter.class), STANDARD_DUNGEON_MOB_RARITY);

        DataFixer datafixer = new DataFixer(1343);
    }

    private  static  void registerEntity(String name, Class<? extends Entity> entity)
    {
        registerEntity(name, entity, ENTITY_NEXT_ID, 50, 0xff00ff, 0x000000);
    }

    private  static  void registerEntity(String name, Class<? extends Entity> entity, int color1, int color2)
    {
        registerEntity(name, entity, ENTITY_NEXT_ID, 50, color1, color2);
    }

    private  static  void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2){
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name),
                entity,
                name,
                id,
                Idealland.instance,
                range,
                1,
                true,
                color1, color2
                );
        ENTITY_NEXT_ID++;
    }
}
