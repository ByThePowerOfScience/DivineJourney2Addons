package btpos.dj2addonstest.testmixins;

import btpos.dj2addonstest.DJ2AddonsTest;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Loader.class)
public abstract class MStartTestsInLoader {
	@Shadow private LoadController modController;
	
//	@Inject(method = "loadMods", at = @At("HEAD"), remap = false)
	@Inject(method = "preinitializeMods", at = @At("TAIL"), remap = false)
	private void runTests(CallbackInfo ci) {
		DJ2AddonsTest.runTests();
	}
	
	@Inject(method= "serverStopped()V", at=@At("HEAD"), remap=false, cancellable = true)
	private void stopNPEFromEarlyReturn(CallbackInfo ci) {
		if (modController == null)
			ci.cancel();
	}
}

