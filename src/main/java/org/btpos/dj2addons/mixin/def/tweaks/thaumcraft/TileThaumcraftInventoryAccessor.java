package org.btpos.dj2addons.mixin.def.tweaks.thaumcraft;

import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import thaumcraft.common.tiles.TileThaumcraftInventory;

@Mixin(TileThaumcraftInventory.class)
public interface TileThaumcraftInventoryAccessor {
	@Invoker
	void callSyncSlots(EntityPlayerMP playerMP);
	
	@Invoker
	boolean callIsSyncedSlot(int i);
}
