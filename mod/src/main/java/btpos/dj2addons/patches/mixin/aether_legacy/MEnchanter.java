package btpos.dj2addons.patches.mixin.aether_legacy;

import com.gildedgames.the_aether.api.enchantments.AetherEnchantment;
import com.gildedgames.the_aether.tile_entities.TileEntityEnchanter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TileEntityEnchanter.class)
public abstract class MEnchanter {
	@Shadow private AetherEnchantment currentEnchantment;
	
	/**
	 * Makes the enchanter consume buckets in crafting like it should.
	 */
	@Redirect(
			method="update()V",
			at=@At(
					target="Lnet/minecraft/item/Item;hasContainerItem(Lnet/minecraft/item/ItemStack;)Z",
					value = "INVOKE")) //TODO expand check to only consume buckets if the output is in a bucket, otherwise leave bucket behind
	private boolean noCheckContainerItem(Item instance, ItemStack stack) {
//		ItemStack output = this.currentEnchantment.getOutput();
//		return !((output.getItem().hasContainerItem(output) && output.getItem().getContainerItem(stack).isItemEqualIgnoreDurability(new ItemStack(Items.BUCKET)))
//		         || output.getItem().getRegistryName().toString().contains("bucket"));
		return false;
	}
}
