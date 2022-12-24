package com.deeplake.adven_one.command;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.HandleCost;
import com.deeplake.adven_one.entity.creatures.attr.ModAttributes;
import com.deeplake.adven_one.util.CommonFunctions;
import com.deeplake.adven_one.util.EntityUtil;
import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

import static com.deeplake.adven_one.item.food.ItemFoodCost.FOOD_COST;

public class CommandAddCost extends CommandBase {

    private final List<String> aliases = Lists.newArrayList(Idealland.MODID, "addcost");

    @Override
    public String getName() {
        return "addcost";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "addcost <base_cost>";
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }


    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            return;
        }

        String s = args[0];
        int cost;

        try{
            cost = Integer.parseInt(s);
        }catch (NumberFormatException e)
        {
            if (sender instanceof EntityPlayerMP)
            {
                CommonFunctions.SendMsgToPlayerStyled((EntityPlayerMP) sender, "idlframewok.msg.dim_id_invalid", TextFormatting.RED, s);
            }
            return;
        }

        if (sender instanceof EntityPlayer)
        {
            try{
                EntityUtil.boostAttr((EntityLivingBase) sender, ModAttributes.COST_MAX, cost, FOOD_COST);
                HandleCost.sendCostInfoMsg((EntityLivingBase) sender);
            }
            catch (IllegalArgumentException e)
            {
                CommonFunctions.SendMsgToPlayerStyled((EntityPlayerMP) sender, "idlframewok.msg.dim_id_invalid", TextFormatting.RED, s);
            }
        }
    }
}
