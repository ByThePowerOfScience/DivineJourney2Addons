package btpos.dj2addons.api.mixin.bloodmagic;

import WayofTime.bloodmagic.tile.TileSoulForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TileSoulForge.class)
public interface TileSoulForgeAccessor {
	@Accessor @Mutable
	static void setTicksRequired(int ticksRequired) {throw new UnsupportedOperationException();}
	
	@Accessor @Mutable
	static void setWorldWillTransferRate(double worldWillTransferRate) {throw new UnsupportedOperationException();}
}
