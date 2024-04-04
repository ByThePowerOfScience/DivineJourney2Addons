package btpos.dj2addons.optimizations.mixin.agricraft;

import com.infinityraider.agricraft.api.v1.seed.AgriSeed;
import com.infinityraider.agricraft.api.v1.seed.IAgriSeedProvider;
import com.infinityraider.agricraft.tiles.TileEntityCrop;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(TileEntityCrop.class)
public abstract class MTileEntityCrop {
	
	@Shadow private AgriSeed seed;
	
	/**
	 * Removes needless checking for if neighbors that will never be overtaken have valid soil.
	 * <p>Saves roughly 0.5% server time for large farms with Hydrators.</p>
	 */
	@WrapOperation(
			remap = false,
			method = "spread",
			at = @At(
					target = "Lcom/infinityraider/infinitylib/utility/WorldHelper;getTileNeighbors(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Ljava/lang/Class;)Ljava/util/List;",
					value = "INVOKE"
			)
	)
	private <T extends IAgriSeedProvider> List<T> dj2addons$ifNotAggressiveIgnoreInhabitedSpots(World world, BlockPos pos, Class<T> type, Operation<List<T>> originalOperation) {
		List<T> originalResult = originalOperation.call(world, pos, type);
		if (!this.seed.getPlant().isAggressive()) {
			originalResult.removeIf(IAgriSeedProvider::hasSeed);
		}
		return originalResult;
	}
}

