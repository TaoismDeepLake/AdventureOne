package com.deeplake.adven_one.world.structure.bigger.bottom;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.util.MathU;
import com.deeplake.adven_one.world.structure.bigger.EnumConnection;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Random;

public class StructureBottomDungeon extends MapGenStructure {
    public static final String NAME = "BottomDungeon";
    final int SEED_CONST = 20220910;
    final int maxDistance = 6;//in chunks
    final int xOffset = 1;//in chunks
    final int zOffset = 1;//in chunks
    static final int ATTEMPTS = 100;
    Random random = new Random();

    ////replace adven_one:suit_back_stone air

    public void resetRandom(int chunkX, int chunkZ) {
        random = world.setRandomSeed(chunkX, chunkZ, SEED_CONST);
    }

    @Override
    public String getStructureName() {
        return NAME;
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored) {
        this.world = worldIn;
        return findNearestStructurePosBySpacing(worldIn, this, pos, maxDistance, 3, SEED_CONST, false, ATTEMPTS, findUnexplored);

    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
        resetRandom(chunkX, chunkZ);
        if (
                MathU.mod(chunkX + chunkZ/2, maxDistance) == xOffset
                        && MathU.mod(chunkX/2 + chunkZ, maxDistance) == zOffset) {
            return true;
        }
        return false;
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        return new Start(world,rand,chunkX,chunkZ);
    }

    public static class Start extends StructureStart {
        protected HashMap<Vec3i, ComponentDungeonRoomBase> roomHashMap = new HashMap<>();
        HashMap<Integer, Boolean> canGoDown = new HashMap<>();
        public BlockPos basePos;
        protected Random random;

        public static int cascadeLevel = 0;

        //Make sure you have this. Or it will error when reloading the game.
        public Start() {
        }

        public Start(World worldIn, Random rand, int x, int z) {
            super(x, z);
            this.random = rand;
            init(worldIn, rand, x, z);
            updateBoundingBox();
        }

        public void init(World worldIn, Random rand, int x, int z)
        {
            basePos = new BlockPos(x << 4, ModConfig.DUNGEON_CONF.BOTTOM_DUNGEON_Y, z << 4);
            ComponentDungeonRoomBase firstComp = new ComponentBottomDungeonRoomCenter(1, EnumFacing.NORTH, Vec3i.NULL_VECTOR, this);
//            firstComp.connections.put(EnumFacing.UP, EnumConnection.PASS);
            firstComp.buildComponent(firstComp, null, random);
            expandRoom(firstComp, rand);
        }

        public static void log(String s, Object... args) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i <= cascadeLevel; i++) {
                stringBuilder.append("  ");
            }
            stringBuilder.append(String.format(s, args));
            Idealland.Log(stringBuilder.toString());
        }

        public ComponentDungeonRoomBase getNewRoom(int type, EnumFacing mainDirection, Vec3i vec3i, StructureBottomDungeon.Start start) {
            ComponentDungeonRoomBase room = new ComponentBottomDungeonRoomSide(type, mainDirection, vec3i, start);
            room.buildComponent(room, null, random);
            enlistRoom(room);
            return room;
        }

        boolean expandRoom(ComponentDungeonRoomBase roomBase, Random random) {
            cascadeLevel++;
            log("expand room from %s", roomBase.relPos);

            enlistRoom(roomBase);
            BlockPos ori = new BlockPos(roomBase.relPos);

            goDir(roomBase, random, EnumFacing.EAST).connections.put(EnumFacing.EAST, EnumConnection.PASS);
            goDir(roomBase, random, EnumFacing.SOUTH).connections.put(EnumFacing.SOUTH, EnumConnection.PASS);
            goDir(roomBase, random, EnumFacing.NORTH).connections.put(EnumFacing.NORTH, EnumConnection.PASS);
            goDir(roomBase, random, EnumFacing.WEST).connections.put(EnumFacing.WEST, EnumConnection.PASS);

            cascadeLevel--;
            return true;
        }

        //returns true if it creates at least 1 new room.
        //this version won't recursive call it.
        ComponentDungeonRoomBase goDir(ComponentDungeonRoomBase roomBase, Random random, EnumFacing dir) {
            cascadeLevel++;
            log("go dir from %s", roomBase.relPos);
            BlockPos nextPos = new BlockPos(roomBase.relPos).offset(dir);

            ComponentDungeonRoomBase targetRoom = roomHashMap.get(nextPos);
            if (targetRoom != null) {
                //already occupied. break it through but fail the room creation.
                roomBase.connect(dir, targetRoom);
                cascadeLevel--;
                return targetRoom;
            } else {
                //create one
                ComponentDungeonRoomBase nextRoom = getNewRoom(0, dir, nextPos, this);
                roomBase.connect(dir, nextRoom);
                cascadeLevel--;
                return nextRoom;
            }
        }

        public void enlistRoom(ComponentDungeonRoomBase roomBase) {
            roomHashMap.put(roomBase.relPos, roomBase);
            components.add(roomBase);
        }
    }
}
