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
	echo Making %%x_log.json block
	(
		echo {
		echo 	"parent": "block/cube_column",
		echo 	"textures": {
		echo 		"end": "%modid%:blocks/%%x_log_top",
		echo        "side": "%modid%:blocks/%%x_log"
		echo 	}
		echo }
	) > %%x_log.json

	echo Making %%x_log.json item
	(
		echo {
		echo 	"parent": "%modid%:block/%%x_log"
		echo }
	) > ../item/%%x_log.json

	echo Making %%x_log.json blockstate
	(
        echo {
        echo   "variants": {
        echo       "axis=y":  { "model": "%modid%:%%x_log" },
        echo       "axis=z":   { "model": "%modid%:%%x_log", "x": 90 },
        echo       "axis=x":   { "model": "%modid%:%%x_log", "x": 90, "y": 90 },
        echo       "axis=none": { "model": "%modid%:%%x_log" }
        echo   }
        echo }
	) > ../../blockstates/%%x_log.json
)