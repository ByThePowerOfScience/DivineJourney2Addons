package btpos.dj2addons.optimizations.mixin.actuallyadditions;

import btpos.dj2addons.optimizations.impl.actuallyadditions.OptimizedLaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.nbt.NBTTagCompound;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldData.class)
public abstract class MWorldData {
	
	@Shadow(remap = false) @Final
	public ConcurrentSet<Network> laserRelayNetworks;
	
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
			remap = false,
			method = "writeToNBT",
			at = @At(
					target = "Lde/ellpeck/actuallyadditions/mod/misc/apiimpl/LaserRelayConnectionHandler;writeNetworkToNBT(Lde/ellpeck/actuallyadditions/api/laser/Network;)Lnet/minecraft/nbt/NBTTagCompound;",
					value = "INVOKE"
			)
	)
	private NBTTagCompound dj2addons$writeToNBT(Network network) {
		return OptimizedLaserRelayConnectionHandler.writeNetworkToNBT(network);
	}
	
	@Inject(
			method = "readFromNBT",
			at = @At(
					target = "Lde/ellpeck/actuallyadditions/mod/data/WorldData;playerSaveData:Ljava/util/concurrent/ConcurrentHashMap;",
					value="FIELD",
					opcode = Opcodes.GETFIELD,
					remap=false
			)
	)
	private void addToLookupMapAfterNetworkLoad(NBTTagCompound compound, CallbackInfo ci) {
		OptimizedLaserRelayConnectionHandler handler = (OptimizedLaserRelayConnectionHandler) ActuallyAdditionsAPI.connectionHandler;
		handler.networkLookupMap.clear();
		laserRelayNetworks.forEach(handler::addNetworkNodesToNetworkLookupMap);
	}
}

