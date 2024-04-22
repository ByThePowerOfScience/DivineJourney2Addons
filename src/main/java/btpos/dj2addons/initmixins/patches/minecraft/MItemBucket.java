package btpos.dj2addons.initmixins.patches.minecraft;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBucket;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import btpos.dj2addons.common.modrefs.CAetherLegacy;
import btpos.dj2addons.common.modrefs.IsModLoaded;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemBucket.class)
abstract class MItemBucket {
	@Shadow(aliases="containedBlock")
	@Final
	private Block containedBlock;
	
	@Redirect(
			method = "tryPlaceContainedLiquid(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z",
			at = @At(
					target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FF)V",
					value = "INVOKE",
					ordinal = 1
			)
	)
	private void changeLavaSound(World world, EntityPlayer player, BlockPos pos, SoundEvent soundIn, SoundCategory category, float volume, float pitch) {
		if (Blocks.LAVA == this.containedBlock && IsModLoaded.aether_legacy && world.provider.getDimension() == CAetherLegacy.getDimensionId()) {
			CAetherLegacy.playFizzleSound(world, player, pos);
		} else {
			world.playSound(player, pos, soundIn, SoundCategory.BLOCKS, volume, pitch);
		}
	}
	
	
	@Redirect(
			method = "tryPlaceContainedLiquid(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z",
			at = @At(
					target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z",
					value = "INVOKE"
			)
	)
	private boolean changeLavaPlaceBehavior(World world, BlockPos blockPos, IBlockState oldState, int flags) {
		if (Blocks.LAVA == this.containedBlock && IsModLoaded.aether_legacy && world.provider.getDimension() == CAetherLegacy.getDimensionId()) {
			return world.setBlockState(blockPos, CAetherLegacy.getAerogelBlock().getDefaultState(), 11);
		} else {
			return world.setBlockState(blockPos, this.containedBlock.getDefaultState(), flags);
		}
	}
}
