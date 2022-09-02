package com.deeplake.adven_one.entity.creatures.suit;

import com.deeplake.adven_one.entity.creatures.EntityGeneralMob;
import com.deeplake.adven_one.entity.creatures.EntityModUnit;
import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import net.minecraft.world.World;

public class EntityDebugMob extends EntityGeneralMob {
    public EntityDebugMob(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        setAttr(32, 0.3, 4, 0, 20);
    }

    public void setTierAll(float tierAll)
    {
        setAtkTier(tierAll);
        setDefTier(tierAll);
    }

    public void setAtkTier(float tier)
    {
        getEntityAttribute(ModAttributes.ATK_TIER).setBaseValue(tier);
    }

    public void setDefTier(float tier)
    {
        getEntityAttribute(ModAttributes.DEF_TIER).setBaseValue(tier);
    }
}
