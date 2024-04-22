package btpos.dj2addons.patches.mixin.vanillafix;

import org.dimdev.vanillafix.crashes.StacktraceDeobfuscator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = StacktraceDeobfuscator.class, remap = false)
abstract class MStacktraceDeobfuscator {
	@Inject(
			method = "deobfuscateMethodName",
			at = @At("HEAD"),
			cancellable = true
	)
	private static void dj2addons$stopNullMethodNamesFromCrashingProgram(String srgName, CallbackInfoReturnable<String> cir)  {
		if (srgName == null) {
			cir.setReturnValue("<UNKNOWN>");
		}
	}
}
