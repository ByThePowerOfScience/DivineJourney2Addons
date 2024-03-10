package btpos.dj2addons.patches.mixin.immersiveengineering;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import blusunrize.immersiveengineering.common.blocks.TileEntityMultiblockPart;
import blusunrize.immersiveengineering.common.blocks.TileEntityIEBase;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IBlockBounds;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IDirectionalTile;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IGeneralMultiblock;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.ITileDrop;
import net.minecraft.util.ITickable;

@Mixin(TileEntityMultiblockPart.class)
public abstract class MTileEntityMultiblockPart extends TileEntityIEBase
        implements ITickable, IDirectionalTile, IBlockBounds, IGeneralMultiblock
{
    @Shadow
    public int[] offset;

    //Original is like this:
    //public boolean isDummy() {
    //    return this.offset[0] != 0 || this.offset[1] != 0 || this.offset[2] != 0;
    //}
    public boolean isDummy() {
        return (this.offset.length >= 1 && this.offset[0] != 0) ||
                (this.offset.length >= 2 && this.offset[1] != 0) ||
                (this.offset.length >= 3 && this.offset[2] != 0);
    }
}
