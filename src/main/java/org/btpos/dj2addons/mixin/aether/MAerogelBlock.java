package org.btpos.dj2addons.mixin.aether;

import com.gildedgames.the_aether.blocks.decorative.BlockAerogel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.btpos.dj2addons.DJ2Addons.LOGGER;

@Mixin(BlockAerogel.class)
public class MAerogelBlock {
	@Inject(method="<init>()V", at=@At("TAIL"))
	private void injectedMethod(CallbackInfo ci) {
		LOGGER.info("Mixin was merged");
	}
}
