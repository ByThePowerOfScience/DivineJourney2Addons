package btpos.dj2addons.initmixins.api.minecraft;

import btpos.dj2addons.api.minecraft.ItemFrames;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityItemFrame.class)
public abstract class MItemHangingEntity extends EntityHanging {
	private MItemHangingEntity(World worldIn) {
		super(worldIn);
	}
	
	
	@Override @SuppressWarnings("ConstantConditions")
	public boolean onValidSurface() {
		IBlockState blockState = world.getBlockState(this.hangingPosition.offset(facingDirection.getOpposite()));
		return !ItemFrames.Internal.isDisallowed(blockState) && super.onValidSurface();
	}
}
