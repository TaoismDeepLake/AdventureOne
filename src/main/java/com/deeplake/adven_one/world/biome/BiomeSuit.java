package com.deeplake.adven_one.world.biome;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.EnumSuit;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class BiomeSuit extends BiomeBase{
    EnumSuit suit;

    public BiomeSuit(String name, EnumSuit suit, BiomeDictionary.Type... type) {
        super(name, type);
        this.suit = suit;
    }

    public EnumSuit getSuit() {
        return suit;
    }

//    @SubscribeEvent
//    public static void onDeco(OreGenEvent.GenerateMinable event)
//    {
//        if (event.getWorld().getBiome(event.getPos()) instanceof BiomeSuit)
//        {
//            switch (event.getType())
//            {
//                case COAL:
//                    //max128
//                    event.setResult(Event.Result.DENY);
//                    break;
//                case DIAMOND:
//                    break;
//                case DIRT:
//                    //max256
//                    event.setResult(Event.Result.DENY);
//                    break;
//                case GOLD:
//                    break;
//                case GRAVEL:
//                    //max236
//                    event.setResult(Event.Result.DENY);
//                    break;
//                case IRON:
//                    break;
//                case LAPIS:
//                    break;
//                case REDSTONE:
//                    break;
//                case QUARTZ:
//                    break;
//                case DIORITE:
//                    break;
//                case GRANITE:
//                    break;
//                case ANDESITE:
//                    break;
//                case EMERALD:
//                    break;
//                case SILVERFISH:
//                    break;
//                case CUSTOM:
//                    break;
//            }
//        }
//    }
}
