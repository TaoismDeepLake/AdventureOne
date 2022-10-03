package com.deeplake.adven_one.init;

import com.deeplake.adven_one.Idealland;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.minecraft.advancements.AdvancementManager.GSON;

public class ModAchivements {
    private static final TypeToken<Map<ResourceLocation, AdvancementProgress>> MAP_TOKEN = new TypeToken<Map<ResourceLocation, AdvancementProgress>>()
    {
    };
    static Logger LOGGER = Idealland.logger;

    public static Map<Advancement, AdvancementProgress> getAdvancements(EntityPlayerMP playerMP)
    {
        Map<Advancement, AdvancementProgress> progress = Maps.newLinkedHashMap();

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

                Stream<Map.Entry<ResourceLocation, AdvancementProgress>> stream = map.entrySet().stream().sorted(Map.Entry.comparingByValue());

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

    //Todo: achivements

}
