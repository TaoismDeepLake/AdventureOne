package com.deeplake.adven_one.init;

import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.util.Reference;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModRecipes {
	public static void Init() {
		//Only smelting recipes
		for (EnumSuit suit : EnumSuit.values()) {
			Item result;
			Item planks = Item.getItemFromBlock(suit.getWoodPlanks());
			Item dirt = Item.getItemFromBlock(suit.getDirt());
			Item stone = Item.getItemFromBlock(suit.getStone());

//			GameRegistry.addSmelting(planks,
//					new ItemStack(Items.COAL),
//					1f);
//
//			GameRegistry.addSmelting(dirt,
//					new ItemStack(Blocks.DIRT),
//					1f);

			GameRegistry.addSmelting(stone,
					new ItemStack(Blocks.STONE),
					1f);
		}
	}
	
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		IForgeRegistry<IRecipe> r = evt.getRegistry();
		//Example
		//r.register(new GobletFill().setRegistryName(new ResourceLocation(Reference.MOD_ID, "goblet_fill")));
	}
}
