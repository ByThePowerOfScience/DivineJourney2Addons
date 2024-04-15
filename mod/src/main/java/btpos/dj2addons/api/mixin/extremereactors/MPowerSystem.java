package btpos.dj2addons.api.mixin.extremereactors;

import btpos.dj2addons.api.extremereactors.ExtremeReactors;
import erogenousbeef.bigreactors.common.multiblock.PowerSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Objects;

@Mixin(value = PowerSystem.class, remap = false)
public class MPowerSystem {

//	if it ain't broke DONT FIX IT
//	/**
//	 * Fixes typo in "Tesla".
//	 */
//	@ModifyConstant(
//			method = "<clinit>",
//			constant = @Constant(stringValue = "Testa")
//	)
//	private static String fixTypo(String fullName) {
//		return "Tesla";
//	}

	/**
	 * Allows CraftTweaker to change the maximum energy stored in the output buffer.
	 */
	@ModifyVariable(
			method="<init>",
			at=@At("HEAD"),
			argsOnly = true
	)
	private static long changeMaxEnergyStored(long value) {
		return ExtremeReactors.Internal.getMaxEnergyStored() != -1L
		        ? ExtremeReactors.Internal.getMaxEnergyStored()
		        : value;
	}

}
