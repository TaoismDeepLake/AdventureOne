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
        public boolean SHOW_DEBUG_ARMOR = true;
    }

    @Config.LangKey("configgui.idlframewok.category.Menu0.SpawnConf")
    @Config.Comment("Spawning")
    public static final SpawnConf SPAWN_CONF = new SpawnConf();

    public static class SpawnConf {
        @Config.LangKey("conf.spawn.enabled")
        @Config.Comment("Spawn mod creatures")
        @Config.RequiresMcRestart
        public boolean SPAWN = true;

//        @Config.LangKey("entity.moroon_tainter.name")
//        @Config.Comment("Spawn Moroon Tainter")
//        @Config.RequiresMcRestart
//        public int SPAWN_TAINTER = 100;
    }

    @Config.LangKey("configgui.idealland.category.Menu0.WorldGenConf")
    @Config.Comment("World Generate")
    public static final WorldGenConf WORLD_GEN_CONF = new WorldGenConf();

    public static class WorldGenConf {

        @Config.LangKey("adv_one.conf.world.dim_id")
        @Config.Comment("DIM Adventure One")
        @Config.RequiresMcRestart
        public int DIM_ONE_ID = 1118;

        //Per Chunk
        public float TREE_DENSITY = 0.001f;

        public int IRON_DENSITY = 16;

        public int REDSTONE_DENSITY = 8;
        public int COAL_DENSITY = 8;

        public int GOLD_DENSITY = 8;
        public int LAPIS_DENSITY = 8;

        public int DIAMOND_DENSITY = 8;

        public int T1_DENSITY = 1;
        public int T1_DENSITY_PLUS = 2;

        public int BEDROCK_DENSITY_PLUS = 2;



    }
}
