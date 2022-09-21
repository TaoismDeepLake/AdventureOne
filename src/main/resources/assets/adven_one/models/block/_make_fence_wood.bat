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
	echo Making %%x_wood_fence_post.json block
	(
		echo {
		echo 	"parent": "block/fence_post",
		echo 	"textures": {
		echo 		"texture": "%modid%:blocks/%%x_planks"
		echo 	}
		echo }
	) > %%x_wood_fence_post.json

	echo Making %%x_wood_fence_side.json block
    	(
    		echo {
    		echo 	"parent": "block/fence_side",
    		echo 	"textures": {
    		echo 		"texture": "%modid%:blocks/%%x_planks"
    		echo 	}
    		echo }
    	) > %%x_wood_fence_side.json

    echo Making %%x_wood_fence_inventory.json block
        	(
        		echo {
        		echo 	"parent": "block/fence_inventory",
        		echo 	"textures": {
        		echo 		"texture": "%modid%:blocks/%%x_planks"
        		echo 	}
        		echo }
        	) > %%x_wood_fence_inventory.json

	echo Making %%x_wood_fence.json item
	(
		echo {
		echo 	"parent": "%modid%:block/%%x_wood_fence_inventory"
		echo }
	) > ../item/%%x_wood_fence.json

	echo Making %%x_wood_fence.json blockstate
	(
		echo {
        echo	"multipart": [
        echo		{   "apply": { "model": "%modid%:%%x_wood_fence_post" }},
        echo		{   "when": { "north": true },
        echo			"apply": { "model": "%modid%:%%x_wood_fence_side", "uvlock": true }
        echo		},
        echo		{   "when": { "east": true },
        echo			"apply": { "model": "%modid%:%%x_wood_fence_side", "y": 90, "uvlock": true }
        echo		},
        echo		{   "when": { "south": true },
        echo			"apply": { "model": "%modid%:%%x_wood_fence_side", "y": 180, "uvlock": true }
        echo		},
        echo		{   "when": { "west": true },
        echo			"apply": { "model": "%modid%:%%x_wood_fence_side", "y": 270, "uvlock": true }
        echo		}
        echo	]
        echo }
	) > ../../blockstates/%%x_wood_fence.json
)