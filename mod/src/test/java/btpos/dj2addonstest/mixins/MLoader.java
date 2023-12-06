package btpos.dj2addonstest.mixins;

import btpos.dj2addonstest.DJ2AddonsTest;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Loader.class)
public abstract class MLoader {
	@Inject(method = "loadMods", at = @At("HEAD"), remap = false)
	private void foo(List<String> injectedModContainers, CallbackInfo ci) {
		DJ2AddonsTest.runTests();
	}
}

