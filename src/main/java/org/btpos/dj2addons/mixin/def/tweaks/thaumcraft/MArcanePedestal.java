package org.btpos.dj2addons.mixin.def.tweaks.thaumcraft;

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
	 * Allows for multiple outputs from an infusion craft without allowing multiple contents to be inserted.
	 */
	@Redirect(
			method= "setInventorySlotContentsFromInfusion(ILnet/minecraft/item/ItemStack;)V",
			at=@At(
					target="Lthaumcraft/common/tiles/crafting/TilePedestal;setInventorySlotContents(ILnet/minecraft/item/ItemStack;)V",
					value="INVOKE"
			)
	)
	public void setInventorySlotContentsFromInfusion(TilePedestal instance, int i, ItemStack itemStack) {
		this.getItems().set(i, itemStack);
		
		this.markDirty();
		if (((TileThaumcraftInventoryAccessor) this).callIsSyncedSlot(i)) {
			((TileThaumcraftInventoryAccessor)this).callSyncSlots(null);
		}
	}
}
