package btpos.dj2addons.tweaks.mixin.thaumcraft;

import btpos.dj2addons.common.modrefs.CModularMagic;
import btpos.dj2addons.common.modrefs.IsModLoaded;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import thaumcraft.common.tiles.essentia.TileEssentiaOutput;

// AKA "Emptying Essentia Transfuser"
@Mixin(TileEssentiaOutput.class)
abstract class MTileEssentiaOutput {
	/**
	 * Stops the Modular Magic "Aspect Provider" (essentia jar) from being drained by Emptying Essentia Transfusers.
	 */
	@WrapOperation(method= "fillBuffer()V", remap=false, at=@At(target="Lthaumcraft/api/ThaumcraftApiHelper;getConnectableTile(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/tileentity/TileEntity;", value="INVOKE"))
	private TileEntity stopDrainModMagicJar(World world, BlockPos pos, EnumFacing face, Operation<TileEntity> original) {
		TileEntity te = original.call(world, pos, face);
		if (!IsModLoaded.modularmagic)
			return te;
		if (CModularMagic.isTileAspectProvider(te)) {
			return null;
		}
		return te;
	}
	//TODO make it so they will only not drain the last point of essentia, so as to keep them "seeded"
	
}