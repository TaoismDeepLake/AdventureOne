package com.deeplake.adven_one.designs;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.util.EntityUtil;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.minecraft.advancements.AdvancementManager.GSON;


@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class HandleCost {
    private static final TypeToken<Map<ResourceLocation, AdvancementProgress>> MAP_TOKEN = new TypeToken<Map<ResourceLocation, AdvancementProgress>>()
    {
    };

    @SubscribeEvent
    public static void onAdvancement(AdvancementEvent event)
    {
        EntityPlayer player = event.getEntityPlayer();
        World world = player.world;
        if (!world.isRemote)
        {
            resetPlayerInitCost(player);
            EntityPlayerMP mp = (EntityPlayerMP) player;
            Map<Advancement, AdvancementProgress> progressMap = getAdvancements(mp);

            int score = 0;
            for (Advancement advancement : progressMap.keySet())
            {
                if (progressMap.get(advancement).isDone())
                {
                    DisplayInfo info = advancement.getDisplay();
                    if (info != null) {
                        switch (info.getFrame())
                        {
                            case CHALLENGE:
                                score += ModConfig.GENERAL_CONF.ADVANCEMENT_COST_CHALL;
                                break;
                            case GOAL:
                                score += ModConfig.GENERAL_CONF.ADVANCEMENT_COST_GOAL;
                                break;
                            case TASK:
                            default:
                                score += ModConfig.GENERAL_CONF.ADVANCEMENT_COST;
                        }
                    }
                }
            }

            EntityUtil.boostAttr(player, ModAttributes.COST, score, ADVANCEMENT_BUFF);
        }
    }

    static Logger LOGGER = Idealland.logger;

    static Map<Advancement, AdvancementProgress> getAdvancements(EntityPlayerMP playerMP)
    {
        Map<Advancement, AdvancementProgress> progress = Maps.<Advancement, AdvancementProgress>newLinkedHashMap();

        String uuid = playerMP.getUniqueID().toString();
        File file1 = new File(playerMP.mcServer.getWorld(0).getSaveHandler().getWorldDirectory(), "advancements");
        File file2 = new File(file1, uuid + ".json");
        if (file2.isFile())
        {
            try
            {
                String s = Files.toString(file2, StandardCharsets.UTF_8);
                Map<ResourceLocation, AdvancementProgress> map = JsonUtils.gsonDeserialize(GSON, s, MAP_TOKEN.getType());

                if (map == null)
                {
                    throw new JsonParseException("Found null for advancements");
                }

                Stream<Map.Entry<ResourceLocation, AdvancementProgress>> stream = map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue));

                for (Map.Entry<ResourceLocation, AdvancementProgress> entry : stream.collect(Collectors.toList()))
                {
                    Advancement advancement = playerMP.getServer().getAdvancementManager().getAdvancement(entry.getKey());

                    if (advancement == null)
                    {
                        LOGGER.warn("Ignored advancement '" + entry.getKey() + "' in progress file " + file2 + " - it doesn't exist anymore?");
                    }
                    else
                    {
                        progress.put(advancement, entry.getValue());
                    }
                }
            }
            catch (JsonParseException jsonparseexception)
            {
                LOGGER.error("Couldn't parse player advancements in " + file2, jsonparseexception);
            }
            catch (IOException ioexception)
            {
                LOGGER.error("Couldn't access player advancements in " + file2, ioexception);
            }
        }
        return progress;
    }


    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        EntityPlayer player = event.player;
        World world = player.world;
        if (!world.isRemote)
        {
            resetPlayerInitCost(player);
        }
    }

    public static void resetPlayerInitCost(EntityPlayer player) {
        player.getEntityAttribute(ModAttributes.COST).setBaseValue(ModConfig.GENERAL_CONF.INIT_COST);
    }

    static final UUID ADVANCEMENT_BUFF = UUID.fromString("98016498-e5ce-3f85-26d2-aad176146d51");
}
