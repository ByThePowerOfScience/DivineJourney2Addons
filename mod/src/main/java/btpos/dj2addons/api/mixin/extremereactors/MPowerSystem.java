package btpos.dj2addons.api.mixin.extremereactors;

import erogenousbeef.bigreactors.common.multiblock.PowerSystem;
import btpos.dj2addons.api.extremereactors.ExtremeReactors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Objects;

@Mixin(PowerSystem.class)
public class MPowerSystem {
	
	/**
	 * Fixes typo in "Tesla".
	 */
	@ModifyVariable(method="<init>", ordinal = 0, at=@At("HEAD"), argsOnly = true)
	private static String fixTypo(String fullName) {
		if (fullName.equals("Testa"))
			return "Tesla";
		else
			return fullName;
	}
	
	/**
	 * Allows CraftTweaker to change the maximum energy stored in the output buffer.
	 */
	@ModifyVariable(method="<init>", at=@At("HEAD"), argsOnly = true)
	private static long changeMaxEnergyStored(long value) {
		return Objects.nonNull(ExtremeReactors.Internal.maxEnergyStored) ? ExtremeReactors.Internal.maxEnergyStored : value;
	}

}
