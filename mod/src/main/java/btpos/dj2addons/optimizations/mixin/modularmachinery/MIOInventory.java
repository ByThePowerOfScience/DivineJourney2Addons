package btpos.dj2addons.optimizations.mixin.modularmachinery;

import com.llamalad7.mixinextras.sugar.Local;
import hellfirepvp.modularmachinery.common.util.IOInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;

@Mixin(value = IOInventory.class)
public abstract class MIOInventory {
	/**
	 * Make initial capacity of hashmaps the correct size from the start.
	 * <p>Saves ~1.79% server time from resize calls:
	 * <p>Callstack: TileMachineController.searchAndUpdateRecipe() -> RequirementItem.canStartCrafting() -> CopyHandlerHelper.copyInventory() -> IOInventory.deserialize() -> IOInventory.readNBT()</p>
	 */
	@Redirect(
			method = "<init>(Lhellfirepvp/modularmachinery/common/tiles/base/TileEntitySynchronized;[I[I[Lnet/minecraft/util/EnumFacing;)V",
			at = @At(
					target = "java/util/HashMap",
					value = "NEW"
			)
	)
	private <T,U> HashMap<T,U> dj2addons$expandHashMapCapacity(@Local(argsOnly = true, ordinal = 0) int[] inSlots, @Local(argsOnly = true, ordinal = 0) int[] outSlots) {
		return new HashMap<>(inSlots.length + outSlots.length);
	}
}

