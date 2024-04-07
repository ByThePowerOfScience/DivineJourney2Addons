package btpos.dj2addons.optimizations.mixin.actuallyadditions;

import btpos.dj2addons.optimizations.impl.actuallyadditions.OptimizedLaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldData.class)
public abstract class MWorldData {
	
	@Redirect(
			method = "readFromNBT",
			at = @At(
					target = "Lde/ellpeck/actuallyadditions/mod/misc/apiimpl/LaserRelayConnectionHandler;readNetworkFromNBT(Lnet/minecraft/nbt/NBTTagCompound;)Lde/ellpeck/actuallyadditions/api/laser/Network;",
					value = "INVOKE"
			)
	)
	private Network dj2addons$readFromNBT(NBTTagCompound tag) {
		return OptimizedLaserRelayConnectionHandler.readNetworkFromNBT(tag);
	}
	
	@Redirect(
			method = "writeToNBT",
			at = @At(
					target = "Lde/ellpeck/actuallyadditions/mod/misc/apiimpl/LaserRelayConnectionHandler;writeNetworkToNBT(Lde/ellpeck/actuallyadditions/api/laser/Network;)Lnet/minecraft/nbt/NBTTagCompound;",
					value = "INVOKE"
			)
	)
	private NBTTagCompound dj2addons$writeToNBT(Network network) {
		return OptimizedLaserRelayConnectionHandler.writeNetworkToNBT(network);
	}
}

