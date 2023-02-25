package com.deeplake.adven_one.recipe;

import com.deeplake.adven_one.Idealland;
import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.designs.SetTier;
import com.deeplake.adven_one.recipe.misc.RecipeHandle;
import com.deeplake.adven_one.recipe.special.RecipeIdentify;
import com.deeplake.adven_one.recipe.traditional.*;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class SuitRecipesInit {
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
        IForgeRegistry<IRecipe> r = evt.getRegistry();

        r.register(new RecipeIdentify().setRegistryName(new ResourceLocation(Idealland.MODID, "identify")));

        registerMisc(r);

        for (EnumSuit suit : EnumSuit.values())
        {
            if (suit.isHidden())
            {
                continue;
            }

            Item dirt = Item.getItemFromBlock(suit.getDirt());

            //stone block
            Item stone = Item.getItemFromBlock(suit.getStone());
            Item stoneStairs = Item.getItemFromBlock(suit.getStoneStairs());
            Item stoneWall = Item.getItemFromBlock(suit.getStoneWall());
            Item stoneSlab = Item.getItemFromBlock(suit.getStoneSlab());

            //wooden block
            Item log = Item.getItemFromBlock(suit.getWoodLog());
            Item planks = Item.getItemFromBlock(suit.getWoodPlanks());
            Item woodStairs = Item.getItemFromBlock(suit.getWoodStairs());
            Item woodFence = Item.getItemFromBlock(suit.getWoodFence());
            Item woodSlab = Item.getItemFromBlock(suit.getWoodSlab());

            registerBuildingBlocks(r, stone, stoneStairs, stoneWall, stoneSlab, log, planks, woodStairs, woodFence, woodSlab);

            registerT1SpecialGear(r, suit, planks);

            registerGeneralTierGear(r, suit);
        }

    }

    private static void registerBuildingBlocks(IForgeRegistry<IRecipe> r, Item stone, Item stoneStairs, Item stoneWall, Item stoneSlab, Item log, Item planks, Item woodStairs, Item woodFence, Item woodSlab) {
        if (isValid(planks))
        {
            r.register(new RecipeDoor(planks, Items.OAK_DOOR));
            if (isValid(woodFence))
            {
                r.register(new RecipeWoodFence(planks, woodFence));
            }
            if (isValid(woodStairs))
            {
                r.register(new RecipeStairs(planks, woodStairs));
            }
            if (isValid(woodSlab))
            {
                r.register(new RecipeWoodSlab(planks, woodSlab));
            }
            if (isValid(log))
            {
                r.register(new RecipePlanks(log, planks));
            }
        }

        if (isValid(stone))
        {
            if (isValid(stoneStairs))
            {
                r.register(new RecipeStairs(stone, stoneStairs));
            }

            if (isValid(stoneWall))
            {
                r.register(new RecipeStoneWall(stone, stoneWall));
            }
            if (isValid(woodSlab))
            {
                r.register(new RecipeStoneSlab(stone, stoneSlab));
            }
        }
    }

    private static void registerT1SpecialGear(IForgeRegistry<IRecipe> r, EnumSuit suit, Item planks) {
        Item result;
        if (suit.getTierMap().get(1) != null)
        {
            result = suit.getTierMap().get(1).getSword();
            if (isValid(result))
            {
                r.register(new RecipeSword2(planks, result));
            }

            result = suit.getTierMap().get(1).getPick();
            if (isValid(result))
            {
                r.register(new RecipePickaxeT1(planks, result));
            }
        }
    }

    private static void registerGeneralTierGear(IForgeRegistry<IRecipe> r, EnumSuit suit) {
        Item result;
        for (SetTier setTier : suit.getTierMap().values())
        {
            Item gem = setTier.getGem();

            if (isValid(gem))
            {
                result = setTier.getPick();
                if (isValid(result))
                {
                    r.register(new RecipePickaxe(gem, result));
                }

                result = setTier.getSword();
                if (isValid(result))
                {
                    r.register(new RecipeSword(gem, result));
                }

                result = setTier.getHead();
                if (isValid(result))
                {
                    r.register(new RecipeArmorHelm(gem, result));
                }

                result = setTier.getChest();
                if (isValid(result))
                {
                    r.register(new RecipeArmorChest(gem, result));
                }

                result = setTier.getLeg();
                if (isValid(result))
                {
                    r.register(new RecipeArmorPants(gem, result));
                }

                result = setTier.getFeet();
                if (isValid(result))
                {
                    r.register(new RecipeArmorShoes(gem, result));
                }

                result = Item.getItemFromBlock(setTier.getGemBlock());
                if (isValid(result))
                {
                    r.register(new RecipeGemBlock(gem, result));
                }
            }
        }
    }

    private static void registerMisc(IForgeRegistry<IRecipe> r)
    {
        r.register(new RecipeHandle());
    }

    private static boolean isValid(Item itemIn) {
        return itemIn != null && itemIn != Items.AIR;
    }
}
