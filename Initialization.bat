@echo off
cd .\src\main\resources\assets\adven_one\models\block
for %%x in (%*) do (
    .\_make_base.bat %%x_planks %%x_dirt %%x_stone
    .\_make_stairs_wood.bat %%x
    .\_make_stairs_stone.bat %%x
    .\_make_fence_wood.bat %%x
    .\_make_fence_stone.bat %%x
    .\_make_log.bat %%x
)