package btpos.dj2addons.patches.mixin.bewitchment;

import com.bewitchment.common.handler.PoppetHandler;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PoppetHandler.class)
public abstract class MPoppetHandler {
	/** Stops crash when crafting a destructible item in an autocrafter. */
	@Inject(
			remap = false,
			method = "toolProtection",
			at = @At("HEAD"),
			cancellable = true
	)
	private void checkNullPlayer(PlayerDestroyItemEvent event, CallbackInfo ci) {
		if (event.getEntityPlayer() == null)
			ci.cancel();
	}
}

