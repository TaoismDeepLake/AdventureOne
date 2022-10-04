package com.deeplake.adven_one.item.suit.modifiers;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.init.ModConfig;
import com.deeplake.adven_one.item.suit.IHasModifiers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class HandleCombatModifier {
    @SubscribeEvent
    public static void onAtk(LivingHurtEvent event)
    {
        float damageBonus = 0;
        EntityLivingBase hurtOne = event.getEntityLiving();
        if (!hurtOne.world.isRemote)
        {
            DamageSource source = event.getSource();
            if (source.getTrueSource() instanceof EntityLivingBase)
            {
                EntityLivingBase attacker = (EntityLivingBase) source.getTrueSource();
                ItemStack mainHand = attacker.getHeldItemMainhand();
                if (mainHand.getItem() instanceof IHasModifiers)
                {
                    IHasModifiers modified = (IHasModifiers) mainHand.getItem();
                    HashMap<EnumModifier, Integer> map = modified.getAllFromNBT(mainHand);

                    damageBonus = getDamageBonusA(map, EnumModifier.KILL_ENDERMAN, hurtOne instanceof EntityEnderman, damageBonus);
                    damageBonus = getDamageBonusA(map, EnumModifier.KILL_CREEPER, hurtOne instanceof EntityCreeper, damageBonus);
                    damageBonus = getDamageBonusA(map, EnumModifier.KILL_PLAYER, hurtOne instanceof EntityPlayer, damageBonus);

                    Item hurtMainHand = hurtOne.getHeldItemMainhand().getItem();
                    damageBonus = getDamageBonusB(map, EnumModifier.KILL_MELEE, !(hurtMainHand instanceof ItemBow), damageBonus);
                    damageBonus = getDamageBonusB(map, EnumModifier.KILL_BOW, hurtMainHand instanceof ItemBow, damageBonus);
                }
            }
            event.setAmount(event.getAmount() + damageBonus);
        }
    }

    private static float getDamageBonusA(HashMap<EnumModifier, Integer> map, EnumModifier killPlayer, boolean hurtOne, float damageBonus) {
        int level = map.getOrDefault(killPlayer, 0);
        if (level > 0 && hurtOne)
        {
            damageBonus += level * ModConfig.MODIFIER_CONF.ATK_FIXED_GROUP.VALUE_A;
        }
        return damageBonus;
    }

    private static float getDamageBonusB(HashMap<EnumModifier, Integer> map, EnumModifier killPlayer, boolean hurtOne, float damageBonus) {
        int level = map.getOrDefault(killPlayer, 0);
        if (level > 0 && hurtOne)
        {
            damageBonus += level * ModConfig.MODIFIER_CONF.ATK_FIXED_GROUP.VALUE_B;
        }
        return damageBonus;
    }

    @SubscribeEvent
    public static void onCrit(CriticalHitEvent event) {
//        float damageBonus = 0;
        if (event.getTarget() instanceof EntityLivingBase)
        {
            EntityLivingBase hurtOne = (EntityLivingBase) event.getTarget();
            if (!hurtOne.world.isRemote) {
                EntityPlayer attacker = event.getEntityPlayer();;
                ItemStack mainHand = attacker.getHeldItemMainhand();
                if (mainHand.getItem() instanceof IHasModifiers) {
                    IHasModifiers modified = (IHasModifiers) mainHand.getItem();
                    HashMap<EnumModifier, Integer> map = modified.getAllFromNBT(mainHand);

                    checkCrit(event, hurtOne, EntityPig.class, EnumModifier.KILL_PIG, map);
                    checkCrit(event, hurtOne, EntityChicken.class, EnumModifier.KILL_CHICK, map);
                    checkCrit(event, hurtOne, EntitySheep.class, EnumModifier.KILL_SHEEP, map);
                    checkCrit(event, hurtOne, EntityCow.class, EnumModifier.KILL_COW, map);
                }
            }
        }
    }

    private static void checkCrit(CriticalHitEvent event, EntityLivingBase hurtOne, Class baseClass, EnumModifier modifier, HashMap<EnumModifier, Integer> map) {
        if (map.containsKey(modifier) && baseClass.isAssignableFrom(hurtOne.getClass()))
            event.setResult(Event.Result.ALLOW);
    }

}
