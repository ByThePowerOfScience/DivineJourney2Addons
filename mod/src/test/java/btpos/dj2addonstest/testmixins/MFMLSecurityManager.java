package btpos.dj2addonstest.testmixins;

import net.minecraftforge.fml.relauncher.FMLSecurityManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FMLSecurityManager.class)
public abstract class MFMLSecurityManager {
	@Redirect(
			remap = false,
			method = "checkPermission(Ljava/security/Permission;)V",
			at = @At(
					target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z",
					value = "INVOKE"
			)
	)
	private boolean foo(String instance, Object o) {
		return false;
	}
}

