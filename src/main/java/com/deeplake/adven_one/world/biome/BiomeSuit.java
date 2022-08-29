package com.deeplake.adven_one.world.biome;

import com.deeplake.adven_one.designs.EnumSuit;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeSuit extends BiomeBase{
    EnumSuit suit;

    public BiomeSuit(String name, EnumSuit suit, BiomeDictionary.Type... type) {
        super(name, type);
        this.suit = suit;
    }

    public EnumSuit getSuit() {
        return suit;
    }
}
