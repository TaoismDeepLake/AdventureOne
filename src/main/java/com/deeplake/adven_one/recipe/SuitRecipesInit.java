package com.deeplake.adven_one.recipe;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.recipe.traditional.*;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class SuitRecipesInit {
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
        IForgeRegistry<IRecipe> r = evt.getRegistry();

        for (EnumSuit suit : EnumSuit.values())
        {
            Item result;
            Item planks = Item.getItemFromBlock(suit.getWoodPlanks());
            Item log = Item.getItemFromBlock(suit.getWoodLog());
            Item dirt = Item.getItemFromBlock(suit.getDirt());

            r.register(new RecipeDoor(planks, Items.OAK_DOOR));
            r.register(new RecipePlanks(log, planks));

            if (suit.getTierMap().get(1) != null)
            {
                result = suit.getTierMap().get(1).getSword();
                if (result != null)
                {
                    r.register(new RecipeSword2(planks, result, dirt));
                }
            }

            for (SetTier setTier : suit.getTierMap().values())
            {
                Item gem = setTier.getGem();

                if (gem != null)
                {
                    result = setTier.getPick();
                    if (result != null)
                    {
                        r.register(new RecipePickaxe(gem, result));
                    }

                    result = setTier.getSword();
                    if (result != null)
                    {
                        r.register(new RecipeSword(gem, result));
                    }

                    result = setTier.getHead();
                    if (result != null)
                    {
                        r.register(new RecipeArmorHelm(gem, result));
                    }

                    result = setTier.getChest();
                    if (result != null)
                    {
                        r.register(new RecipeArmorChest(gem, result));
                    }

                    result = setTier.getLeg();
                    if (result != null)
                    {
                        r.register(new RecipeArmorPants(gem, result));
                    }

                    result = setTier.getFeet();
                    if (result != null)
                    {
                        r.register(new RecipeArmorShoes(gem, result));
                    }

                    result = Item.getItemFromBlock(setTier.getGemBlock());
                    if (result != null)
                    {
                        r.register(new RecipeGemBlock(gem, result));
                    }
                }
            }
        }

    }
}
