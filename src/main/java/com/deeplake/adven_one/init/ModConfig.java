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

    @Config.Comment("IdlFramework general config.")
    public static final GeneralConf GENERAL_CONF = new GeneralConf();

    public static class GeneralConf {
        public WorldPressureConf CONF_UP = new WorldPressureConf(136,6.0,100.0);
        public WorldPressureConf CONF_DOWN = new WorldPressureConf(125,3.0,10.0);

        public MeteorConf METEOR_CONF = new MeteorConf();
    }

    public static class MeteorConf {
        public boolean ENABLE_METEOR_RAIN = true;
        @Config.RangeInt(min = 1)
        @Config.Comment("Smaller number means dense meteor rain.")
        public int METEOR_PERIOD = 1;
        @Config.RangeDouble(min = Float.MIN_VALUE)
        public float STANDARD_RATE = 1f;
        @Config.Comment("If the place can not see sky.")
        public float COVERED_REDUCTION = 1f;

        @Config.Comment("Higher places get bonus rate.")
        public float HEIGHT_BONUS = 0.03f;

        public float METEOR_RADIUS = 32f;

        @Config.RangeDouble(min = Float.MIN_VALUE)
        public float METEOR_SPEED = 3f;
    }

    public static class WorldPressureConf {
        public WorldPressureConf(double y_START, double DAMAGE_TAKEN_UP_RATIO, double DIG_TIME_UP_RATIO) {
            Y_START = y_START;
            this.DAMAGE_TAKEN_UP_RATIO = DAMAGE_TAKEN_UP_RATIO;
            this.DIG_TIME_UP_RATIO = DIG_TIME_UP_RATIO;
        }

        @Config.Comment("from which Y the effect start counting")
        public double Y_START;

        @Config.Comment("1.0 = +100% damage taken per 100 blocks")
        public double DAMAGE_TAKEN_UP_RATIO;

        @Config.Comment("1.0 = +100% dig time needed per 100 blocks")
        public double DIG_TIME_UP_RATIO;
    }

    public static class CostConf {
        public int INIT_COST = 20;
        public int ADVANCEMENT_COST = 1;
        public int ADVANCEMENT_COST_GOAL = 2;
        public int ADVANCEMENT_COST_CHALL = 5;

        @Config.RequiresMcRestart
        public int FOOD_COST_SMALL = 1;
        @Config.RequiresMcRestart
        public int FOOD_COST_NORMAL = 2;
        @Config.RequiresMcRestart
        public int FOOD_COST_BIG = 5;
    }

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

        //Crafting
        public final TierQualityConf TIER_QUALITY_1 = new TierQualityConf();
        public final TierQualityConf TIER_QUALITY_2 = new TierQualityConf();
        public final TierQualityConf TIER_QUALITY_3 = new TierQualityConf();
        public final TierQualityConf TIER_QUALITY_4 = new TierQualityConf();

        @Config.RangeDouble(min = 0f)
        public double UNIDENTIFIED_QUALITY = 0.45f;
        @Config.Ignore()
        public final TierQualityConf[] TIER_QUALITY_CONF = new TierQualityConf[4];
        {
            TIER_QUALITY_CONF[0] = TIER_QUALITY_1;
            TIER_QUALITY_CONF[1] = TIER_QUALITY_2;
            TIER_QUALITY_CONF[2] = TIER_QUALITY_3;
            TIER_QUALITY_CONF[3] = TIER_QUALITY_4;
        }

        public final CostConf COST_CONF = new CostConf();

        public final CostConfigByTier COST_TIER_1 =
                 new CostConfigByTier(20,20,30,50,40,30);
        public final CostConfigByTier COST_TIER_2 =
                 new CostConfigByTier(3,COST_TIER_1);
        public final CostConfigByTier COST_TIER_3 =
                 new CostConfigByTier(9,COST_TIER_1);
        public final CostConfigByTier COST_TIER_4 =
                 new CostConfigByTier(27,COST_TIER_1);

        @Config.Ignore()
        public final CostConfigByTier[] COST_TIER = new CostConfigByTier[4];
        {
            COST_TIER[0] = COST_TIER_1;
            COST_TIER[1] = COST_TIER_2;
            COST_TIER[2] = COST_TIER_3;
            COST_TIER[3] = COST_TIER_4;
        }
    }

    public static class CostConfigByTier{
        //all actual cost is multiplied by this.
        @Config.RangeInt(min = 0) public int FACTOR;
        @Config.RangeInt(min = 0) public int SWORD_COST;
        @Config.RangeInt(min = 0) public int PICK_COST;
        @Config.RangeInt(min = 0) public int HEAD_COST;
        @Config.RangeInt(min = 0) public int CHEST_COST;
        @Config.RangeInt(min = 0) public int LEG_COST;
        @Config.RangeInt(min = 0) public int FEET_COST;

        public CostConfigByTier(int SWORD_COST, int PICK_COST, int HEAD_COST, int CHEST_COST, int LEG_COST, int FEET_COST) {
            this(1,SWORD_COST,PICK_COST,HEAD_COST,CHEST_COST,LEG_COST,FEET_COST);
        }

        public CostConfigByTier(int FACTOR, CostConfigByTier parent) {
            this(FACTOR,parent.SWORD_COST,parent.PICK_COST,parent.HEAD_COST,parent.CHEST_COST,parent.LEG_COST,parent.FEET_COST);
        }

        public CostConfigByTier(int FACTOR, int SWORD_COST, int PICK_COST, int HEAD_COST, int CHEST_COST, int LEG_COST, int FEET_COST) {
            this.FACTOR = FACTOR;
            this.SWORD_COST = SWORD_COST;
            this.PICK_COST = PICK_COST;
            this.HEAD_COST = HEAD_COST;
            this.CHEST_COST = CHEST_COST;
            this.LEG_COST = LEG_COST;
            this.FEET_COST = FEET_COST;
        }
    }

    public static class TierQualityConf {

        @Config.RangeDouble(min = 0f)
        public double MIN_GEM_QUALITY = 0.5f;

        @Config.RangeDouble(min = 0f)
        public double DELTA_GEM_QUALITY = 1f;
    }

    public static final ModifierConf MODIFIER_CONF = new ModifierConf();
    public static class ModifierConf {
        public ModifierConfGroup ATK_FIXED_GROUP = new ModifierConfGroup();
        public ModifierConfGroup HP_FIXED_GROUP = new ModifierConfGroup(5,4,3,2,1);
        public ModifierConfGroup EFFICIENCY_FIXED_GROUP = new ModifierConfGroup(1,0.8,0.5,0.3,0.2);
        public ModifierConfGroup COST_REDUCE_FIXED_GROUP = new ModifierConfGroup(10,5,3,2,1);
        public ModifierConfGroup COST_UP_FIXED_GROUP = new ModifierConfGroup(10,5,3,2,1);

        public ModifierConfGroup PRESSURE_DOWN_FIXED_GROUP = new ModifierConfGroup(10,5,3,2,1);
    }

    public enum EnumFixLevel{
        A,B,C,D,E
    }

    public static class ModifierConfGroup {
        public ModifierConfGroup(double VALUE_A, double VALUE_B, double VALUE_C, double VALUE_D, double VALUE_E) {
            this.VALUE_A = VALUE_A;
            this.VALUE_B = VALUE_B;
            this.VALUE_C = VALUE_C;
            this.VALUE_D = VALUE_D;
            this.VALUE_E = VALUE_E;
        }

        public ModifierConfGroup() {
        }

        public double getByLevel(EnumFixLevel level)
        {
            switch (level)
            {
                case A:
                    return VALUE_A;
                case B:
                    return VALUE_B;
                case C:
                    return VALUE_C;
                case D:
                    return VALUE_D;
                case E:
                    return VALUE_E;
                default:
                    throw new IllegalStateException("Unexpected value: " + level);
            }

        }


        @Config.RangeDouble(min = 0f)
        public double VALUE_A = 2f;

        @Config.RangeDouble(min = 0f)
        public double VALUE_B = 1.5f;

        @Config.RangeDouble(min = 0f)
        public double VALUE_C = 1f;

        @Config.RangeDouble(min = 0f)
        public double VALUE_D = 0.5f;

        @Config.RangeDouble(min = 0f)
        public double VALUE_E = 0.3f;
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

        //relative to T1
        @Config.RangeDouble(min = 0.01f)
        public double HP_T3_RATIO = 9;

        //relative to T1
        @Config.RangeDouble(min = 0.01f)
        public double ATK_T3_RATIO = 3;
    }

    @Config.LangKey("configgui.adven_one.category.Menu0.SpawnConf")
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
        public double QUALITY_PER_DUST = 0.01;

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

        public CraterConf CRATER_CONF = new CraterConf();
    }

    public static class CraterConf{
        @Config.RangeDouble(min = 0, max = 1)
        public double CRATER_TREE_CHANCE = 0.3f;
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
