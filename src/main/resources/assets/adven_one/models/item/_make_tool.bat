@echo off
:: Vazkii's JSON creator for blocks
:: Put in your /resources/assets/%modid%/models/block
:: Makes basic block JSON files as well as the acossiated item and simple blockstate
:: Can make multiple blocks at once
::
:: Usage:
:: _make (block name 1) (block name 2) (block name x)
::
:: Change this to your mod's ID
set modid=adven_one

setlocal enabledelayedexpansion

for %%x in (%*) do (
	echo Making %%x_1_pickaxe.json item
	(
		echo {
		echo 	"parent": "minecraft:item/handheld",
        echo    "textures": {
        echo    "layer0": "%modid%:items/misc/%%x_1_pickaxe"
        echo    "layer1":"%modid%:items/overlay_1"
        echo    }
		echo }
	) > %%x_1_pickaxe.json

	echo Making %%x_1_sword.json item
    	(
    		echo {
    		echo 	"parent": "minecraft:item/handheld",
            echo    "textures": {
            echo    "layer0": "%modid%:items/misc/%%x_1_sword"
            echo    "layer1":"%modid%:items/overlay_1"
            echo    }
    		echo }
    	) > %%x_1_sword.json

    echo Making %%x_2_pickaxe.json item
    	(
    		echo {
    		echo 	"parent": "minecraft:item/handheld",
            echo    "textures": {
            echo    "layer0": "%modid%:items/misc/%%x_2_pickaxe"
            echo    "layer1":"%modid%:items/overlay_2"
            echo    }
    		echo }
    	) > %%x_2_pickaxe.json

    	echo Making %%x_2_sword.json item
        	(
        		echo {
        		echo 	"parent": "minecraft:item/handheld",
                echo    "textures": {
                echo    "layer0": "%modid%:items/misc/%%x_2_sword"
                echo    "layer1":"%modid%:items/overlay_2"
                echo    }
        		echo }
        	) > %%x_2_sword.json

    echo Making %%x_3_pickaxe.json item
    	(
    		echo {
    		echo 	"parent": "minecraft:item/handheld",
            echo    "textures": {
            echo    "layer0": "%modid%:items/misc/%%x_3_pickaxe"
            echo    "layer1":"%modid%:items/overlay_3"
            echo    }
    		echo }
    	) > %%x_3_pickaxe.json

    	echo Making %%x_3_sword.json item
        	(
        		echo {
        		echo 	"parent": "minecraft:item/handheld",
                echo    "textures": {
                echo    "layer0": "%modid%:items/misc/%%x_3_sword"
                echo    "layer1":"%modid%:items/overlay_3"
                echo    }
        		echo }
        	) > %%x_3_sword.json

    echo Making %%x_4_pickaxe.json item
    	(
    		echo {
    		echo 	"parent": "minecraft:item/handheld",
            echo    "textures": {
            echo    "layer0": "%modid%:items/misc/%%x_4_pickaxe"
            echo    "layer1":"%modid%:items/overlay_4"
            echo    }
    		echo }
    	) > %%x_4_pickaxe.json

    	echo Making %%x_4_sword.json item
        	(
        		echo {
        		echo 	"parent": "minecraft:item/handheld",
                echo    "textures": {
                echo    "layer0": "%modid%:items/misc/%%x_4_sword"
                echo    "layer1":"%modid%:items/overlay_4"
                echo    }
        		echo }
        	) > %%x_4_sword.json
)