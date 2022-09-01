package org.btpos.dj2addons.mixin.def.patches.modularmagic;

import fr.frinn.modularmagic.common.crafting.requirement.RequirementLifeEssence;
import fr.frinn.modularmagic.common.tile.TileLifeEssenceProvider;
import hellfirepvp.modularmachinery.common.crafting.helper.RecipeCraftingContext;
import hellfirepvp.modularmachinery.common.machine.MachineComponent;
import hellfirepvp.modularmachinery.common.util.ResultChance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("rawtypes")
@Mixin(RequirementLifeEssence.class)
public abstract class MRequirementLifeEssence {
	/**
	 * Fixes crash when removing a blood orb before the craft finishes.
	 * <p>Fixes <a href="https://github.com/Divine-Journey-2/Divine-Journey-2/issues/668">Divine-Journey-2#668</a></p>
	 */
	@Inject(
			remap = false,
			method = "finishCrafting",
			at = @At(
					target = "Lfr/frinn/modularmagic/common/tile/TileLifeEssenceProvider;getSoulNetwork()LWayofTime/bloodmagic/core/data/SoulNetwork;",
					value = "INVOKE",
					shift = At.Shift.BEFORE
			),
			cancellable = true
	)
	private void checkNull(MachineComponent component, RecipeCraftingContext context, ResultChance chance, CallbackInfoReturnable<Boolean> cir) {
		if (component.getContainerProvider() == null || ((TileLifeEssenceProvider) component.getContainerProvider()).getSoulNetwork() == null)
			cir.setReturnValue(true);
	}
}

