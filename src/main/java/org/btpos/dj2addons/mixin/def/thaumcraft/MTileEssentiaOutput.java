package org.btpos.dj2addons.mixin.def.thaumcraft;

import fr.frinn.modularmagic.common.tile.TileAspectProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.common.tiles.essentia.TileEssentiaOutput;



// AKA "Emptying Essentia Transfuser"
@Mixin(TileEssentiaOutput.class)
abstract class MTileEssentiaOutput {
	/**
	 * Stops the Modular Magic "Aspect Provider" (essentia jar) from being drained by Emptying Essentia Transfusers.
	 */
	@Redirect(method= "fillBuffer()V", remap=false, at=@At(target="Lthaumcraft/api/ThaumcraftApiHelper;getConnectableTile(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/tileentity/TileEntity;", value="INVOKE"))
	private TileEntity stopDrainModMagicJar(World world, BlockPos pos, EnumFacing face) {
		TileEntity te = ThaumcraftApiHelper.getConnectableTile(world, pos, face);
		return (te instanceof TileAspectProvider) ? null : te;
	}
	
	
}