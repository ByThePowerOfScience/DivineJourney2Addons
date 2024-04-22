package btpos.dj2addons.optimizations.mixin.actuallyadditions;

import btpos.dj2addons.optimizations.impl.actuallyadditions.GraphNetwork;
import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityItemViewer.GenericItemHandlerInfo;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayItem;
import de.ellpeck.actuallyadditions.mod.util.compat.SlotlessableItemHandlerWrapper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(TileEntityLaserRelayItem.class)
public abstract class MTileEntityLaserRelayItem extends TileEntity {
	@Inject(
			remap = false,
			method = "getItemHandlersInNetwork",
			at = @At("HEAD"),
			cancellable = true
	)
	private void dj2addons$softOverwrite_getItemHandlersInNetwork(Network network, List<GenericItemHandlerInfo> storeList, CallbackInfo ci)
	{
		if (!(network instanceof GraphNetwork)) {
			throw new RuntimeException("Somehow we got a normal network through."); // DEBUG
		}
		
		((GraphNetwork) network).forEach(node -> {
			BlockPos relay = node.pos;
			if (relay != null && this.world.isBlockLoaded(relay)) {
				TileEntity aRelayTile = this.world.getTileEntity(relay);
				if (aRelayTile instanceof TileEntityLaserRelayItem) {
					TileEntityLaserRelayItem relayTile = (TileEntityLaserRelayItem) aRelayTile;
					GenericItemHandlerInfo info = new GenericItemHandlerInfo(relayTile);
					
					for (Map.Entry<BlockPos, SlotlessableItemHandlerWrapper> handler : relayTile.handlersAround.entrySet()) {
						info.handlers.add(handler.getValue());
					}
					
					storeList.add(info);
				}
			}
		});
		
		ci.cancel();
	}
}

