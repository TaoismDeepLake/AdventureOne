package com.deeplake.adven_one.world.structure;

import com.deeplake.adven_one.world.structure.bigger.bottom.ComponentBottomDungeonRoomCenter;
import com.deeplake.adven_one.world.structure.bigger.bottom.ComponentBottomDungeonRoomSide;
import com.deeplake.adven_one.world.structure.bigger.bottom.StructureBottomDungeon;
import com.deeplake.adven_one.world.structure.bigger.top.ComponentTopDungeonRoomCenter;
import com.deeplake.adven_one.world.structure.bigger.top.ComponentTopDungeonRoomSide;
import com.deeplake.adven_one.world.structure.bigger.top.StructureTopDungeon;
import net.minecraft.world.gen.structure.MapGenStructureIO;

public class InitGiantStructures {

    public static void registerWorldGen() {
           MapGenStructureIO.registerStructure(StructureBottomDungeon.Start.class, StructureBottomDungeon.NAME);
        MapGenStructureIO.registerStructureComponent(ComponentBottomDungeonRoomCenter.class, "BDungC");
        MapGenStructureIO.registerStructureComponent(ComponentBottomDungeonRoomSide.class, "BDungS");

        MapGenStructureIO.registerStructure(StructureTopDungeon.Start.class, StructureTopDungeon.NAME);
        MapGenStructureIO.registerStructureComponent(ComponentTopDungeonRoomCenter.class, "TDungC");
        MapGenStructureIO.registerStructureComponent(ComponentTopDungeonRoomSide.class, "TDungS");
    }
}
