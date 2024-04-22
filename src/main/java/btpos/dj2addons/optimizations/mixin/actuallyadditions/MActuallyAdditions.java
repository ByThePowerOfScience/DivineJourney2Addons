package btpos.dj2addons.optimizations.mixin.actuallyadditions;

import btpos.dj2addons.optimizations.impl.actuallyadditions.OptimizedLaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.laser.ILaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ActuallyAdditions.class)
public abstract class MActuallyAdditions {
	@Redirect(
			remap=false,
			method = "preInit",
			at=@At(
					target = "de/ellpeck/actuallyadditions/api/ActuallyAdditionsAPI.connectionHandler : Lde/ellpeck/actuallyadditions/api/laser/ILaserRelayConnectionHandler;",
					opcode = Opcodes.PUTSTATIC,
					value = "FIELD"
			)
	)
	private void setConnectionHandler(ILaserRelayConnectionHandler value) {
		ActuallyAdditionsAPI.connectionHandler = new OptimizedLaserRelayConnectionHandler();
	}
}

