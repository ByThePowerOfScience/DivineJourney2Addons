package btpos.dj2addons.optimizations.mixin.actuallyadditions;

import btpos.dj2addons.optimizations.impl.actuallyadditions.OptimizedLaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.LaserRelayConnectionHandler;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value=LaserRelayConnectionHandler.class, remap=false)
public abstract class MLaserRelayConnectionHandler {
	@Inject(
			method= "readNetworkFromNBT(Lnet/minecraft/nbt/NBTTagCompound;)Lde/ellpeck/actuallyadditions/api/laser/Network;",
			at = @At("HEAD"),
			cancellable = true
	)
	private static void dj2addons$softOverwrite_readNetworkFromNBT(NBTTagCompound tag, CallbackInfoReturnable<Network> cir) {
		cir.setReturnValue(OptimizedLaserRelayConnectionHandler.readNetworkFromNBT(tag));
	}
	
	@Inject(
			method= "writeNetworkToNBT",
			at = @At("HEAD"),
			cancellable = true
	)
	private static void dj2addons$softOverwrite_writeNetworkToNBT(Network network, CallbackInfoReturnable<NBTTagCompound> cir) {
		cir.setReturnValue(OptimizedLaserRelayConnectionHandler.writeNetworkToNBT(network));
	}
	
}

