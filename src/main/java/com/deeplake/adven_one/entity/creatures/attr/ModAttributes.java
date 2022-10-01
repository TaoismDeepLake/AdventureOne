package com.deeplake.adven_one.entity.creatures.attr;

import com.deeplake.adven_one.Idealland;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ModAttributes {

    static final double MIN = -9999999;
    static final double MAX = 999999f;
    public static float BASE_CRIT_DMG = 50;

    public static final HashSet<IAttribute> allNewAttrs = new HashSet<>();

    public static final IAttribute DEF_TIER = getNewAttrNonpercent("def_tier");
    public static final IAttribute ATK_TIER = getNewAttrNonpercent("atk_tier");
    public static final IAttribute EFFECIENCY = getNewAttrNonpercent("efficiency");

    @SubscribeEvent
    public static void onConstruct(EntityEvent.EntityConstructing entityConstructing)
    {
        Entity entity = entityConstructing.getEntity();
        if (entity instanceof EntityLivingBase)
        {
            EntityLivingBase livingBase = (EntityLivingBase) entity;
            for (IAttribute attr:
                    allNewAttrs) {
                livingBase.getAttributeMap().registerAttribute(attr);
            }
        }
    }

    //"description" is merely another matching name.
    //used by net.minecraft.entity.ai.attributes.AttributeMap::getAttributeInstanceByName
    //which is used in some places. It is not intended for human reading nor translation, more like a key.
    public static IAttribute getNewAttr(String name)
    {
        IAttribute attribute = new RangedAttribute(null, getAttrName(name), 0, MIN, MAX).setDescription(name).setShouldWatch(false);
        allNewAttrs.add(attribute);
        return attribute;
    }

    public static IAttribute getNewAttrNonpercent(String name)
    {
        IAttribute attribute = new RangedAttribute(null, getAttrName(name), 0, MIN, MAX).setDescription(name).setShouldWatch(false);
        allNewAttrs.add(attribute);
        return attribute;
    }

    static final String MID_NAME = ".attr.";
    static String getAttrName(String name){
        return Idealland.MODID + MID_NAME + name;
    }
}
