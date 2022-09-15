package com.deeplake.adven_one.world.structure.bigger.top;

import com.deeplake.adven_one.entity.creatures.suit.EntityTier2Mob;
import com.deeplake.adven_one.entity.creatures.suit.EntityTier2MobM;
import com.deeplake.adven_one.entity.creatures.suit.EntityTier3Mob;
import com.deeplake.adven_one.entity.creatures.suit.EntityTier3MobM;
import com.deeplake.adven_one.world.structure.bigger.bottom.ComponentDungeonRoomBase;
import com.deeplake.adven_one.world.structure.bigger.bottom.StructureBottomDungeon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import java.util.Random;

public class ComponentTopDungeonRoomSide extends ComponentTopDungeonRoomBase {

    public ComponentTopDungeonRoomSide() {
        super();
    }

    public ComponentTopDungeonRoomSide(int type, EnumFacing mainDirection, Vec3i vec3i, StructureTopDungeon.Start start) {
        super(type, mainDirection, vec3i, start);
    }

    protected void buildContent(World worldIn, Random randomIn, StructureBoundingBox sbb) {
        //relative pos
        buildCenterPillars(worldIn, randomIn, sbb);
        setRandomThing(worldIn, randomIn, sbb);
    }

    void setRandomThing(World worldIn, Random randomIn, StructureBoundingBox sbb)
    {
        int xzCenter = (sbb.getXSize() + 1) / 2;
        int yMin = outerSize + 1;
        int yMax = sizeY - outerSize - 2;

        BlockPos pos = new BlockPos(xzCenter - 1, (yMin + yMax)/2 , xzCenter - 1);
        setRandomThing(worldIn, randomIn, sbb, pos);

        pos = new BlockPos(xzCenter + 1, (yMin + yMax)/2 , xzCenter + 1);
        setRandomThing(worldIn, randomIn, sbb, pos);
    }

    private void setRandomThing(World worldIn, Random randomIn, StructureBoundingBox sbb, BlockPos pos) {
        if (randomIn.nextBoolean())
        {
            //chest
            setChest(worldIn, randomIn, sbb, pos);
        }
        else
        {
            //spawner
            Class<? extends EntityLivingBase> entityClass = randomIn.nextBoolean() ? EntityTier3Mob.class : EntityTier3MobM.class;
            setSpawner(worldIn, sbb, pos, entityClass);
        }
    }
}
