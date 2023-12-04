package btpos.dj2addons.tweaks.mixin.thaumcraft;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import thaumcraft.common.tiles.TileThaumcraftInventory;
import thaumcraft.common.tiles.crafting.TilePedestal;

@Mixin(TilePedestal.class)
public abstract class MArcanePedestal extends TileThaumcraftInventory {
	private MArcanePedestal(int size) {
		super(size);
	}
	
	/**
	 * @author ByThePowerOfScience
	 * @reason Allows infusion recipes to output multiple items.
	 */
	@Redirect(
			remap=false,
			method="setInventorySlotContentsFromInfusion",
			at=@At(
					target="Lthaumcraft/common/tiles/crafting/TilePedestal;setInventorySlotContents(ILnet/minecraft/item/ItemStack;)V",
					value="INVOKE"
			)
	)
	public void dj2addons$bypassStackSize(TilePedestal instance, int i, ItemStack itemStack) {
		this.getItems().set(i, itemStack);
		
		this.markDirty();
		if (((TileThaumcraftInventoryAccessor) this).callIsSyncedSlot(i)) {
			((TileThaumcraftInventoryAccessor)this).callSyncSlots(null);
		}
	}
}
