local outFile = nil;
local modName = "adven_one";
local blockName = "grid_normal";

local function GenModelBlockItem()
	local path = string.format("src\\main\\resources\\assets\\%s\\models\\item\\%s.json", modName, blockName);
	outFile = io.open(path,"w");
	outFile:write('{\n');
	local content = string.format("\t\"parent\": \"%s:block/%s\"\n", modName,blockName );
	outFile:write(content);
	outFile:write('}\n');
	outFile:close();
end

local function GenModelBlock()
	local path = string.format("src\\main\\resources\\assets\\%s\\models\\block\\%s.json", modName, blockName);
	outFile = io.open(path,"w");
	outFile:write('{\n');
	outFile:write('\t\"parent\": \"block/cube_all\",\n');
	outFile:write('\t\"textures\": {\n');
	local content = string.format("\t\t\"all\": \"%s:blocks/%s\"\n", modName,blockName );
	outFile:write(content);
	outFile:write('\t}\n');
	outFile:write('}\n');
	outFile:close();
end

local function GenModelBlockColumn()
	local path = string.format("src\\main\\resources\\assets\\%s\\models\\block\\%s.json", modName, blockName);
	outFile = io.open(path,"w");

	local content = string.format('{\n  "parent": "block/cube_column",\n  "textures": {\n      "end": "%s:blocks/%s_top",\n      "side": "%s:blocks/%s"\n  }\n}\n' , modName,blockName, modName,blockName);
	outFile:write(content);

	outFile:close();
end

local function GenBlockState()
	local path = string.format("src\\main\\resources\\assets\\%s\\blockstates\\%s.json", modName, blockName);
	outFile = io.open(path,"w");
	outFile:write('{\n');
	outFile:write('\t\"variants\": {\n');
	local content = string.format("\t\t\"normal\": { \"model\": \"%s:%s\" }\n", modName,blockName );
	outFile:write(content);
	outFile:write('\t}\n');
	outFile:write('}\n');
	outFile:close();
end

local function GenBlockStateCol()
	local path = string.format("src\\main\\resources\\assets\\%s\\blockstates\\%s.json", modName, blockName);
	outFile = io.open(path,"w");
-- 	outFile:write('{\n');
-- 	outFile:write('\t\"variants\": {\n');
	local content = string.format('{\n  "variants": {\n      "axis=y":  { "model": "%s:%s" },\n      "axis=z":   { "model": "%s:%s", "x": 90 },\n      "axis=x":   { "model": "%s:%s", "x": 90, "y": 90 },\n      "axis=none": { "model": "%s:%s" }\n  }\n}\n', modName,blockName, modName,blockName, modName,blockName, modName,blockName );
	outFile:write(content);
-- 	outFile:write('\t}\n');
-- 	outFile:write('}\n');
	outFile:close();
end

local function GenBlock(_blockName)
	blockName = _blockName;
	print("Creating:"..blockName)
	GenModelBlockItem();
	GenModelBlock();
	GenBlockState();
end

local function GenBlockCol(_blockName)
	blockName = _blockName;
	print("Creating:"..blockName)
	GenModelBlockItem();
	GenModelBlockColumn();
	GenBlockStateCol();
end

local function GenItem(_typeName, _itemName)
	print("Creating:".._typeName.." ".._itemName)
	local path = string.format("src\\main\\resources\\assets\\%s\\models\\item\\%s.json", modName, _itemName);
	outFile = io.open(path,"w");

	local content = string.format('{"parent": "item/handheld","textures": {"layer0":"%s:items/%s/%s"}}\n', modName, _typeName, _itemName );
	outFile:write(content);

	outFile:close();
end

local function GenItemTier(_typeName, _itemName, tier)
	print("Creating:".._typeName.." ".._itemName)
	local path = string.format("src\\main\\resources\\assets\\%s\\models\\item\\%s.json", modName, _itemName);
	outFile = io.open(path,"w");

	local content = string.format('{"parent": "item/handheld","textures": {"layer0":"%s:items/%s/%s",\n"layer1":"%s:items/overlay_%d"}}\n', modName, _typeName, _itemName, modName, tier );
	outFile:write(content);

	outFile:close();
end

local function GenSet(_setName)
    GenBlock(_setName.."_planks");
    GenBlock(_setName.."_dirt");
    GenBlock(_setName.."_stone");
    GenBlockCol(_setName.."_log");

    for i = 1, 4 do
        GenItemTier("misc", string.format("%s_%d_%s", _setName, i, "gem"), i);
        GenItemTier("misc", string.format("%s_%d_%s", _setName, i, "sword"), i);
        GenItemTier("misc", string.format("%s_%d_%s", _setName, i, "pickaxe"), i);
        for x = 0, 3 do
            GenItemTier("misc", string.format("%s_%d_%s%d", _setName, i, "armor", x), i);
        end

        GenBlock(string.format("%s_%d_%s", _setName, i, "ore"));
        GenBlock(string.format("%s_%d_%s", _setName, i, "block"));
    end
end

--  GenItem("misc", "idl_ai_terminal");
-- GenSet("suit_back")
-- GenSet("suit_test")
-- GenSet("suit_celestial")
-- GenSet("suit_luck_a")

GenBlock("forest_terrain");
GenBlock("mountain_terrain");
GenBlock("swamp_terrain");


--GenItem("misc", "water_extractor")
--GenItem("misc", "disturb_measure")
--GenItem("skill", "skill_radar_creature")
-- GenItem("skill", "clone_block")

-- for i = 1,4 do 
-- 	GenItem("armor", "l_m_armor_" .. i)
-- end
