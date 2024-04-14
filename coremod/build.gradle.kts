plugins {
	id("dj2addons.java-conventions")
}

version = rootProject.version



modUtils.enableMixins(
	"org.spongepowered:mixin:${project.properties["mixinVersion"]}",
	"mixins.dj2addons.core.refmap.json"
)

tasks.jar {
	manifest {
		attributes(mapOf(
				"FMLCorePlugin" to "btpos.dj2addons.DJ2ALoadingPlugin",
				
				"TweakClass" to "org.spongepowered.asm.launch.MixinTweaker",
				"MixinConfigs" to "mixins.dj2addons.bootstrap.json"
		))
	}
}