package com.deeplake.adven_one.designs;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.item.suit.IHasCost;
import com.deeplake.adven_one.util.CommonFunctions;
import com.deeplake.adven_one.util.EntityUtil;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.UUID;


@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class HandleCost {
    static final String MESSAGE_COST = Idealland.MODID + ".msg.cost_info";
    static final String MESSAGE_COST_OVERLOAD = Idealland.MODID + ".msg.cost_info_overload";
    static final UUID ADVANCEMENT_BUFF = UUID.fromString("98016498-e5ce-3f85-26d2-aad176146d51");

    static final String KEY_NEED_COST_INFO = "need_cost_info";
    @SubscribeEvent
    public static void onChangeEquip(LivingEquipmentChangeEvent event)
    {
        //  the stupid thing is that when this event is fired,
        //the attr modifiction is not applied yet, hence reporting the outdated info.
        //  need to come up with a solution, like making a nbt tag.
        EntityLivingBase living = event.getEntityLiving();
        if (living instanceof EntityPlayer)
        {
            ItemStack from = event.getFrom();
            ItemStack to = event.getTo();
            if (from.getItem() instanceof IHasCost || to.getItem() instanceof IHasCost)
            {
                //no need for the persistent one
                IDLNBTUtil.SetBoolean(living, KEY_NEED_COST_INFO ,true);
            }
        }
    }

    public static boolean checkOverload(EntityLivingBase livingBase)
    {
        return livingBase.getEntityAttribute(ModAttributes.COST).getAttributeValue() +
                livingBase.getEntityAttribute(ModAttributes.COST_MAX).getAttributeValue() < 0;
    }

    @SubscribeEvent
    public static void playerTick(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase livingBase = event.getEntityLiving();
        if (!livingBase.world.isRemote && livingBase instanceof EntityPlayer)
        {
            if (checkOverload(livingBase))
            {
                overload(livingBase);
            }
            if (IDLNBTUtil.GetBoolean(livingBase, KEY_NEED_COST_INFO, false))
            {
                IDLNBTUtil.SetBoolean(livingBase, KEY_NEED_COST_INFO ,false);
                sendCostInfoMsg(livingBase);
            }
        }
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        EntityPlayer player = event.player;
        World world = player.world;
        if (!world.isRemote)
        {
            resetPlayerInitCost(player);
            //  If the player has IHasCost equipped, it will trigger equip change event.
            //no need to say it here.
            //sendCostInfoMsg(player);
        }
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        resetPlayerInitCost(event.player);
    }

    @SubscribeEvent
    public static void onAdvancement(AdvancementEvent event)
    {
        EntityPlayer player = event.getEntityPlayer();
        World world = player.world;
        if (!world.isRemote)
        {
            DisplayInfo info = event.getAdvancement().getDisplay();
            int score = 0;
            if (info != null)
            {
                switch (info.getFrame())
                {
                    case CHALLENGE:
                        score += ModConfig.TIER_CONF.COST_CONF.ADVANCEMENT_COST_CHALL;
                        break;
                    case GOAL:
                        score += ModConfig.TIER_CONF.COST_CONF.ADVANCEMENT_COST_GOAL;
                        break;
                    case TASK:
                    default:
                        score += ModConfig.TIER_CONF.COST_CONF.ADVANCEMENT_COST;
                }
            }
            EntityUtil.boostAttr(player, ModAttributes.COST_MAX, score, ADVANCEMENT_BUFF);
            sendCostInfoMsg(player);
        }
    }

    private static void sendCostInfoMsg(EntityLivingBase player) {
        String key = checkOverload(player) ?
                MESSAGE_COST_OVERLOAD : MESSAGE_COST;

        CommonFunctions.SafeSendMsgToPlayer(player, key,
                -player.getEntityAttribute(ModAttributes.COST).getAttributeValue(),
                player.getEntityAttribute(ModAttributes.COST_MAX).getAttributeValue());
    }

    public static void resetPlayerInitCost(EntityPlayer player) {
        player.getEntityAttribute(ModAttributes.COST_MAX).setBaseValue(ModConfig.GENERAL_CONF.INIT_COST);
    }

    public static void overload(EntityLivingBase livingBase)
    {
        EntityUtil.ApplyBuff(livingBase, MobEffects.BLINDNESS, 0, 3);
        EntityUtil.ApplyBuff(livingBase, MobEffects.SLOWNESS, 3, 3);
        EntityUtil.ApplyBuff(livingBase, MobEffects.MINING_FATIGUE, 3, 3);
        EntityUtil.ApplyBuff(livingBase, MobEffects.WEAKNESS, 3, 3);
    }
}
