package btpos.dj2addons.optimizations.mixin.actuallyadditions;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayEnergy;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayEnergy.Mode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TileEntityLaserRelayEnergy.class)
public interface TileEntityLaserRelayEnergyAccessor {
	@Accessor
	Mode getMode();
}
