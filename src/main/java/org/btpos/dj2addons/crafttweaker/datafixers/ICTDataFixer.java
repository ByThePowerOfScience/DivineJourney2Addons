package org.btpos.dj2addons.crafttweaker.datafixers;

import net.minecraft.util.datafix.IFixableData;
import net.minecraftforge.common.util.ModFixs;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass
public interface ICTDataFixer {
	IFixableData getInternal();
	
	void registerSelf(ModFixs modFixs);
}
