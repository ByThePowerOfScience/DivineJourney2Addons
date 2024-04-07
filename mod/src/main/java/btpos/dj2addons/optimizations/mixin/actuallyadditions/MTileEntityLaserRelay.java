package btpos.dj2addons.optimizations.mixin.actuallyadditions;

import de.ellpeck.actuallyadditions.api.laser.ILaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelay;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TileEntityLaserRelay.class)
public abstract class MTileEntityLaserRelay {
	@Redirect(
            remap=false,
			method = "readSyncableNBT",
			at = @At(
					target = "Lde/ellpeck/actuallyadditions/api/laser/ILaserRelayConnectionHandler;removeRelayFromNetwork(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;)V",
					value = "INVOKE"
			)
	)
	private void foo(ILaserRelayConnectionHandler instance, BlockPos blockPos, World world) {
	
	}
}

