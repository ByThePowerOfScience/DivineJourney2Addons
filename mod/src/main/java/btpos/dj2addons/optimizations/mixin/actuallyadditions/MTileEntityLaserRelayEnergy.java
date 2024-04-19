package btpos.dj2addons.optimizations.mixin.actuallyadditions;

import btpos.dj2addons.optimizations.impl.actuallyadditions.GraphNetwork;
//import com.llamalad7.mixinextras.sugar.Local;
//import com.llamalad7.mixinextras.sugar.Share;
//import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayEnergy;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayEnergy.Mode;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(value=TileEntityLaserRelayEnergy.class, remap=false)
public abstract class MTileEntityLaserRelayEnergy extends TileEntity {
	
	@SuppressWarnings("rawtypes")
	@Redirect(
			remap = false,
			method = "transferEnergyToReceiverInNeed",
			at = @At(
					target = "Lio/netty/util/internal/ConcurrentSet;iterator()Ljava/util/Iterator;",
					value = "INVOKE"
			)
	)
	private Iterator dj2addons$stopConnectionsLoop(ConcurrentSet instance) {
		return new Iterator() {
			@Override
			public boolean hasNext() {
				return false;
			}
			
			@Override
			public Object next() {
				return null;
			}
		};
	}
	
	@Unique
	private static int dj2addons$totalReceiverAmount;
	
	@Inject(
			method="transferEnergyToReceiverInNeed",
			at = @At(
					target = "Lio/netty/util/internal/ConcurrentSet;iterator()Ljava/util/Iterator;",
					value = "INVOKE_ASSIGN"
			),
			locals = LocalCapture.CAPTURE_FAILSOFT
	)
	public void dj2addons$useGraphNetworkInsteadOfIterating(EnumFacing from, Network network, int maxTransfer, boolean simulate,
	                                                        CallbackInfoReturnable<Integer> cir, Set<TileEntityLaserRelayEnergy> relaysThatWork)
//	                                                        @Local(ordinal=1) Set<TileEntityLaserRelayEnergy> relaysThatWork,
//	                                                        @Share("dj2addons$totalReceiverAmount") LocalIntRef sharedTotalReceiverAmount)
	{
		if (!(network instanceof GraphNetwork)) {
			throw new RuntimeException("Not a GraphNetwork! WTF!!!!");
		}
		
		AtomicInteger totalReceiverAmount = new AtomicInteger(0);
		
		((GraphNetwork) network).forEach(node -> {
			BlockPos relay = node.pos;
			
			// the rest of this is copied from the original method
			if (relay != null && this.world.isBlockLoaded(relay)) {
				TileEntity relayTile = this.world.getTileEntity(relay);
				if (relayTile instanceof TileEntityLaserRelayEnergy) {
					TileEntityLaserRelayEnergy theRelay = (TileEntityLaserRelayEnergy) relayTile;
					if (((TileEntityLaserRelayEnergyAccessor)theRelay).getMode() != Mode.INPUT_ONLY) {
						boolean workedOnce = false;
						
						for (EnumFacing facing : theRelay.receiversAround.keySet()) {
							//noinspection EqualsBetweenInconvertibleTypes
							if (!theRelay.equals(this) || facing != from) {
								TileEntity tile = theRelay.receiversAround.get(facing);
								
								EnumFacing opp = facing.getOpposite();
								if (tile != null) {
									if (tile.hasCapability(CapabilityEnergy.ENERGY, opp)) {
										IEnergyStorage cap = tile.getCapability(CapabilityEnergy.ENERGY, opp);
										if (cap != null && cap.receiveEnergy(maxTransfer, true) > 0) {
											totalReceiverAmount.incrementAndGet();
											workedOnce = true;
										}
									}
								}
							}
						}
						
						if (workedOnce) {
							relaysThatWork.add(theRelay);
						}
					}
				}
			}
		});
		
		dj2addons$totalReceiverAmount = totalReceiverAmount.get();
//		sharedTotalReceiverAmount.set(totalReceiverAmount.get());
	}
	
	@ModifyVariable(
			method="transferEnergyToReceiverInNeed",
			name = "totalReceiverAmount",
			at = @At(
					target = "io/netty/util/internal/ConcurrentSet.iterator ()Ljava/util/Iterator;",
					value = "INVOKE_ASSIGN",
					shift = Shift.BY,
					by = 2
			)
	)
	public int dj2addons$addBackTotalReceiverAmount(int value) {//, @Share("dj2addons$totalReceiverAmount") LocalIntRef ref) {
		return dj2addons$totalReceiverAmount;
		//		return ref.get();
	}
}

