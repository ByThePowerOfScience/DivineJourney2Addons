//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.btpos.dj2addons.mixin.aether;

import com.gildedgames.the_aether.AetherEventHandler;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({AetherEventHandler.class})
public class MPatchAerogel {
	
	@Redirect(
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
					value = "INVOKE"
			)
	)
	private void addEventResult(FillBucketEvent event, CallbackInfo ci) {
		event.setResult(Result.ALLOW);
	}
}
