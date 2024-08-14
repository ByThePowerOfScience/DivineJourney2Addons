//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package btpos.dj2addons.patches.mixin.aether_legacy;

import com.gildedgames.the_aether.AetherEventHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({AetherEventHandler.class})
public class MAetherEventHandler {
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
	
	/**
	 * Removes lava behavior due to this being handled by {@link net.minecraft.world.World#setBlockState World.setBlockState} through {@link btpos.dj2addons.initmixins.patches.minecraft.aether_legacy.aerogel.MWorld MWorld}.
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

