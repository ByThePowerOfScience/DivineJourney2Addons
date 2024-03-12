package btpos.dj2addons.patches.mixin.immersiveengineering;

import blusunrize.immersiveengineering.common.blocks.TileEntityMultiblockPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value=TileEntityMultiblockPart.class, remap=false)
public abstract class MTileEntityMultiblockPart {
    @Shadow
    public int[] offset;
    
    @Inject(
        method="isDummy",
        at=@At("HEAD"),
        cancellable = true
    )
    public void softOverwrite_fixInvalidOffsetSize(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(
                (this.offset.length >= 1 && this.offset[0] != 0) ||
                (this.offset.length >= 2 && this.offset[1] != 0) ||
                (this.offset.length >= 3 && this.offset[2] != 0));
    }
}
