package btpos.dj2addons.tweaks.mixin.thaumcraft;

import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import thaumcraft.common.tiles.TileThaumcraftInventory;

@Mixin(TileThaumcraftInventory.class)
public interface TileThaumcraftInventoryAccessor {
	@Invoker(remap=false)
	void callSyncSlots(EntityPlayerMP playerMP);
	
	@Invoker(remap=false)
	boolean callIsSyncedSlot(int i);
}
