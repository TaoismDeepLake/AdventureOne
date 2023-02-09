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
	echo Making %%x_grass.json block
	(
		echo {
        echo  	"parent": "block/cube",
        echo  	"textures": {
        echo  		"particle": "%modid%:blocks/%%x_dirt",
        echo  		"down": "%modid%:blocks/%%x_dirt",
        echo  		"up": "%modid%:blocks/%%x_grass_top",
        echo  		"north": "%modid%:blocks/%%x_grass_side",
        echo  		"south": "%modid%:blocks/%%x_grass_side",
        echo  		"west": "%modid%:blocks/%%x_grass_side",
        echo  		"east": "%modid%:blocks/%%x_grass_side"
        echo  	}
        echo  }
	) > %%x_grass.json

	echo Making %%x_grass.json item
	(
		echo {
		echo 	"parent": "%modid%:block/%%x_grass"
		echo }
	) > ../item/%%x_grass.json

	echo Making %%x_grass.json blockstate
	(
        echo {
        echo 	"variants": {
        echo 		"normal": { "model": "%modid%:%%x_grass" }
        echo 	}
        echo }
	) > ../../blockstates/%%x_grass.json
)
