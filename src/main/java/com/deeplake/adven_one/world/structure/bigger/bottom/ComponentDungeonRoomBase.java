package com.deeplake.adven_one.world.structure.bigger.bottom;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.util.CommonFunctions;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTDef;
import com.deeplake.adven_one.util.WorldGenUtil;
import com.deeplake.adven_one.world.structure.bigger.ComponentBase;
import com.deeplake.adven_one.world.structure.bigger.EnumConnection;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.HashMap;
import java.util.Random;

import static com.deeplake.adven_one.util.WorldGenUtil.IRON_FENCE;

public class ComponentDungeonRoomBase extends ComponentBase {
    public HashMap<EnumFacing, EnumConnection> connections = new HashMap<>();
    EnumFacing mainDirection = EnumFacing.NORTH;
    public BlockPos relPos;//not in the unit of blocks, but of rooms.
    StructureBottomDungeon.Start start; //please note that when populating, this may be null.

    BlockSelectorDecoFloor blockSelectorDecoFloor = new BlockSelectorDecoFloor();

    protected int outerSize = 1;
    protected int sizeXZ;
    protected int sizeY;

    protected boolean keepBorderY = true;
    protected boolean keepBorderXZ = true;

    private IBlockState wallState = Blocks.BRICK_BLOCK.getDefaultState();
    private IBlockState wall2 = Blocks.PURPUR_DOUBLE_SLAB.getDefaultState();
    private IBlockState floor = Blocks.PURPUR_DOUBLE_SLAB.getDefaultState();
    private IBlockState GLASS_WHITE = Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.WHITE);
    IBlockState WEB = Blocks.WEB.getDefaultState();

    //grammar
    public ComponentDungeonRoomBase() {
        setCoordBaseMode(EnumFacing.SOUTH);
    }

    public ComponentDungeonRoomBase(int type, EnumFacing mainDirection, Vec3i vec3i, StructureBottomDungeon.Start start) {
        super(type);
        this.mainDirection = mainDirection;
        relPos = new BlockPos(vec3i);
        setCoordBaseMode(EnumFacing.SOUTH);

        int roomXz = ModConfig.DUNGEON_CONF.BOTTOM_DUNGEON_ROOM_XZ;
        int roomY = ModConfig.DUNGEON_CONF.BOTTOM_DUNGEON_ROOM_Y;
        BlockPos worldPos = start.basePos.add(relPos.getX() * roomXz,
                relPos.getY() * roomY,
                relPos.getZ() * roomXz);

        sizeXZ = roomXz;
        sizeY = roomY;

        this.boundingBox = new StructureBoundingBox(
                worldPos,
                worldPos.add(roomXz - 1,
                        roomY - 1,
                        roomXz - 1)
        );

        if (ModConfig.DEBUG_CONF.DEBUG_MODE) {
            StructureBottomDungeon.Start.log("[N]Created a room index(%s,%s,%s), bb is %s", vec3i.getX(), vec3i.getY(), vec3i.getZ(), this.boundingBox);
        }

        checkArgumentValidity();
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound) {
        for (EnumFacing facing : connections.keySet()) {
            tagCompound.setInteger(facing.getName(), connections.get(facing).ordinal());
        }
        tagCompound.setTag(IDLNBTDef.POS_NAME, NBTUtil.createPosTag(relPos));
        tagCompound.setInteger(IDLNBTDef.MAIN_DIR, mainDirection.ordinal());
        tagCompound.setInteger(IDLNBTDef.KEY_SIZE_XZ, sizeXZ);
        tagCompound.setInteger(IDLNBTDef.KEY_SIZE_Y, sizeY);
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
        for (EnumFacing facing : EnumFacing.values()) {
            if (tagCompound.hasKey(facing.getName())) {
                connections.put(facing, EnumConnection.values()[tagCompound.getInteger(facing.getName())]);
            }
        }
        relPos = NBTUtil.getPosFromTag(tagCompound.getCompoundTag(IDLNBTDef.POS_NAME));
        mainDirection = EnumFacing.values()[tagCompound.getInteger(IDLNBTDef.MAIN_DIR)];
        sizeXZ = tagCompound.getInteger(IDLNBTDef.KEY_SIZE_XZ);
        sizeY = tagCompound.getInteger(IDLNBTDef.KEY_SIZE_Y);
    }

    //places blocks.
    @Override
    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
        EnumSuit suit = EnumSuit.getSuit(worldIn, structureBoundingBoxIn);
        setWallState(suit.getWOOD_PLANKS().getDefaultState());
        setWall2(suit.getWOOD_LOG().getDefaultState());
        setFloor(suit.getDIRT().getDefaultState());

        buildExteriorWallAndClean(worldIn, randomIn, structureBoundingBoxIn);

        if (!connections.containsKey(EnumFacing.DOWN)) {
            buildFloor(worldIn, randomIn, structureBoundingBoxIn);
        }

        buildConnections(worldIn, randomIn, structureBoundingBoxIn);
        buildContent(worldIn, randomIn, structureBoundingBoxIn);

        return true;
    }

    protected void buildContent(World worldIn, Random randomIn, StructureBoundingBox sbb) {
//        decorateWeb(worldIn, randomIn, sbb);

//        decorateCorner(worldIn, randomIn, sbb);
    }

    protected void buildFloor(World worldIn, Random randomIn, StructureBoundingBox sbb) {
        fillWithRandomizedBlocks(worldIn, sbb,
                outerSize + 1, outerSize + 1, outerSize + 1, sizeXZ - outerSize - 2, getFloorHeight(), sizeXZ - outerSize - 2, false, randomIn, blockSelectorDecoFloor);
    }

    protected void buildExteriorWallAndClean(World worldIn, Random randomIn, StructureBoundingBox sbb) {
        //basic exterior
//        StructureBottomDungeon.Start.log("building walls: %s", relPos);

        fillWithBlocks(worldIn, sbb,
                outerSize, outerSize, outerSize, sizeXZ - outerSize - 1, sizeY - outerSize - 1, sizeXZ - outerSize - 1, getWallState(), WorldGenUtil.AIR, false);
    }

    protected boolean checkArgumentValidity() {
        if (getNotPassageLength() * 2 >= sizeXZ) {
            Idealland.LogWarning("notPassage XZ is too big. Force set to 1");
            setNotPassageLength(1);
            return false;
        }

        if (getXzPassageHeight() < 3) {
            Idealland.LogWarning("xzPassageHeight is too small for floor height. Auto adjusting");
            setXzPassageHeight(3);
            return false;
        }

        if (getFloorHeight() + getXzPassageHeight() + outerSize - 1 > sizeY) {
            setFloorHeight(outerSize);
            setXzPassageHeight(3);
            Idealland.LogWarning("xz Passage is not valid, too big for this room height. Auto adjusting");
            return false;
        }

        if (getNotPassageLengthY() > sizeY) {
            Idealland.LogWarning("notPassage Y is too big. Force set to 2");
            setNotPassageLengthY(2);
            return false;
        }

        return true;
    }

    protected void buildConnections(World worldIn, Random randomIn, StructureBoundingBox sbb) {
        for (EnumFacing facing :
                connections.keySet()) {
//            Idealland.Log("building connectionsMicro %s %s, sbb = %s", relPos, facing, sbb);
            buildConnection(worldIn, randomIn, sbb, facing);
        }
    }

    //note that u have to use this sbb instead of the bounding box of this component.
    public void buildConnection(World worldIn, Random randomIn, StructureBoundingBox sbb, EnumFacing facing) {
//        StructureBottomDungeon.Start.log("connectionsMicro: %s->%s", relPos, facing);
        if (CommonFunctions.isVertical(facing)) {
            int yMin = facing == EnumFacing.DOWN ? 0 : (sizeY - outerSize - 1);
            int yMax = facing == EnumFacing.DOWN ? outerSize : (sizeY - 1);
            int xzMin = getNotPassageLength();
            int xzMax = sizeXZ - getNotPassageLength() - 1;

            for (int x = xzMin; x <= xzMax; x++) {
                for (int z = xzMin; z <= xzMax; z++) {
                    boolean isBoundary = x == xzMin || z == xzMin || x == xzMax || z == xzMax;
                    IBlockState state = (isBoundary && keepBorderY) ? getWall2() : WorldGenUtil.AIR;
                    for (int y = yMin; y <= yMax; y++) {
                        setBlockState(worldIn, state, x, y, z, sbb);
                    }
                }
            }
        } else {
            int yMin = getFloorHeight() - 1;
            int yMax = getFloorHeight() + getXzPassageHeight() - 1;

            int doorMin = getNotPassageLength();
            int doorMax = sizeXZ - getNotPassageLength() - 1;

            //Passage depth = outerSize + 1
            boolean isNegative = CommonFunctions.isNegativeDir(facing);

            int depthMin = isNegative ? 0 : (sizeXZ - outerSize - 1);
            int depthMax = isNegative ? (outerSize) : (sizeXZ - 1);

//            StructureBottomDungeon.Start.log("connectionsMicro: %d,%d,%d,%d,%d,%d", doorMin, doorMax, yMin, yMax, depthMin, depthMax);

            if (CommonFunctions.isXDir(facing)) {
                for (int z = doorMin; z <= doorMax; z++) {
                    for (int y = yMin; y <= yMax; y++) {
                        boolean isBoundary = y == yMin || z == doorMin || z == doorMax;
                        if (y == yMax) {
                            isBoundary = keepBorderY;
                        }

                        IBlockState state = (isBoundary && keepBorderXZ) ? getWall2() : WorldGenUtil.AIR;
                        for (int x = depthMin; x <= depthMax; x++) {
                            setBlockState(worldIn, state, x, y, z, sbb);
                        }
                    }
                }
            } else {
                for (int x = doorMin; x <= doorMax; x++) {
                    for (int y = yMin; y <= yMax; y++) {
                        boolean isBoundary = y == yMin || x == doorMin || x == doorMax;
                        if (y == yMax) {
                            isBoundary = keepBorderY;
                        }
                        IBlockState state = (isBoundary && keepBorderXZ) ? getWall2() : WorldGenUtil.AIR;
                        for (int z = depthMin; z <= depthMax; z++) {
                            setBlockState(worldIn, state, x, y, z, sbb);
                        }
                    }
                }
            }
        }
    }

    public void connect(EnumFacing facing, ComponentDungeonRoomBase target) {
        connections.put(facing, EnumConnection.PASS);
        if (target != null) {
            target.connections.put(facing.getOpposite(), EnumConnection.PASS);
//            StructureBottomDungeon.Start.log("connecting: %s->%s,%s", relPos, target.relPos, facing);
        } else {
            Idealland.LogWarning("Connection Failed: %s-%s", relPos, facing);
        }
    }

    public IBlockState getWallState() {
        return wallState;
    }

    public void setWallState(IBlockState wallState) {
        this.wallState = wallState;
    }

    public IBlockState getWall2() {
        return wall2;
    }

    public void setWall2(IBlockState wall2) {
        this.wall2 = wall2;
    }

    public void setFloor(IBlockState floor) {
        this.floor = floor;
    }

    public IBlockState getFloor() {
        return floor;
    }

    protected int getNotPassageLength() {
        return ModConfig.DUNGEON_CONF.notPassageLength;
    }

    protected void setNotPassageLength(int notPassageLength) {
        ModConfig.DUNGEON_CONF.notPassageLength = notPassageLength;
    }

    protected int getNotPassageLengthY() {
        return ModConfig.DUNGEON_CONF.notPassageLengthY;
    }

    protected void setNotPassageLengthY(int notPassageLengthY) {
        ModConfig.DUNGEON_CONF.notPassageLengthY = notPassageLengthY;
    }

    protected int getFloorHeight() {
        return ModConfig.DUNGEON_CONF.floorHeight;
    }

    protected void setFloorHeight(int floorHeight) {
        ModConfig.DUNGEON_CONF.floorHeight = floorHeight;
    }

    protected int getXzPassageHeight() {
        return ModConfig.DUNGEON_CONF.xzPassageHeight;
    }

    protected void setXzPassageHeight(int xzPassageHeight) {
        ModConfig.DUNGEON_CONF.xzPassageHeight = xzPassageHeight;
    }

    public static class BlockSelectorDecoFloor extends StructureComponent.BlockSelector {
        //caching is ok
        IBlockState normal = Blocks.CONCRETE.getDefaultState();
        IBlockState light = Blocks.REDSTONE_LAMP.getDefaultState();

        IBlockState state = normal;

        /**
         * picks Block Ids and Metadata
         */
        public void selectBlocks(Random rand, int x, int y, int z, boolean wall) {
            int dist = ModConfig.DUNGEON_CONF.stepLightDistance + 1;
            if (x % dist == 0 && z % dist == 0) {
                state = light;
            } else {
                state = normal;
            }
        }

        public IBlockState getBlockState() {
            return state;
        }
    }

    public int getRandomState(Random random) {
        return random.nextInt(2);
    }

    //theoretically this need to be stored into nbt to prevent duplicate spawning, but I think it's okay.
    protected void setSpawner(World worldIn, StructureBoundingBox sbb, BlockPos posRel, Class<? extends EntityLivingBase> entityClass) {
        setBlockState(worldIn, WorldGenUtil.MOB_SPAWNER, posRel.getX(), posRel.getY(), posRel.getZ(), sbb);

        BlockPos blockpos = getWorldPos(posRel);
        if (worldIn.getTileEntity(blockpos) instanceof TileEntityMobSpawner)//wrong
        {
            TileEntityMobSpawner tileEntityMobSpawner = (TileEntityMobSpawner) worldIn.getTileEntity(blockpos);
            assert tileEntityMobSpawner != null;
            tileEntityMobSpawner.getSpawnerBaseLogic().setEntityId(EntityList.getKey(entityClass));
        }
    }

    protected void setChest(World worldIn, Random rand, StructureBoundingBox sbb, BlockPos posRel) {
        setBlockState(worldIn, WorldGenUtil.BOX, posRel.getX(), posRel.getY(), posRel.getZ(), sbb);
        BlockPos blockpos = getWorldPos(posRel);
        if (worldIn.getTileEntity(blockpos) instanceof TileEntityChest)
        {
            TileEntityChest teBox = (TileEntityChest) worldIn.getTileEntity(blockpos);
            assert teBox != null;

            EnumSuit suit = EnumSuit.getSuit(worldIn, blockpos);
            SetTier tier = suit.getTierMap().get(3);
            //~4 ingot t3
            if (tier != null)
            {
                ItemStack stack = new ItemStack(tier.getGem(),
                        ModConfig.DUNGEON_CONF.ingotPerBottomChestMin + rand.nextInt(ModConfig.DUNGEON_CONF.ingotPerBottomChestDelta + 1));

                teBox.setInventorySlotContents(0, stack);
            }

            //torch 16
            ItemStack stack = new ItemStack(Blocks.TORCH,
                    ModConfig.DUNGEON_CONF.torchPerBottomChestMin + rand.nextInt(ModConfig.DUNGEON_CONF.torchPerBottomChestDelta + 1));
            teBox.setInventorySlotContents(1, stack);

            //6 bread
            stack = new ItemStack(Items.BREAD,
                    ModConfig.DUNGEON_CONF.breadPerBottomChestMin + rand.nextInt(ModConfig.DUNGEON_CONF.breadPerBottomChestDelta + 1));
            teBox.setInventorySlotContents(2, stack);

            //16 stick
            stack = new ItemStack(Items.STICK,
                    ModConfig.DUNGEON_CONF.stickPerBottomChestMin + rand.nextInt(ModConfig.DUNGEON_CONF.stickPerBottomChestDelta + 1));
            teBox.setInventorySlotContents(3, stack);
        }
    }

    public void buildCenterPillars(World worldIn, Random randomIn, StructureBoundingBox sbb)
    {
        int xzCenter = (sbb.getXSize() + 1) / 2;
        int yMin = outerSize + 1;
        int yMax = sizeY - outerSize - 2;

        fillWithBlocks(worldIn, sbb,
                xzCenter - 2, yMin, xzCenter - 2,
                xzCenter, yMax, xzCenter,
                IRON_FENCE, WorldGenUtil.STONE, false);

        fillWithBlocks(worldIn, sbb,
                xzCenter, yMin, xzCenter,
                xzCenter + 2, yMax, xzCenter + 2,
                IRON_FENCE, WorldGenUtil.STONE, false);
    }

    public BlockPos getWorldPos(BlockPos posRel)
    {
        return new BlockPos(
                this.getXWithOffset(posRel.getX(), posRel.getZ()),
                this.getYWithOffset(posRel.getY()),
                this.getZWithOffset(posRel.getX(), posRel.getZ()));
    }
}
