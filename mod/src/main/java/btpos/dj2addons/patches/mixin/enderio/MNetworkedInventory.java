package btpos.dj2addons.patches.mixin.enderio;

import crazypants.enderio.conduits.conduit.item.NetworkedInventory;

import javax.annotation.Nonnull;
import net.minecraft.util.EnumFacing;
import crazypants.enderio.conduits.conduit.item.IItemConduit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import net.minecraftforge.items.IItemHandler;
import crazypants.enderio.base.filter.item.IItemFilter;

@Mixin(value=NetworkedInventory.class, remap=false)
public abstract class MNetworkedInventory {
	@Shadow
	private @Nonnull IItemConduit con;
	@Shadow
	private @Nonnull EnumFacing conDir;
	@Shadow
	private void setNextStartingSlot(int slot) {}

	@Inject(method = "transferItems", at = @At(value = "RETURN", ordinal = 2), locals = LocalCapture.CAPTURE_FAILSOFT)
	public void onTransferItems(CallbackInfoReturnable<Boolean> ci,
		IItemHandler inventory, int numSlots, int maxExtracted, IItemFilter filter, int slotChecksPerTick, int i, int slot	
		) {
		if (!con.isRoundRobinEnabled(conDir)) return;
		setNextStartingSlot(slot + 1);
	}
}
