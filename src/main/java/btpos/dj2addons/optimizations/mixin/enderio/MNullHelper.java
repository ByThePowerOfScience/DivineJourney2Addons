package btpos.dj2addons.optimizations.mixin.enderio;

import com.enderio.core.common.util.NullHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NullHelper.class)
public abstract class MNullHelper {
	/**
	 * Stops vast numbers of initializations/array copies of StringBuilder for large amounts of NPEs.
	 */
	@Inject(
			remap = false,
			method = "join",
			at = @At("HEAD"),
			cancellable = true
	)
	private static void dj2addons$optimizeJoinForSingleStrings(Object[] data, CallbackInfoReturnable<String> cir) {
		if (data.length == 1) {
			cir.setReturnValue(data[0].toString());
		}
	}
}

