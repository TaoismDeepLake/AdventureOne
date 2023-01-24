@echo off
cd .\src\main\resources\assets\adven_one\models\block
for %%x in (%*) do (
    .\_make_base.bat %%x_planks %%x_dirt %%x_stone
    .\_make_base.bat %%x_1_ore %%x_1_block %%x_2_ore %%x_2_block %%x_3_ore %%x_3_block %%x_4_ore %%x_4_block
    .\_make_stairs_wood.bat %%x
    .\_make_stairs_stone.bat %%x
    .\_make_fence_wood.bat %%x
    .\_make_fence_stone.bat %%x
    .\_make_log.bat %%x
    .\_make_grass.bat %%x
    cd ..\item
    ..\item\_make.bat %%x_1_gem %%x_2_gem %%x_3_gem %%x_4_gem
    ..\item\_make_armor1.bat %%x_1 %%x_2 %%x_3 %%x_4
    ..\item\_make_armor2.bat %%x_1 %%x_2 %%x_3 %%x_4
    ..\item\_make_armor3.bat %%x_1 %%x_2 %%x_3 %%x_4
    ..\item\_make_armor4.bat %%x_1 %%x_2 %%x_3 %%x_4
    ..\item\_make_tool.bat %%x
)