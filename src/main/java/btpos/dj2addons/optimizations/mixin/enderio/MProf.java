package btpos.dj2addons.optimizations.mixin.enderio;

import crazypants.enderio.base.diagnostics.Prof;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.regex.Pattern;

@Mixin(value = Prof.class, remap = false)
public abstract class MProf {
	
	@Unique private static final Pattern dj2addons$replacePeriod = Pattern.compile("\\.", Pattern.LITERAL);
	@Unique private static final Pattern dj2addons$replaceCrazyPantsString = Pattern.compile("crazypants_enderio_machines_machine_", Pattern.LITERAL);
	@Unique private static final Pattern dj2addons$replaceAllNonAlpha = Pattern.compile("[^a-zA-Z_]");
	
	/**
	 * Cache the pattern instead of recompiling every time. Saves 1.30% server time from my profiling.
	 */
	@Redirect(
			method="makeSection",
			at=@At(
					target="Ljava/lang/String;replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;",
					value="INVOKE"
			)
	)
	private static String dj2addons$precompileRegexPatterns_replace(String instance, CharSequence regex, CharSequence replacement) {
		char first = regex.charAt(0); // only match first char for speed since we know the only possibilities
		if (first == '.') {
			return dj2addons$replacePeriod.matcher(instance).replaceAll((String)replacement);
		} else {
			return dj2addons$replaceCrazyPantsString.matcher(instance).replaceAll((String)replacement);
		}
	}
	
	/**
	 * Cache the pattern instead of recompiling every time. Saves 1.30% server time from my profiling.
	 */
	@Redirect(
			method="makeSection",
			at=@At(
					target="Ljava/lang/String;replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;",
					value="INVOKE"
			)
	)
	private static String dj2addons$precompileRegexPatterns_replaceAll(String instance, String regex, String replacement) {
		return dj2addons$replaceAllNonAlpha.matcher(instance).replaceAll(replacement);
	}
}

