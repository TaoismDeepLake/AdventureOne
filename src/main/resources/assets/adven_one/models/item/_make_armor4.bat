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
	echo Making %%x_armor0.json item
        	(
        		echo {
        		echo 	"parent": "minecraft:item/handheld",
                echo    "textures": {
                echo    "layer0": "%modid%:items/misc/%%x_armor0",
                echo    "layer1":"%modid%:items/overlay_4"
                echo    }
        		echo }
        	) > %%x_armor0.json

        	echo Making %%x_armor1.json item
            (
            	echo {
            	echo 	"parent": "minecraft:item/handheld",
                echo    "textures": {
                echo    "layer0": "%modid%:items/misc/%%x_armor1",
                echo    "layer1":"%modid%:items/overlay_4"
                echo    }
            		echo }
            ) > %%x_armor1.json

            echo Making %%x_armor2.json item
            (
                echo {
                echo 	"parent": "minecraft:item/handheld",
                echo    "textures": {
                echo    "layer0": "%modid%:items/misc/%%x_armor2",
                echo    "layer1":"%modid%:items/overlay_4"
                echo    }
                echo }
            ) > %%x_armor2.json

            echo Making %%x_armor3.json item
            (
                 echo {
                 echo 	"parent": "minecraft:item/handheld",
                 echo    "textures": {
                 echo    "layer0": "%modid%:items/misc/%%x_armor3",
                 echo    "layer1":"%modid%:items/overlay_4"
                 echo    }
                 echo }
            ) > %%x_armor3.json
)