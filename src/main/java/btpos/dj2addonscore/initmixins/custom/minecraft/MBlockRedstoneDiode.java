package btpos.dj2addonscore.initmixins.custom.minecraft;

import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockRedstoneDiode.class)
public abstract class MBlockRedstoneDiode {
	@Shadow(aliases="calculateInputStrength")
	protected int calculateInputStrength(World worldIn, BlockPos pos, IBlockState state) {
		throw new IllegalStateException();
	}
}
