@echo off
set modid=adven_one

for %%x in (%*) do (
	echo Making %%x.json block
	(
		echo {
		echo 	"parent": "block/cube_all",
		echo 	"textures": {
		echo 		"all": "%modid%:blocks/%%x"
		echo 	}
		echo }
	) > %%x.json

	echo Making %%x.json item
	(
		echo {
		echo 	"parent": "%modid%:block/%%x"
		echo }
	) > ../item/%%x.json

	echo Making %%x.json blockstate
	(
        echo {
        echo 	"variants": {
        echo 		"normal": { "model": "%modid%:%%x" }
        echo 	}
        echo }
	) > ../../blockstates/%%x.json
)