package org.btpos.dj2addons.mixin.init.minecraft.aerogel;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.btpos.dj2addons.impl.classrefs.CAetherLegacy;
import org.btpos.dj2addons.impl.classrefs.IsModLoaded;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(World.class)
public class MWorld {
	@Redirect(
			method="setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z",
			at=@At(
					target="Lnet/minecraft/world/chunk/Chunk;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;",
					value="INVOKE"
			)
	)
	private IBlockState addLavaAetherCase(Chunk chunk, BlockPos pos, IBlockState newState) {
		if (IsModLoaded.aether_legacy && ((World)(Object)this).provider.getDimension() == CAetherLegacy.getDimensionId() && Blocks.LAVA.getDefaultState().equals(newState)) {
			return chunk.setBlockState(pos, CAetherLegacy.getAerogelBlock().getDefaultState());
		} else {
			return chunk.setBlockState(pos, newState);
		}
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
		if (IsModLoaded.aether_legacy && ((World)(Object)this).provider.getDimension() == CAetherLegacy.getDimensionId() && Blocks.LAVA.getDefaultState().equals(newState)) {
			CAetherLegacy.playFizzleSound((World)(Object)this, pos);
		}
	}
}
