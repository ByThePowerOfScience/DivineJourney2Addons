package btpos.dj2addons.initmixins.patches.minecraft;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import btpos.dj2addons.common.modrefs.CAetherLegacy;
import btpos.dj2addons.common.modrefs.IsModLoaded;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(World.class)
public abstract class MWorld {
	@ModifyVariable(
			method="setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z",
			at=@At("HEAD"),
			argsOnly = true
	)
	private IBlockState addLavaAetherCase(IBlockState newState) {
		if ((Blocks.LAVA == newState.getBlock() || Blocks.FLOWING_LAVA == newState.getBlock())
		    && IsModLoaded.aether_legacy
		    && ((World)(Object)this).provider.getDimension() == CAetherLegacy.getDimensionId())
		{
			return CAetherLegacy.getAerogelBlock().getDefaultState();
		}
		return newState;
	}
	
	@Inject(
			method="setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z",
			at=@At(
					target="Lnet/minecraft/world/chunk/Chunk;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;",
					value="INVOKE"
			),
			locals=LocalCapture.CAPTURE_FAILSOFT
	)
	private void addLavaAetherSound(BlockPos pos, IBlockState newState, int flags, CallbackInfoReturnable<Boolean> cir) {
		if (Blocks.LAVA.getDefaultState().equals(newState) && IsModLoaded.aether_legacy && ((World)(Object)this).provider.getDimension() == CAetherLegacy.getDimensionId()) {
			CAetherLegacy.playFizzleSound((World)(Object)this, pos);
		}
	}
}
