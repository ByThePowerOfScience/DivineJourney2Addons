package btpos.dj2addons.patches.mixin.mekanism;

import mekanism.client.render.FluidRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FluidRenderer.class)
public abstract class MFluidRenderer {
	@ModifyVariable(
			remap = false,
			method = "getTankDisplay(Lmekanism/client/render/FluidRenderer$RenderData;D)Lmekanism/client/render/MekanismRenderer$DisplayInteger;",
			name="stage",
			at=@At("STORE")
	)
	private static int dj2addons$catchOutOfBoundsStage(int stage) {
		return Math.max(stage, 0);
	}
}

