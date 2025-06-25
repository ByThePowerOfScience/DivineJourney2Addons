package btpos.dj2addons.patches.mixin.immersiveengineering;

import blusunrize.immersiveengineering.common.blocks.TileEntityMultiblockPart;
import blusunrize.immersiveengineering.common.blocks.stone.TileEntityAlloySmelter;
import blusunrize.immersiveengineering.common.blocks.stone.TileEntityBlastFurnace;
import blusunrize.immersiveengineering.common.blocks.stone.TileEntityBlastFurnaceAdvanced;
import blusunrize.immersiveengineering.common.blocks.stone.TileEntityCokeOven;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityExcavator;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityArcFurnace;

import blusunrize.immersiveengineering.common.blocks.metal.TileEntityMultiblockMetal;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityAssembler;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityAutoWorkbench;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityBottlingMachine;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityCrusher;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityDieselGenerator;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityFermenter;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityLightningrod;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityMetalPress;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityMixer;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityRefinery;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntitySheetmetalTank;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntitySilo;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntitySqueezer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={
		TileEntityAlloySmelter.class,
		TileEntityBlastFurnace.class,
		TileEntityBlastFurnaceAdvanced.class,
		TileEntityCokeOven.class,
		TileEntityExcavator.class,
		TileEntityArcFurnace.class,
		
		TileEntityMultiblockMetal.class,
		TileEntityAssembler.class,
		TileEntityAutoWorkbench.class,
		TileEntityBottlingMachine.class,
		TileEntityCrusher.class,
		TileEntityDieselGenerator.class,
		TileEntityFermenter.class,
		TileEntityLightningrod.class,
		TileEntityMetalPress.class,
		TileEntityMixer.class,
		TileEntityRefinery.class,
		TileEntitySheetmetalTank.class,
		TileEntitySilo.class,
		TileEntitySqueezer.class
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
