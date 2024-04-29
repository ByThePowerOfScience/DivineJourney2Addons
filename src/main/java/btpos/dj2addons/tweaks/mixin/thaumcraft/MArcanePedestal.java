package btpos.dj2addons.tweaks.mixin.thaumcraft;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import thaumcraft.common.tiles.TileThaumcraftInventory;
import thaumcraft.common.tiles.crafting.TilePedestal;

@Mixin(TilePedestal.class)
public abstract class MArcanePedestal extends TileThaumcraftInventory {
	private MArcanePedestal(int size) {
		super(size);
	}
	
	/**
	 * @author Azanor
	 * @reason Allows infusion recipes to output multiple items.
	 */
	@Overwrite(remap=false)
	public void setInventorySlotContentsFromInfusion(int i, ItemStack itemStack) {
		this.getItems().set(i, itemStack);
		
		this.markDirty();
		if (((TileThaumcraftInventoryAccessor) this).callIsSyncedSlot(i)) {
			((TileThaumcraftInventoryAccessor)this).callSyncSlots(null);
		}
	}
}
