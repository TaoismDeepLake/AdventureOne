package com.deeplake.adven_one.potion;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.potion.buff.BaseSimplePotion;
import com.deeplake.adven_one.util.Reference;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModPotions {

    static final UUID UUID_TERRAIN_HINDER = UUID.fromString("7adc87da-8943-4874-c7de-050c1ed0d452");

    public static final List<Potion> INSTANCES = new ArrayList<Potion>();

    public static final Potion TERRAIN_HINDER =
            new BaseSimplePotion(false, 0x333333, "terrain_hinder", 0)
                    .registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, UUID_TERRAIN_HINDER.toString(), -0.25f, 2);
//    public static final PotionDeadly DEADLY = new PotionDeadly(false, 0x333333, "deadly", 0);
//    public static final PotionZenHeart ZEN_HEART = new PotionZenHeart(false, 0xcccc00, "zen_heart", 1);

    @Nullable
    private static Potion getRegisteredMobEffect(String id)
    {
        Potion potion = Potion.REGISTRY.getObject(new ResourceLocation(id));

        if (potion == null)
        {
            throw new IllegalStateException("Invalid MobEffect requested: " + id);
        }
        else
        {
            return potion;
        }
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> evt)
    {
        //VIRUS_ONE.tuples.add(new EffectTuple(0.2f, MobEffects.NAUSEA, 100));

        evt.getRegistry().registerAll(INSTANCES.toArray(new Potion[0]));
        Idealland.LogWarning("registered %d potion", INSTANCES.size());
    }
}
