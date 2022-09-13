---
name: 需求模板
about: 内部人员提需求
title: "【需求】【代码】添加从锭到块的配方"
labels: ''
assignees: ''

---

指定人物：mo

要求：
-添加每个tier，用4个宝石（锭）合成一个对应的块的配方。
-不允许使用json配方。
-暂不添加逆向的（从块到锭）的配方。

编程指导：
-在com.deeplake.adven_one.recipe.SuitRecipesInit里注册你的配方。在这里你可以找到不少它的姊妹需求的实现，参考一下如何用java写配方。
-你可以给你的配方写一个新的类，也最好这样做。新的类放在com.deeplake.adven_one.recipe.traditional里。
