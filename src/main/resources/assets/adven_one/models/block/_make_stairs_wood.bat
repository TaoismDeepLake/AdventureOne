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
	echo Making %%x_wood_inner_stairs.json block
	(
		echo {
		echo 	"parent": "block/inner_stairs",
		echo 	"textures": {
		echo 		"bottom": "%modid%:blocks/%%x",
        echo        "top": "%modid%:blocks/%%x",
        echo        "side": "%modid%:blocks/%%x"
		echo 	}
		echo }
	) > %%x_wood_inner_stairs.json

	echo Making %%x_wood_outer_stairs.json block
    	(
    		echo {
    		echo 	"parent": "block/outer_stairs",
    		echo 	"textures": {
    		echo 		"bottom": "%modid%:blocks/%%x_planks",
            echo        "top": "%modid%:blocks/%%x_planks",
            echo        "side": "%modid%:blocks/%%x_planks"
    		echo 	}
    		echo }
    	) > %%x_wood_outer_stairs.json

	echo Making %%x_wood_stairs.json item
	(
		echo {
		echo 	"parent": "%modid%:block/%%x_wood_stairs"
		echo }
	) > ../item/%%x_wood_stairs.json

	echo Making %%x_wood_stairs.json blockstate
	(
		echo {
        echo	"variants": {
        echo		    "facing=east,half=bottom,shape=straight":  { "model": "%modid%:%%x_wood_stairs" },
        echo		    "facing=west,half=bottom,shape=straight":  { "model": "%modid%:%%x_wood_stairs", "y": 180, "uvlock": true },
        echo		    "facing=south,half=bottom,shape=straight": { "model": "%modid%:%%x_wood_stairs", "y": 90, "uvlock": true },
        echo		    "facing=north,half=bottom,shape=straight": { "model": "%modid%:%%x_wood_stairs", "y": 270, "uvlock": true },
        echo		    "facing=east,half=bottom,shape=outer_right":  { "model": "%modid%:%%x_wood_outer_stairs" },
        echo		    "facing=west,half=bottom,shape=outer_right":  { "model": "%modid%:%%x_wood_outer_stairs", "y": 180, "uvlock": true },
        echo		    "facing=south,half=bottom,shape=outer_right": { "model": "%modid%:%%x_wood_outer_stairs", "y": 90, "uvlock": true },
        echo	    	"facing=north,half=bottom,shape=outer_right": { "model": "%modid%:%%x_wood_outer_stairs", "y": 270, "uvlock": true },
        echo		    "facing=east,half=bottom,shape=outer_left":  { "model": "%modid%:%%x_wood_outer_stairs", "y": 270, "uvlock": true },
        echo	    	"facing=west,half=bottom,shape=outer_left":  { "model": "%modid%:%%x_wood_outer_stairs", "y": 90, "uvlock": true },
        echo		    "facing=south,half=bottom,shape=outer_left": { "model": "%modid%:%%x_wood_outer_stairs" },
        echo	    	"facing=north,half=bottom,shape=outer_left": { "model": "%modid%:%%x_wood_outer_stairs", "y": 180, "uvlock": true },
        echo		    "facing=east,half=bottom,shape=inner_right":  { "model": "%modid%:%%x_wood_inner_stairs" },
        echo	    	"facing=west,half=bottom,shape=inner_right":  { "model": "%modid%:%%x_wood_inner_stairs", "y": 180, "uvlock": true },
        echo	    	"facing=south,half=bottom,shape=inner_right": { "model": "%modid%:%%x_wood_inner_stairs", "y": 90, "uvlock": true },
        echo	    	"facing=north,half=bottom,shape=inner_right": { "model": "%modid%:%%x_wood_inner_stairs", "y": 270, "uvlock": true },
        echo	    	"facing=east,half=bottom,shape=inner_left":  { "model": "%modid%:%%x_wood_inner_stairs", "y": 270, "uvlock": true },
        echo	    	"facing=west,half=bottom,shape=inner_left":  { "model": "%modid%:%%x_wood_inner_stairs", "y": 90, "uvlock": true },
        echo	    	"facing=south,half=bottom,shape=inner_left": { "model": "%modid%:%%x_wood_inner_stairs" },
        echo	    	"facing=north,half=bottom,shape=inner_left": { "model": "%modid%:%%x_wood_inner_stairs", "y": 180, "uvlock": true },
        echo	    	"facing=east,half=top,shape=straight":  { "model": "%modid%:%%x_wood_stairs", "x": 180, "uvlock": true },
        echo		    "facing=west,half=top,shape=straight":  { "model": "%modid%:%%x_wood_stairs", "x": 180, "y": 180, "uvlock": true },
        echo		    "facing=south,half=top,shape=straight": { "model": "%modid%:%%x_wood_stairs", "x": 180, "y": 90, "uvlock": true },
        echo	    	"facing=north,half=top,shape=straight": { "model": "%modid%:%%x_wood_stairs", "x": 180, "y": 270, "uvlock": true },
        echo	    	"facing=east,half=top,shape=outer_right":  { "model": "%modid%:%%x_wood_outer_stairs", "x": 180, "y": 90, "uvlock": true },
        echo	    	"facing=west,half=top,shape=outer_right":  { "model": "%modid%:%%x_wood_outer_stairs", "x": 180, "y": 270, "uvlock": true },
        echo	    	"facing=south,half=top,shape=outer_right": { "model": "%modid%:%%x_wood_outer_stairs", "x": 180, "y": 180, "uvlock": true },
        echo	    	"facing=north,half=top,shape=outer_right": { "model": "%modid%:%%x_wood_outer_stairs", "x": 180, "uvlock": true },
        echo	    	"facing=east,half=top,shape=outer_left":  { "model": "%modid%:%%x_wood_outer_stairs", "x": 180, "uvlock": true },
        echo	    	"facing=west,half=top,shape=outer_left":  { "model": "%modid%:%%x_wood_outer_stairs", "x": 180, "y": 180, "uvlock": true },
        echo	    	"facing=south,half=top,shape=outer_left": { "model": "%modid%:%%x_wood_outer_stairs", "x": 180, "y": 90, "uvlock": true },
        echo	    	"facing=north,half=top,shape=outer_left": { "model": "%modid%:%%x_wood_outer_stairs", "x": 180, "y": 270, "uvlock": true },
        echo	    	"facing=east,half=top,shape=inner_right":  { "model": "%modid%:%%x_wood_inner_stairs", "x": 180, "y": 90, "uvlock": true },
        echo	    	"facing=west,half=top,shape=inner_right":  { "model": "%modid%:%%x_wood_inner_stairs", "x": 180, "y": 270, "uvlock": true },
        echo	    	"facing=south,half=top,shape=inner_right": { "model": "%modid%:%%x_wood_inner_stairs", "x": 180, "y": 180, "uvlock": true },
        echo	    	"facing=north,half=top,shape=inner_right": { "model": "%modid%:%%x_wood_inner_stairs", "x": 180, "uvlock": true },
        echo	    	"facing=east,half=top,shape=inner_left":  { "model": "%modid%:%%x_wood_inner_stairs", "x": 180, "uvlock": true },
        echo	    	"facing=west,half=top,shape=inner_left":  { "model": "%modid%:%%x_wood_inner_stairs", "x": 180, "y": 180, "uvlock": true },
        echo	    	"facing=south,half=top,shape=inner_left": { "model": "%modid%:%%x_wood_inner_stairs", "x": 180, "y": 90, "uvlock": true },
        echo	    	"facing=north,half=top,shape=inner_left": { "model": "%modid%:%%x_wood_inner_stairs", "x": 180, "y": 270, "uvlock": true }
        echo	    }
        echo }
	) > ../../blockstates/%%x_wood_stairs.json
)