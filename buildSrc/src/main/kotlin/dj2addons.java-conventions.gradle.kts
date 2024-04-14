

plugins {
	id("java-library")
	id("maven-publish")
	id("com.gtnewhorizons.retrofuturagradle") //version "1.3.35"
	id("idea")
}



//    apply(plugin="java-library")
//    apply(plugin="maven-publish")
//    apply(plugin="org.jetbrains.gradle.plugin.idea-ext") //, version="1.1.7"
//    apply(plugin="com.gtnewhorizons.retrofuturagradle") //, version="1.3.33"
//    apply(plugin="idea")

// Set the toolchain version to decouple the Java we run Gradle with from the Java used to compile and run the mod
java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(8))
		// Azul covers the most platforms for Java 8 toolchains, crucially including MacOS arm64
		vendor.set(org.gradle.jvm.toolchain.JvmVendorSpec.AZUL)
	}
	// Generate sources and javadocs jars when building and publishing
	withSourcesJar()
	withJavadocJar()
}

repositories {
	mavenCentral()
	maven { url = uri("https://jitpack.io") }
	maven { url = uri("https://repo.spongepowered.org/repository/maven-public/") }
}

val mixinextras: String = project.properties["mixinextras"].toString()
val mixinVersion: String = project.properties["mixin_version"].toString()

dependencies {
	annotationProcessor(mixinextras)
	annotationProcessor("org.spongepowered:mixin:${mixinVersion}")
	
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.2.0")
	testCompileOnly("org.junit.jupiter:junit-jupiter-api:5.2.0")
	testCompileOnly("org.junit.platform:junit-platform-launcher:1.1.0")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.2.0")
	
	runtimeOnly(files(rootProject.path + "/libs/_MixinBootstrap-1.1.0.jar"))
}

// Most RFG configuration lives here, see the JavaDoc for com.gtnewhorizons.retrofuturagradle.MinecraftExtension
minecraft {
	mcVersion.set("1.12.2")
	
	// Username for client run configurations
	username.set("Developer")
	
	// Generate a field named VERSION with the mod version in the injected Tags class
	injectedTags.put("VERSION", project.version)
	
	// If you need the old replaceIn mechanism, prefer the injectTags task because it doesn't inject a javac plugin.
	// tagReplacementFiles.add("RfgExampleMod.java")
	
	// Enable assertions in the mod's package when running the client or server
	extraRunJvmArguments.add("-ea:${project.group}")
	extraRunJvmArguments.addAll(listOf("-Dforge.logging.console.level=debug", "-Dmixin.debug.verbose=true", "-Dmixin.debug.export=true", "-Dmixin.hotSwap=true"))
	
	// If needed, add extra tweaker classes like for mixins
	extraTweakClasses.add("org.spongepowered.asm.launch.MixinTweaker")
	
	// Exclude some Maven dependency groups from being automatically included in the reobfuscated runs
	groupsToExcludeFromAutoReobfMapping.addAll("com.diffplug", "com.diffplug.durian", "net.industrial-craft")
}

// Generates a class named rfg.examplemod.Tags with the mod version in it, you can find it at
tasks.injectTags.configure {
	outputClassName.set("${project.group}.Tags")
}

// Put the version from gradle into mcmod.info
tasks.processResources.configure {
	val projVersion = project.version.toString() // Needed for configuration cache to work
	inputs.property("version", projVersion)
	
	filesMatching("mcmod.info") {
		expand(mapOf("modVersion" to projVersion))
	}
}

// Create a new dependency type for runtime-only dependencies that don't get included in the maven publication
val runtimeOnlyNonPublishable: Configuration by configurations.creating {
	description = "Runtime only dependencies that are not published alongside the jar"
	isCanBeConsumed = false
	isCanBeResolved = false
}

//val shadow: Configuration by configurations.creating {
//    description = "Dependencies that should be copied as-is into META-INF/libs during build task."
//}

listOf(configurations.runtimeClasspath, configurations.testRuntimeClasspath).forEach {
	it.configure {
		extendsFrom(
				runtimeOnlyNonPublishable
		)
	}
}
