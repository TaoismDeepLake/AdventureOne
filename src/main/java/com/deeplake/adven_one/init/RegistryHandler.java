package com.deeplake.adven_one.init;

import com.deeplake.adven_one.blocks.ModBlocks;
import com.deeplake.adven_one.blocks.blockSuit.IBlockSuit;
import com.deeplake.adven_one.command.CommandDimTeleport;
import com.deeplake.adven_one.designs.EnumSuit;
import com.deeplake.adven_one.enchantments.ModEnchantmentInit;
import com.deeplake.adven_one.entity.ModEntityInit;
import com.deeplake.adven_one.entity.RenderHandler;
import com.deeplake.adven_one.item.ItemBlockBase;
import com.deeplake.adven_one.item.ModItems;
import com.deeplake.adven_one.item.suit.modifiers.ModifierList;
import com.deeplake.adven_one.util.IHasModel;
import com.deeplake.adven_one.util.ModSoundHandler;
import com.deeplake.adven_one.world.dimension.InitDimension;
import com.deeplake.adven_one.worldgen.InitWorldGen;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

import static com.deeplake.adven_one.world.structure.InitGiantStructures.registerGiantStructure;

@EventBusSubscriber
public class RegistryHandler {
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
	}

	@SubscribeEvent
	public static void onEnchantmentRegister(RegistryEvent.Register<Enchantment> event)
	{
		ModEnchantmentInit.BeforeRegister();
		event.getRegistry().registerAll(ModEnchantmentInit.ENCHANTMENT_LIST.toArray(new Enchantment[0]));

//		for (Enchantment enchantment : Enchantment.REGISTRY) {
//			IdlFramework.Log("registered enchantments: %s", enchantment.getName());
//		}
	}

	public static void addToItems(Block block)
	{
		if (block instanceof IBlockSuit)
		{
			ModItems.ITEMS.add(new ItemBlockBase(block).setRegistryName(Objects.requireNonNull(block.getRegistryName())));
		}
		else {
			ModItems.ITEMS.add(new ItemBlock(block).setRegistryName(Objects.requireNonNull(block.getRegistryName())));
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Item item : ModItems.ITEMS)
		{
			if (item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
			}
		}
		
		for(Block block : ModBlocks.BLOCKS)
		{
			if (block instanceof IHasModel)
			{
				((IHasModel)block).registerModels();
			}
		}

		RenderHandler.registerEntityRenders();
	}

	public static void preInitRegistries(FMLPreInitializationEvent event)
	{
		EnumSuit.init();
		EnumSuit.initLeavesColor();

		InitBiome.registerBiomes();
		InitDimension.registerDimensions();

		ModEntityInit.registerEntities();

		registerGiantStructure();
		InitWorldGen.registerWorldGen();
	}

	public static void postInitReg()
	{
		//WorldType TYPE_ONE = new WorldTypeOne();
		EnumSuit.initOreDict();
		ModifierList.initModifier();
	}

	public static void initRegistries(FMLInitializationEvent event)
	{
		ModSoundHandler.soundRegister();
	}

	public static void serverRegistries(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandDimTeleport());
    }
}
