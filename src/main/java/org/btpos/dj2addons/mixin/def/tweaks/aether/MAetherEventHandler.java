//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.btpos.dj2addons.mixin.def.tweaks.aether;

import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.AetherEventHandler;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({AetherEventHandler.class})
public class MAetherEventHandler {
	
	/**
	 * Optimization by checking the dimension of the FillBucketEvent FIRST instead of later.
	 */
	@Inject(remap=false, method="onFillBucket", at=@At("HEAD"), cancellable = true)
	private void checkDimensionFirst(FillBucketEvent event, CallbackInfo ci) {
		if (event.getEntityPlayer().dimension != 0 && event.getEntityPlayer().dimension != AetherConfig.dimension.aether_dimension_id)
			ci.cancel();
	}
	
	/*@Redirect(
			remap = false,
			method = "onFillBucket",
			at = @At(
					target = "Lnet/minecraftforge/event/entity/player/FillBucketEvent;setResult(Lnet/minecraftforge/fml/common/eventhandler/Event$Result;)V",
					value = "INVOKE",
					ordinal = 1
			)
	)
	private void removeEventResultLine(FillBucketEvent instance, Result result) {}
	
	@Inject(
			remap = false,
			method = "onFillBucket",
			at = @At(
					target = "Lnet/minecraft/entity/player/EntityPlayer;playSound(Lnet/minecraft/util/SoundEvent;FF)V",
					value = "INVOKE",
					ordinal = 1
			)
	)
	private void addEventResult(FillBucketEvent event, CallbackInfo ci) {
		event.setResult(Result.ALLOW);
	}
	*/
	
	@ModifyVariable(
			remap = false,
			method = "onFillBucket",
			name = "isLava",
			at = @At("STORE")
	)
	private boolean removeLavaBehavior(boolean b) {
		return false;
	}
}

