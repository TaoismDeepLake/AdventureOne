package com.deeplake.adven_one.util;

import com.deeplake.adven_one.IdlFramework;
import com.deeplake.adven_one.util.NBTStrDef.IDLNBTDef;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface IHasModel {
	default void registerModels() {
		if (this instanceof Block) {
			IdlFramework.proxy.registerItemRenderer(Item.getItemFromBlock((Block) this), 0, IDLNBTDef.NAME_INVENTORY);
		} else if (this instanceof Item) {
			IdlFramework.proxy.registerItemRenderer((Item) this, 0, IDLNBTDef.NAME_INVENTORY);
		} else {
			IdlFramework.LogWarning("A IHasModel is not handled:" + this.toString());
		}
	}
}
