package org.btpos.dj2addons.mixin.def.patches.aether;

import com.gildedgames.the_aether.tile_entities.TileEntityEnchanter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TileEntityEnchanter.class)
public abstract class MEnchanter {
	@Redirect(
			method="update()V",
			at=@At(
					target="Lnet/minecraft/item/Item;hasContainerItem(Lnet/minecraft/item/ItemStack;)Z",
					value = "INVOKE"))
	private boolean noCheckContainerItem(Item instance, ItemStack stack) {
		return false;
	}
}
