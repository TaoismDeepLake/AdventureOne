package com.deeplake.adven_one.init;

import com.deeplake.adven_one.util.Reference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Reference.MOD_ID, category = "")
public class ModConfig {
    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    private static class EventHandler {

        private EventHandler() {
        }

        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Reference.MOD_ID)) {
                ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }

    @Config.LangKey("configgui.idlframewok.category.Menu0.GeneralConf")
    @Config.Comment("IdlFramework general config.")
    public static final GeneralConf GeneralConf = new GeneralConf();

    public static class GeneralConf {
//        @Config.LangKey("idlframewok.conf.general.welcome")
//        @Config.Comment("The text shown when a player logs in. Can be a key or a string.")
//        public String WELCOME_MSG = "idlframewok.msg.welcome";


    }

    @Config.LangKey("configgui.idlframewok.category.Menu0.DebugConf")
    @Config.Comment("Config for developers")
    public static final DebugConf DEBUG_CONF = new DebugConf();

    public static class DebugConf {
        public boolean DEBUG_MODE = false;
        public boolean SHOW_DEBUG_ARMOR = false;
    }

    public static final SpecialMobConf SPECIAL_MOB_CONF = new SpecialMobConf();

    public static class SpecialMobConf {
        @Config.RangeDouble(min = 0f, max = 1f)
        public double BOSS_DEFLECT_CHANCE = 0.5f;

        @Config.RangeDouble(min = 1f)
        public double BOSS_STEVE_HP = 256f;

        @Config.RangeDouble(min = 0f)
        public double BOSS_STEVE_ATK = 1f;
    }

    public static final TierConf TIER_CONF = new TierConf();

    public static class TierConf {
        //public static final TierConf MELEE_CONF = new TierConf();

        @Config.RangeDouble(min = 0f)
        public double SWORD_ATK_T1 = 4;

        @Config.RangeDouble(min = 0f)
        public double SWORD_ATK_T2 = 8;

        @Config.RangeDouble(min = 0f)
        public double SWORD_ATK_T3 = 24;

        @Config.RangeInt(min = 0)
        public double EARTH_BARRIER_REQ_BIOME = 2;

        @Config.RangeInt(min = 0)
        public double SKY_BARRIER_REQ_BIOME = 2;

        public final MobAttrConf NORMAL_MOB = new MobAttrConf();
        public final EquipAttrConf EQUIP_ATTR_1 = new EquipAttrConf();
        public final EquipAttrConf EQUIP_ATTR_2 = new EquipAttrConf();
        public final EquipAttrConf EQUIP_ATTR_3 = new EquipAttrConf();
        public final EquipAttrConf EQUIP_ATTR_4 = new EquipAttrConf();

        @Config.Ignore()
        public final EquipAttrConf[] EQUIP_ATTR_CONF = new EquipAttrConf[4];
        {
            EQUIP_ATTR_CONF[0] = EQUIP_ATTR_1;
            EQUIP_ATTR_CONF[1] = EQUIP_ATTR_2;
            EQUIP_ATTR_CONF[2] = EQUIP_ATTR_3;
            EQUIP_ATTR_CONF[3] = EQUIP_ATTR_4;
        }
    }

    public static class EquipAttrConf {
        @Config.RangeDouble(min = 0f)
        public double MIN_GEM_QUALITY = 0.5f;

        @Config.RangeDouble(min = 0f)
        public double DELTA_GEM_QUALITY = 1f;
    }
    public static class MobAttrConf {
        @Config.RangeDouble(min = 1f)
        public double HP_BASE = 20;

        //note that they have an axe +7
        @Config.RangeDouble(min = 0.5f)
        public double ATK_BASE = 1;

        @Config.RangeDouble(min = 0.01f)
        public double HP_T2_RATIO = 3;

        @Config.RangeDouble(min = 0.01f)
        public double ATK_T2_RATIO = 2;

        //relatetive to T1
        @Config.RangeDouble(min = 0.01f)
        public double HP_T3_RATIO = 9;

        //relatetive to T1
        @Config.RangeDouble(min = 0.01f)
        public double ATK_T3_RATIO = 3;
    }

    @Config.LangKey("configgui.idlframewok.category.Menu0.SpawnConf")
    @Config.Comment("Spawning")
    public static final SpawnConf SPAWN_CONF = new SpawnConf();

    public static class SpawnConf {
        @Config.LangKey("conf.spawn.enabled")
        @Config.Comment("Spawn mod creatures")
        @Config.RequiresMcRestart
        public boolean SPAWN = true;

        @Config.LangKey("conf.spawn_t1")
        @Config.Comment("Monster Spawn T1")
        @Config.RequiresMcRestart
        public int SPAWN_T1 = 100;

        @Config.LangKey("conf.spawn_t1m")
        @Config.Comment("Monster Spawn T1m")
        @Config.RequiresMcRestart
        public int SPAWN_T1M = 100;

        @Config.LangKey("conf.spawn_t2")
        @Config.Comment("Monster Spawn T2")
        @Config.RequiresMcRestart
        public int SPAWN_T2 = 100;

        @Config.LangKey("conf.spawn_t2m")
        @Config.Comment("Monster Spawn T2m")
        @Config.RequiresMcRestart
        public int SPAWN_T2M = 100;

        @Config.LangKey("conf.spawn_t3")
        @Config.Comment("Monster Spawn T3")
        @Config.RequiresMcRestart
        public int SPAWN_T3 = 100;

        @Config.LangKey("conf.spawn_t3m")
        @Config.Comment("Monster Spawn T3m")
        @Config.RequiresMcRestart
        public int SPAWN_T3M = 100;
    }

    public static final QualityConf QUALITY_CONF = new QualityConf();

    public static class QualityConf {
        public double MIN_Q_PICKAXE = 0.5;
        public double DELTA_Q_PICKAXE = 1.0;

        public double MIN_Q_SWORD = 0.5;
        public double DELTA_Q_SWORD = 1.0;

        public double MIN_Q_ARMOR = 0.5;
        public double DELTA_Q_ARMOR = 1.0;
    }

    @Config.LangKey("configgui.idealland.category.Menu0.WorldGenConf")
    @Config.Comment("World Generate")
    public static final WorldGenConf WORLD_GEN_CONF = new WorldGenConf();

    public static class WorldGenConf {

        @Config.LangKey("adv_one.conf.world.dim_id")
        @Config.Comment("DIM Adventure One")
        @Config.RequiresMcRestart
        public int DIM_ONE_ID = 1118;


        @Config.Comment("In Blocks")
        @Config.RequiresMcRestart
        public int BIOME_X_SPAN = 128;
        public int MOUNTAIN_THICKNESS = 6;
        public int MOUNTAIN_Y_DELTA = 3;

        //Per Chunk
        public float TREE_DENSITY = 0.001f;

        public int GLASS_BARRIER_DENSITY = 256;
        public int IRON_DENSITY = 16;

        public int REDSTONE_DENSITY = 8;
        public int COAL_DENSITY = 8;

        public int GOLD_DENSITY = 8;
        public int LAPIS_DENSITY = 8;

        public int DIAMOND_DENSITY = 8;

        public int T1_DENSITY = 1;
        public int T1_DENSITY_PLUS = 2;

        public int BEDROCK_DENSITY_PLUS = 2;

        @Config.Comment("Sparseness of hole")
        public int CHUNK_PER_HOLE = 20;

    }

    @Config.LangKey("configgui.idealland.category.Menu0.DungeonGenConf")
    @Config.Comment("Dungeon Generate")
    public static final DungeonConf DUNGEON_CONF = new DungeonConf();

    public static class DungeonConf {
        public int BOTTOM_DUNGEON_Y = 32;
        public int TOP_DUNGEON_Y = 216;

        //include exterior
        public int BOTTOM_DUNGEON_ROOM_XZ = 17;
        public int BOTTOM_DUNGEON_ROOM_Y = 10;

        public int notPassageLength = 6;
        public int notPassageLengthY = 1;
        public int xzPassageHeight = 5;
        public int floorHeight = 2;
        public int stepLightDistance = 4;

        @Config.RangeInt(min = 0)
        public int ingotPerBottomChestMin = 2;
        @Config.RangeInt(min = 0)
        public int ingotPerBottomChestDelta = 4;

        @Config.RangeInt(min = 0)
        public int torchPerBottomChestMin = 8;
        @Config.RangeInt(min = 0)
        public int torchPerBottomChestDelta = 24;

        @Config.RangeInt(min = 0)
        public int breadPerBottomChestMin = 2;
        @Config.RangeInt(min = 0)
        public int breadPerBottomChestDelta = 10;

        @Config.RangeInt(min = 0)
        public int stickPerBottomChestMin = 1;
        @Config.RangeInt(min = 0)
        public int stickPerBottomChestDelta = 7;
    }
}
