package org.btpos.dj2addons.mixin.def.api.astralsorcery;

import hellfirepvp.astralsorcery.common.tile.TileCelestialCrystals;
import net.minecraft.block.state.IBlockState;
import org.btpos.dj2addons.crafttweaker.impl.astralsorcery.VAstralSorcery;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TileCelestialCrystals.class)
public class MTileCelestialCrystals {
	@ModifyArg(method="update", at=@At(target="Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z", ordinal = 0, value="INVOKE"))
	public IBlockState alterStarmetalIronChange(IBlockState state) {
		return VAstralSorcery.starmetalConversion;
	}
	
	@ModifyVariable(method="update", name="mul", at=@At("LOAD"), ordinal = 0)
	public double scaleTimeRequired(double d) {
		return d;
	}
}
