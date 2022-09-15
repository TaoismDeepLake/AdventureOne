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

public class ComponentTopDungeonRoomCenter extends ComponentTopDungeonRoomBase {

    public ComponentTopDungeonRoomCenter() {
        super();
    }

    public ComponentTopDungeonRoomCenter(int type, EnumFacing mainDirection, Vec3i vec3i, StructureTopDungeon.Start start) {
        super(type, mainDirection, vec3i, start);
    }

    protected void buildContent(World worldIn, Random randomIn, StructureBoundingBox sbb) {
        //relative pos
        int xzCenter = (sbb.getXSize() + 1) / 2;
        int yMin = outerSize + 1;
        int yMax = sizeY - outerSize - 2;

        buildCenterPillars(worldIn, randomIn, sbb);

        BlockPos pos = new BlockPos(xzCenter - 1, yMin + 1, xzCenter - 1);

        Class<? extends EntityLivingBase> entityClass = EntityTier3Mob.class;
        setSpawner(worldIn, sbb, pos, entityClass);

        entityClass = EntityTier3MobM.class;
        pos = new BlockPos(xzCenter + 1, yMin + 1, xzCenter + 1);
        setSpawner(worldIn, sbb, pos, entityClass);
    }


}
