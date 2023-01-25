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
	echo Making %%x_stone_inner_stairs.json block
	(
		echo {
		echo 	"parent": "block/inner_stairs",
		echo 	"textures": {
		echo 		"bottom": "%modid%:blocks/%%x_stone",
        echo        "top": "%modid%:blocks/%%x_stone",
        echo        "side": "%modid%:blocks/%%x_stone"
		echo 	}
		echo }
	) > %%x_stone_inner_stairs.json

	echo Making %%x_stone_outer_stairs.json block
    	(
    		echo {
    		echo 	"parent": "block/outer_stairs",
    		echo 	"textures": {
    		echo 		"bottom": "%modid%:blocks/%%x_stone",
            echo        "top": "%modid%:blocks/%%x_stone",
            echo        "side": "%modid%:blocks/%%x_stone"
    		echo 	}
    		echo }
    	) > %%x_stone_outer_stairs.json

	echo Making %%x_stone_stairs.json block
    	(
    		echo {
            echo   "parent": "block/stairs",
            echo    "textures": {
            echo     "bottom": "%modid%:blocks/%%x_stone",
            echo     "top": "%modid%:blocks/%%x_stone",
            echo     "side": "%modid%:blocks/%%x_stone"
            echo   }
            echo }
    	) > %%x_stone_stairs.json

	echo Making %%x_stone_stairs.json item
	(
		echo {
		echo 	"parent": "%modid%:block/%%x_stone_stairs"
		echo }
	) > ../item/%%x_stone_stairs.json

	echo Making %%x_stone_slab_double.json item
    	(
    		echo {
    		echo 	"parent": "%modid%:block/%%x_stone"
    		echo }
    	) > ../item/%%x_stone_slab_double.json

    echo Making %%x_stone_wall_post.json item
    	(
    		echo {
    		echo 	"parent": "%modid%:block/%%x_stone"
    		echo }
    	) > ../item/%%x_stone_wall_post.json

    echo Making %%x_stone_slab_upper.json block
        	(
        		echo {
        		echo 	"parent": "block/upper_slab",
        		echo 	"textures": {
        		echo 		"bottom": "%modid%:blocks/%%x_stone",
                echo        "top": "%modid%:blocks/%%x_stone",
                echo        "side": "%modid%:blocks/%%x_stone"
        		echo 	}
        		echo }
        	) > %%x_stone_slab_upper.json

    echo Making %%x_stone_fence.json item
    	(
    		echo {
    		echo 	"parent": "%modid%:block/%%x_stone_fence_inventory"
    		echo }
    	) > ../item/%%x_stone_fence.json

    echo Making %%x_stone_slab.json item
    	(
    		echo {
    		echo 	"parent": "%modid%:block/%%x_stone_slab_half"
    		echo }
    	) > ../item/%%x_stone_slab.json

    echo Making %%x_stone_slab_half.json block
        	(
        		echo {
        		echo 	"parent": "block/half_slab",
        		echo 	"textures": {
        		echo 		"bottom": "%modid%:blocks/%%x_stone",
                echo        "top": "%modid%:blocks/%%x_stone",
                echo        "side": "%modid%:blocks/%%x_stone"
        		echo 	}
        		echo }
        	) > %%x_stone_slab_half.json

	echo Making %%x_stone_wall.json item
    	(
    		echo {
    		echo 	"parent": "%modid%:block/%%x_stone_wall_inventory"
    		echo }
    	) > ../item/%%x_stone_wall.json

	echo Making %%x_stone_wall_side.json item
    	(
    		echo {
            echo 	"parent": "minecraft:block/wall_side",
            echo    "textures": {
            echo        "wall": "%modid%:blocks/%%x_stone"
            echo    }
            echo }
    	) > %%x_stone_wall_side.json

    echo Making %%x_stone_wall_post.json item
        	(
        		echo {
                echo 	"parent": "minecraft:block/wall_post",
                echo    "textures": {
                echo        "wall": "%modid%:blocks/%%x_stone"
                echo    }
                echo }
        	) > %%x_stone_wall_post.json

    echo Making %%x_stone_wall_inventory.json item
            	(
            		echo {
                    echo 	"parent": "minecraft:block/wall_inventory",
                    echo    "textures": {
                    echo        "wall": "%modid%:blocks/%%x_stone"
                    echo    }
                    echo }
            	) > %%x_stone_wall_inventory.json

	echo Making %%x_stone_stairs.json blockstate
	(
		echo {
        echo	"variants": {
        echo		    "facing=east,half=bottom,shape=straight":  { "model": "%modid%:%%x_stone_stairs" },
        echo		    "facing=west,half=bottom,shape=straight":  { "model": "%modid%:%%x_stone_stairs", "y": 180, "uvlock": true },
        echo		    "facing=south,half=bottom,shape=straight": { "model": "%modid%:%%x_stone_stairs", "y": 90, "uvlock": true },
        echo		    "facing=north,half=bottom,shape=straight": { "model": "%modid%:%%x_stone_stairs", "y": 270, "uvlock": true },
        echo		    "facing=east,half=bottom,shape=outer_right":  { "model": "%modid%:%%x_stone_outer_stairs" },
        echo		    "facing=west,half=bottom,shape=outer_right":  { "model": "%modid%:%%x_stone_outer_stairs", "y": 180, "uvlock": true },
        echo		    "facing=south,half=bottom,shape=outer_right": { "model": "%modid%:%%x_stone_outer_stairs", "y": 90, "uvlock": true },
        echo	    	"facing=north,half=bottom,shape=outer_right": { "model": "%modid%:%%x_stone_outer_stairs", "y": 270, "uvlock": true },
        echo		    "facing=east,half=bottom,shape=outer_left":  { "model": "%modid%:%%x_stone_outer_stairs", "y": 270, "uvlock": true },
        echo	    	"facing=west,half=bottom,shape=outer_left":  { "model": "%modid%:%%x_stone_outer_stairs", "y": 90, "uvlock": true },
        echo		    "facing=south,half=bottom,shape=outer_left": { "model": "%modid%:%%x_stone_outer_stairs" },
        echo	    	"facing=north,half=bottom,shape=outer_left": { "model": "%modid%:%%x_stone_outer_stairs", "y": 180, "uvlock": true },
        echo		    "facing=east,half=bottom,shape=inner_right":  { "model": "%modid%:%%x_stone_inner_stairs" },
        echo	    	"facing=west,half=bottom,shape=inner_right":  { "model": "%modid%:%%x_stone_inner_stairs", "y": 180, "uvlock": true },
        echo	    	"facing=south,half=bottom,shape=inner_right": { "model": "%modid%:%%x_stone_inner_stairs", "y": 90, "uvlock": true },
        echo	    	"facing=north,half=bottom,shape=inner_right": { "model": "%modid%:%%x_stone_inner_stairs", "y": 270, "uvlock": true },
        echo	    	"facing=east,half=bottom,shape=inner_left":  { "model": "%modid%:%%x_stone_inner_stairs", "y": 270, "uvlock": true },
        echo	    	"facing=west,half=bottom,shape=inner_left":  { "model": "%modid%:%%x_stone_inner_stairs", "y": 90, "uvlock": true },
        echo	    	"facing=south,half=bottom,shape=inner_left": { "model": "%modid%:%%x_stone_inner_stairs" },
        echo	    	"facing=north,half=bottom,shape=inner_left": { "model": "%modid%:%%x_stone_inner_stairs", "y": 180, "uvlock": true },
        echo	    	"facing=east,half=top,shape=straight":  { "model": "%modid%:%%x_stone_stairs", "x": 180, "uvlock": true },
        echo		    "facing=west,half=top,shape=straight":  { "model": "%modid%:%%x_stone_stairs", "x": 180, "y": 180, "uvlock": true },
        echo		    "facing=south,half=top,shape=straight": { "model": "%modid%:%%x_stone_stairs", "x": 180, "y": 90, "uvlock": true },
        echo	    	"facing=north,half=top,shape=straight": { "model": "%modid%:%%x_stone_stairs", "x": 180, "y": 270, "uvlock": true },
        echo	    	"facing=east,half=top,shape=outer_right":  { "model": "%modid%:%%x_stone_outer_stairs", "x": 180, "y": 90, "uvlock": true },
        echo	    	"facing=west,half=top,shape=outer_right":  { "model": "%modid%:%%x_stone_outer_stairs", "x": 180, "y": 270, "uvlock": true },
        echo	    	"facing=south,half=top,shape=outer_right": { "model": "%modid%:%%x_stone_outer_stairs", "x": 180, "y": 180, "uvlock": true },
        echo	    	"facing=north,half=top,shape=outer_right": { "model": "%modid%:%%x_stone_outer_stairs", "x": 180, "uvlock": true },
        echo	    	"facing=east,half=top,shape=outer_left":  { "model": "%modid%:%%x_stone_outer_stairs", "x": 180, "uvlock": true },
        echo	    	"facing=west,half=top,shape=outer_left":  { "model": "%modid%:%%x_stone_outer_stairs", "x": 180, "y": 180, "uvlock": true },
        echo	    	"facing=south,half=top,shape=outer_left": { "model": "%modid%:%%x_stone_outer_stairs", "x": 180, "y": 90, "uvlock": true },
        echo	    	"facing=north,half=top,shape=outer_left": { "model": "%modid%:%%x_stone_outer_stairs", "x": 180, "y": 270, "uvlock": true },
        echo	    	"facing=east,half=top,shape=inner_right":  { "model": "%modid%:%%x_stone_inner_stairs", "x": 180, "y": 90, "uvlock": true },
        echo	    	"facing=west,half=top,shape=inner_right":  { "model": "%modid%:%%x_stone_inner_stairs", "x": 180, "y": 270, "uvlock": true },
        echo	    	"facing=south,half=top,shape=inner_right": { "model": "%modid%:%%x_stone_inner_stairs", "x": 180, "y": 180, "uvlock": true },
        echo	    	"facing=north,half=top,shape=inner_right": { "model": "%modid%:%%x_stone_inner_stairs", "x": 180, "uvlock": true },
        echo	    	"facing=east,half=top,shape=inner_left":  { "model": "%modid%:%%x_stone_inner_stairs", "x": 180, "uvlock": true },
        echo	    	"facing=west,half=top,shape=inner_left":  { "model": "%modid%:%%x_stone_inner_stairs", "x": 180, "y": 180, "uvlock": true },
        echo	    	"facing=south,half=top,shape=inner_left": { "model": "%modid%:%%x_stone_inner_stairs", "x": 180, "y": 90, "uvlock": true },
        echo	    	"facing=north,half=top,shape=inner_left": { "model": "%modid%:%%x_stone_inner_stairs", "x": 180, "y": 270, "uvlock": true }
        echo	    }
        echo }
	) > ../../blockstates/%%x_stone_stairs.json

	echo Making %%x_stone_wall.json blockstate
    	(
    		echo {
            echo "multipart": [
            echo 	{   "when": { "up": "true" },
            echo   	  "apply": { "model": "%modid%:%%x_stone_wall_post" }
            echo 	},
            echo 	{   "when": { "north": "true" },
            echo 	  "apply": { "model": "%modid%:%%x_stone_wall_side", "uvlock": true }
            echo 	},
            echo 	{   "when": { "east": "true" },
            echo 	  "apply": { "model": "%modid%:%%x_stone_wall_side", "y": 90, "uvlock": true }
            echo 	},
            echo 	{   "when": { "south": "true" },
            echo 	  "apply": { "model": "%modid%:%%x_stone_wall_side", "y": 180, "uvlock": true }
            echo 	},
            echo 	{   "when": { "west": "true" },
            echo 	  "apply": { "model": "%modid%:%%x_stone_wall_side", "y": 270, "uvlock": true }
            echo 	}
            echo  	]
            echo }

    	) > ../../blockstates/%%x_stone_wall.json
)