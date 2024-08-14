package btpos.dj2addons.optimizations.mixin.aether_legacy;

import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.AetherEventHandler;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AetherEventHandler.class)
public abstract class MAetherEventHandler {
	/**
	 * Optimization by checking the dimension of the FillBucketEvent FIRST instead of later.
	 */
	@Inject(remap=false, method="onFillBucket", at=@At("HEAD"), cancellable = true)
	private void checkDimensionFirst(FillBucketEvent event, CallbackInfo ci) {
		if (event.getEntityPlayer().dimension != 0 && event.getEntityPlayer().dimension != AetherConfig.dimension.aether_dimension_id)
			ci.cancel();
	}
}

