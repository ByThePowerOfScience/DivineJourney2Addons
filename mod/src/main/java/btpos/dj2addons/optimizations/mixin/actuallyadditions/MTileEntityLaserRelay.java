package btpos.dj2addons.optimizations.mixin.actuallyadditions;

import btpos.dj2addons.optimizations.impl.actuallyadditions.OptimizedLaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.api.laser.ILaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInventoryBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelay;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TileEntityLaserRelay.class)
public abstract class MTileEntityLaserRelay extends TileEntityInventoryBase {
	
	
	public MTileEntityLaserRelay(int slots, String name) {
		super(slots, name);
	}
	
	@Redirect(
            remap=false,
			method = "readSyncableNBT",
			at = @At(
					target = "Lde/ellpeck/actuallyadditions/api/laser/ILaserRelayConnectionHandler;removeRelayFromNetwork(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;)V",
					value = "INVOKE"
			)
	)
	private void dj2addons$dontRemoveFromNetwork(ILaserRelayConnectionHandler instance, BlockPos blockPos, World world) {}
	
	
	/**
	 * Updates the client's idea of the current state of the tile.
	 * @author ByThePowerOfScience
	 * @reason Implement optimized network handler
	 */
	@Overwrite(remap=false) @Override
	public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
		super.readSyncableNBT(compound, type);
		
		// so I can hotswap it in dev
		OptimizedLaserRelayConnectionHandler.onNetworkSync(compound, type, this.pos, this.world);
	}
	
}

