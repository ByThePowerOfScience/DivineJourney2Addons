package btpos.dj2addons.patches.mixin.immersiveengineering;

import blusunrize.immersiveengineering.common.blocks.TileEntityMultiblockPart;
import blusunrize.immersiveengineering.common.blocks.stone.TileEntityAlloySmelter;
import blusunrize.immersiveengineering.common.blocks.stone.TileEntityBlastFurnace;
import blusunrize.immersiveengineering.common.blocks.stone.TileEntityCokeOven;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityExcavator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={
		TileEntityAlloySmelter.class,
		TileEntityBlastFurnace.class,
		TileEntityCokeOven.class,
		TileEntityExcavator.class
}, remap=false)
public abstract class MTileEntityMultiblockPartSubclasses<T extends TileEntityMultiblockPart<T>> extends TileEntityMultiblockPart<T> {
	protected MTileEntityMultiblockPartSubclasses(int[] structureDimensions) {
		super(structureDimensions);
	}
	
	@Inject(
			method="isDummy",
			at=@At("HEAD"),
			cancellable = true
	)
	public void softOverwrite_fixInvalidOffsetSize(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(super.isDummy());
	}
}
