package com.deeplake.adven_one.designs;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.item.suit.IHasModifiers;
import com.deeplake.adven_one.item.suit.IHasQuality;
import com.deeplake.adven_one.item.suit.modifiers.EnumFeature;
import com.deeplake.adven_one.item.suit.modifiers.EnumModifier;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTDef;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = Idealland.MODID,value = Side.CLIENT)
public class HandleItemDesc {
    static final String MODIFIER_LEVEL_FORMAT = Idealland.MODID + ".desc.modifier";
    static final String FEATURE_LEVEL_FORMAT = Idealland.MODID + ".desc.feature";
    static final String FEATURE_FORMAT = Idealland.MODID + ".desc.feature.no_lv";
    static final String OREDICT_PREFIX = "oredict.";
    static final String EXTRA_COUNT = Idealland.MODID + ".desc.extra_count";

    @SubscribeEvent
    public static void onToolTip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        displayExtraCount(event, stack);
        displayQuality(event, stack);
        displayOredict(event, stack);
        displayModifier(event, stack);
    }

    private static void displayExtraCount(ItemTooltipEvent event, ItemStack stack) {

        int count = IDLNBTUtil.GetInt(stack, IDLNBTDef.EXTRA_COUNT_NBT);
        if (count > 0)
        {
            event.getToolTip().add(I18n.format(EXTRA_COUNT, count));
        }
    }

    private static void displayOredict(ItemTooltipEvent event, ItemStack stack) {
        if (ModConfig.TOOL_TIP_CONF.SHOW_ORE_DICT)
        {
            int[] ids = OreDictionary.getOreIDs(stack);
            if (ids.length > 0)
            {
                StringBuilder stringBuilder = new StringBuilder();
                for(int i : ids)
                {
                    stringBuilder.append(I18n.format(OREDICT_PREFIX+OreDictionary.getOreName(i)));
                }

                event.getToolTip().add(stringBuilder.toString());
            }
        }
    }

    private static void displayModifier(ItemTooltipEvent event, ItemStack stack) {
        if (stack.getItem() instanceof IHasModifiers) {
            IHasModifiers iHasModifiers = (IHasModifiers) stack.getItem();
            if (iHasModifiers.needFirstTick(stack)) {
                //event.getToolTip().add(I18n.format("adven_one.shared.quality_random"));
            }
            else
            {
                HashMap<EnumFeature, Integer> map2 = iHasModifiers.getAllFeatureFromNBT(stack);
                if (!map2.isEmpty())
                {
                    for (EnumFeature feature : map2.keySet())
                    {
                        if (feature.getMaxLv() == 1)
                        {
                            event.getToolTip().add(I18n.format(FEATURE_FORMAT, I18n.format(feature.getLangKey())));
                        }
                        else {
                            int lv = map2.get(feature);
                            event.getToolTip().add(
                                    I18n.format(FEATURE_LEVEL_FORMAT,
                                            I18n.format(feature.getLangKey()), lv)
                            );
                        }
                    }
                }

                HashMap<EnumModifier, Integer> map = iHasModifiers.getAllModiFromNBT(stack);
                for (EnumModifier modifier : map.keySet())
                {
                    if (modifier.getMaxLv() == 1)
                    {
                        event.getToolTip().add(I18n.format(modifier.getLangKey()));
                    }
                    else {
                        int lv = map.get(modifier);
                        event.getToolTip().add(
                                I18n.format(MODIFIER_LEVEL_FORMAT,
                                    I18n.format(modifier.getLangKey()), lv)
                        );
                    }
                }


            }
        }
    }

    private static void displayQuality(ItemTooltipEvent event, ItemStack stack) {
        if (stack.getItem() instanceof IHasQuality)
        {
            IHasQuality iHasQuality = (IHasQuality) stack.getItem();
            if (iHasQuality.needFirstTick(stack))
            {
                event.getToolTip().add(I18n.format("adven_one.shared.quality_random"));
            }
            else {
                double quality = iHasQuality.getQuality(stack);
                if (quality < 1.0f)
                {
                    event.getToolTip().add(I18n.format("adven_one.shared.quality_desc_bad", String.format("%.2f", quality * 100)));
                }else {
                    event.getToolTip().add(I18n.format("adven_one.shared.quality_desc_good", String.format("%.2f", quality * 100)));
                }
            }
        }
    }
}
