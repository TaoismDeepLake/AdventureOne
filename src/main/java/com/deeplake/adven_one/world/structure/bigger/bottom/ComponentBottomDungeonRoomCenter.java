package com.deeplake.adven_one.world.structure.bigger.bottom;

import com.deeplake.adven_one.entity.creatures.suit.EntityTier2Mob;
import com.deeplake.adven_one.entity.creatures.suit.EntityTier2MobM;
import com.deeplake.adven_one.util.WorldGenUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import java.util.Random;

public class ComponentBottomDungeonRoomCenter extends ComponentDungeonRoomBase{

    public ComponentBottomDungeonRoomCenter() {
        super();
    }

    public ComponentBottomDungeonRoomCenter(int type, EnumFacing mainDirection, Vec3i vec3i, StructureBottomDungeon.Start start) {
        super(type, mainDirection, vec3i, start);
    }

    protected void buildContent(World worldIn, Random randomIn, StructureBoundingBox sbb) {
        //relative pos
        int xzCenter = (sbb.getXSize() + 1) / 2;
        int yMin = outerSize + 1;
        int yMax = sizeY - outerSize - 2;

        buildCenterPillars(worldIn, randomIn, sbb);

        BlockPos pos = new BlockPos(xzCenter - 1, yMin + 1, xzCenter - 1);

        Class<? extends EntityLivingBase> entityClass = EntityTier2Mob.class;
        setSpawner(worldIn, sbb, pos, entityClass);

        entityClass = EntityTier2MobM.class;
        pos = new BlockPos(xzCenter + 1, yMin + 1, xzCenter + 1);
        setSpawner(worldIn, sbb, pos, entityClass);
    }


}
