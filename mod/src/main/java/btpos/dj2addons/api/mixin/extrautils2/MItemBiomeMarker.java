package btpos.dj2addons.api.mixin.extrautils2;

import com.rwtema.extrautils2.items.ItemBiomeMarker;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import btpos.dj2addons.api.extrautils2.ExtraUtilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemBiomeMarker.class)
public abstract class MItemBiomeMarker {
	
	/**
	 * Prevents setting the NBT of a biome marker to any biome that has been excluded.
	 */
	@Inject(
			remap = false,
			method = "setBiome",
			at = @At(
					target = "Lcom/rwtema/extrautils2/utils/helpers/NBTHelper;getOrInitTagCompound(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/nbt/NBTTagCompound;",
					value = "INVOKE",
					shift = Shift.BEFORE
			),
			locals = LocalCapture.CAPTURE_FAILSOFT,
			cancellable = true
	)
	private static void checkAllowedBiomes(ItemStack itemStackIn, Biome biome, CallbackInfoReturnable<ItemStack> cir, ResourceLocation rl) {
		if (ExtraUtilities.Internal.getExcludedBiomes().contains(rl))
			cir.setReturnValue(itemStackIn);
	}
}

