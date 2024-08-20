import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.jvm.toolchain.JvmVendorSpec


plugins {
    `java-library`
    idea
    id("maven-publish")
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.8"
    id("com.gtnewhorizons.retrofuturagradle") version "1.4.1"
}

group = "btpos.dj2addons"
version = "1.2.1.1.1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
        // Azul covers the most platforms for Java 8 toolchains, crucially including MacOS arm64
        vendor.set(JvmVendorSpec.AZUL)
    }
    // Generate sources and javadocs jars when building and publishing
    withSourcesJar()
    withJavadocJar()
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
        inheritOutputDirs = true // Fix resources in IJ-Native runs
    }
}


tasks.register<Copy>("copyModToModpack") {
    group = "dj2addons"
    from(tasks.reobfJar)
    into(File("%APPDATA%\\.mineyourmind\\instances\\divinejourney2\\minecraft\\mods"))
}



val pythonFile: File = if (Os.isFamily(Os.FAMILY_WINDOWS)) {
    project.file("venv/Scripts/python.exe")
} else {
    project.file("venv/bin/python3")
}


// Add an access tranformer
// tasks.deobfuscateMergedJarToSrg.configure {accessTransformerFiles.from("src/main/resources/META-INF/mymod_at.cfg")}

// Dependencies
repositories {
    mavenLocal()
    maven {
        name = "CurseMaven"
        url = uri("https://cursemaven.com")
    }
    maven {
        name = "Spongepowered"
        url = uri("https://repo.spongepowered.org/repository/maven-public/")
    }
    maven {
        name = "JEI Maven"
        url = uri("https://dvs1.progwml6.com/files/maven")
    }
    maven {
        name = "BlameJared"
        url = uri("https://maven.blamejared.com/")
    }
    maven {
        name = "CoFH Maven"
        url = uri("https://maven.covers1624.net")
    }
    maven {
        url = uri("https://maven.cleanroommc.com")
    }
    mavenCentral()
}



dependencies {
    annotationProcessor(modUtils.enableMixins(
        "org.spongepowered:mixin:${project.properties["mixin_version"]}:processor",
        "mixins.dj2addons.refmap.json"
    ))
    
	compileOnly("zone.rong:mixinbooter:7.0") { // bleugh please get it out of this package cleanroom
        isTransitive = false
    }
    runtimeOnly(rfg.deobf("curse.maven:mixin-booter-419286:4090558")) // ensure compatibility with the one used in the modpack
    
    implementation("com.cleanroommc:configanytime:1.0") // init configs for mixins
    
    implementation("CraftTweaker2:CraftTweaker2-MC1120-Main:1.12-+")
    
    implementation("mezz.jei:jei_1.12.2:4.16.1.302")
    
    compileOnly(rfg.deobf("curse.maven:DraconicEvolution-223565:3431261"))
    
    
    compileOnly(rfg.deobf("curse.maven:ActuallyAdditions-228404:3117927"))
    compileOnly(rfg.deobf("curse.maven:EnderIO-64578:3328811"))
    compileOnly(rfg.deobf("curse.maven:ProjectIntelligence-306028:2833640"))
    compileOnly(rfg.deobf("curse.maven:EnderCore-231868:2972849"))
    compileOnly(rfg.deobf("curse.maven:BrandonsCore-231382:3408276"))
    compileOnly(rfg.deobf("curse.maven:CodeChickenLib-242818:2779848"))
    
    compileOnly("cofh:RedstoneFlux:1.12-2.0.0.1:universal")
    
    compileOnly(rfg.deobf("curse.maven:Erebus-220698:3211974"))
    
    // **CRAFTTWEAKER HOOKS**
    implementation(rfg.deobf("curse.maven:Patchouli-306770:3162874"))
    implementation(rfg.deobf("curse.maven:Baubles-227083:2518667"))
    
    
    compileOnly(rfg.deobf("curse.maven:Botania-225643:3330934"))
    
    compileOnly(rfg.deobf("curse.maven:ExtraUtils2-225561:2678374"))
    
    
    
    compileOnly(rfg.deobf("curse.maven:Roots-246183:3484394"))
    compileOnly(rfg.deobf("curse.maven:MysticalWorld-282940:3460961"))
    compileOnly(rfg.deobf("curse.maven:MysticalLib-277064:3483816"))
    
    
    compileOnly(rfg.deobf("curse.maven:AstralSorcery-241721:3044416"))
    
    compileOnly(rfg.deobf("curse.maven:ImmersiveEngineering-231951:2974106"))
    
    compileOnly(rfg.deobf("curse.maven:BloodMagic-224791:2822288"))
    compileOnly(rfg.deobf("curse.maven:GuideAPI-228832:2645992"))
    
    
    compileOnly(rfg.deobf("curse.maven:Totemic-237541:2700798"))
    
    compileOnly(rfg.deobf("curse.maven:ExtremeReactors-250277:3194746"))
    compileOnly(rfg.deobf("curse.maven:ZeroCore-247921:3194743"))
    
    
    compileOnly(rfg.deobf("curse.maven:Bewitchment-285439:3256343") as String) {
        exclude(group="org.spongepowered")
    }
    
    
    compileOnly(rfg.deobf("curse.maven:ModTweaker-220954:3840577"))
    
    // **TWEAKS**
    compileOnly(rfg.deobf("curse.maven:RFTools-224641:2861573"))
    compileOnly(rfg.deobf("curse.maven:McJtyLib-233105:2745846"))
    
    compileOnly(rfg.deobf("curse.maven:Forgelin-248453:2785465"))
    compileOnly(rfg.deobf("curse.maven:TeslaCoreLib-254602:3438487"))
    compileOnly(rfg.deobf("curse.maven:IndustrialForegoing-266515:2745321"))
    
    compileOnly(rfg.deobf("curse.maven:AgriCraft-225635:3317747"))
    compileOnly(rfg.deobf("curse.maven:InfinityLib-251396:3317119"))
    
    compileOnly(rfg.deobf("curse.maven:MysticalAgriculture-246640:2704562"))
    
    compileOnly(rfg.deobf("curse.maven:MoreTweaker-336569:3226142"))
    compileOnly(rfg.deobf("curse.maven:MTLib-253211:3308160"))
    
    
    compileOnly(rfg.deobf("curse.maven:AppleSkin-248787:2987247"))
    
    compileOnly(rfg.deobf("curse.maven:ModularMachinery-270790:2713714"))
    compileOnly(rfg.deobf("curse.maven:ModularMagic-324318:2737623"))
    
    implementation(rfg.deobf("curse.maven:Thaumcraft-223628:2629023"))
    
    compileOnly(rfg.deobf("curse.maven:Aether-255308:3280119"))
    
    
    compileOnly(rfg.deobf("curse.maven:PackagedAuto-308380:3614585"))
    
    
    compileOnly(rfg.deobf("curse.maven:ThermalExpansion-69163:2926431"))
    compileOnly(rfg.deobf("curse.maven:ThermalFoundation-222880:2926428"))
    compileOnly(rfg.deobf("curse.maven:CoFHCore-69162:2920433"))
    
    compileOnly(rfg.deobf("curse.maven:Mekanism-268560:2835175"))
    
    compileOnly(rfg.deobf("curse.maven:RadixCore-77286:2483855"))
    
    compileOnly(rfg.deobf("curse.maven:VanillaFix-292785:2915154"))
    
    
    // Dev Tools
    testImplementation( // For ZenDoc
        "org.reflections:reflections:0.10.2"
    )
    
    // Testing Framework
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.2.0")
    testCompileOnly("org.junit.jupiter:junit-jupiter-api:5.2.0")
    testCompileOnly("org.junit.platform:junit-platform-launcher:1.1.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.2.0")
}

configurations.compileClasspath {
    this.allDependencies.forEach {
        if (it.group != "org.spongepowered") // trying to stop the shaded mixin dep in Bewitchment from being what IDEA links to
            (it as ExternalDependency).exclude(group="org.spongepowered")
    }
}




// Most RFG configuration lives here, see the JavaDoc for com.gtnewhorizons.retrofuturagradle.MinecraftExtension
minecraft {
    mcVersion.set("1.12.2")
    
    // Username for client run configurations
//    username.set("Developer")
    
    // Generate a field named VERSION with the mod version in the injected Tags class
    injectedTags.put("VERSION", project.version)
//    injectedTags.put("CERT_FINGERPRINT", project.property("dj2addons_signSHA1").toString())
    
    // If you need the old replaceIn mechanism, prefer the injectTags task because it doesn't inject a javac plugin.
    // tagReplacementFiles.add("RfgExampleMod.java")
    
    // Enable assertions in the mod's package when running the client or server
    extraRunJvmArguments.add("-ea:${project.group}")
    extraRunJvmArguments.addAll(listOf(
        "-Dforge.logging.markers=REGISTRIES,REGISTRYDUMP",
        "-Dforge.logging.console.level=debug",
        "-Dmixin.debug.verbose=true",
        "-Dmixin.debug.export=true"
    ))
    
    // If needed, add extra tweaker classes like for mixins
    extraTweakClasses.add("org.spongepowered.asm.launch.MixinTweaker")
    
    // Exclude some Maven dependency groups from being automatically included in the reobfuscated runs
    groupsToExcludeFromAutoReobfMapping.addAll(
        "com.diffplug",
        "com.diffplug.durian",
        "net.industrial-craft"
    )
}

// Generates a class named rfg.examplemod.Tags with the mod version in it, you can find it at
tasks.injectTags.configure {
    outputClassName.set("${project.group}.Tags")
}

tasks.processIdeaSettings.configure {
    dependsOn(tasks.injectTags)
}

// Create a new dependency type for runtime-only dependencies that don't get included in the maven publication
val runtimeOnlyNonPublishable: Configuration by configurations.creating {
    description = "Runtime only dependencies that are not published alongside the jar"
    isCanBeConsumed = false
    isCanBeResolved = false
}

listOf(configurations.runtimeClasspath, configurations.testRuntimeClasspath).forEach {
    it.configure {
        extendsFrom(
            runtimeOnlyNonPublishable
        )
    }
}

configurations.testImplementation {
    extendsFrom(configurations.runtimeClasspath.get(), configurations.compileClasspath.get())
}

// Put the version from gradle into mcmod.info
tasks.processResources.configure {
    val projVersion = project.version.toString() // Needed for configuration cache to work
    val mcVersion = "1.12.2"
    inputs.properties(mapOf(
        "version" to projVersion,
        "mc_version" to mcVersion
    ))
    
    filesMatching("mcmod.info") {
        expand(mapOf(
            "modVersion" to projVersion,
            "minecraftVersion" to mcVersion
        ))
    }
}

tasks.jar.configure {
    manifest {
        attributes(mapOf(
            "FMLCorePluginContainsFMLMod" to true,
            "FMLCorePlugin" to "btpos.dj2addons.core.DJ2ACoremod",
            "TweakerClass" to "org.spongepowered.asm.launch.MixinTweaker"
        ))
    }
}

tasks.javadoc {
    with (options as StandardJavadocDocletOptions) {
        addBooleanOption("Xdoclint:none", true)
        tags(
            "apiNote:a:API Note:",
            "implSpec:a:Implementation Requirements:",
            "implNote:a:Implementation Note:",
            "reason:a:Reason:"
        )
    }
}

//tasks.register("signJar") {
//    onlyIf {
//        project.hasProperty("dj2addons_keyStore")
//    }
//    dependsOn(tasks.reobfJar)
//
//    val keystore = project.property("dj2addons_keyStore").toString()
//    val keystore_alias = project.property("dj2addons_keyStoreAlias").toString()
//    val keystore_pass = project.property("dj2addons_keyStorePass").toString()
//    val signSha1 = project.property("dj2addons_signSHA1").toString()
//
//    inputs.properties(mapOf(
//        "keystore" to keystore,
//        "keystore_alias" to keystore_alias,
//        "keystore_pass" to keystore_pass,
//        "signSHA1" to signSha1,
//        "toSign" to tasks.reobfJar.get().outputs
//    ))
//
//    doLast {
//        ant.withGroovyBuilder {
//            "signjar"(
//                "jar" to tasks.reobfJar.get().outputs.files,
//                "destDir" to tasks.reobfJar.get().outputs.files,
//                "alias" to keystore_alias,
//                "storetype" to "jks",
//                "keystore" to keystore,
//                "storepass" to keystore_pass,
//                "verbose" to "true",
//                "preservelastmodified" to "true"
//            )
//        }
//    }
//}
//
//tasks.reobfJar.configure {
//    finalizedBy("signJar")
//}




tasks.register<Exec>("generateModpackZip") {
    commandLine(pythonFile, "./python/GenerateModpackZip.py")
}

tasks.register<Exec>("updateModpack") {
    dependsOn(tasks.build)
    commandLine(pythonFile, "./python/UpdateModpack.py")
}

tasks.register<Exec>("updateCFModpack") {
    dependsOn(tasks.build)
    commandLine(pythonFile, "./python/UpdateCFModpack.py")
}



tasks.register<Exec>("removeShadedMixinLibraries") {
    commandLine(pythonFile, rootProject.file("python/RemoveShadedMixinLibraries.py").toString())
}



tasks.register<Copy>("generateTestZS") {
    from(rootProject.file("Test.zs"))
    into(project.file("run/scripts"))
}

//tasks.register("runTests", JavaExec::class) {
//    dependsOn("build", "generateTestZS")
//    val modproject = project(":mod")
//    val coremodproject = project(":coremod")
//    classpath = sourceSets.main.output.classesDirs
//                + sourceSets.main.compileClasspath
//                + sourceSets.test.output.classesDirs
//                + sourceSets.test.runtimeClasspath
//                + coremodproject.sourceSets.main.output.classesDirs
//                + coremodproject.sourceSets.main.compileClasspath
//                + coremodproject.sourceSets.test.output.classesDirs
//                + coremodproject.sourceSets.test.runtimeClasspath
//    main = "GenerateTestZS"
//    setArgsString(rootProject.projectDir.toPath().resolve("Test.zs").toString())
//}

tasks.register<Jar>("buildTestJar") {
    from(sourceSets.test)
    archiveBaseName.set("dj2addons-test")
}


// Publishing to a Maven repository
//publishing {
//    publications {
//        create<MavenPublication>("maven") {
//            from(components["java"])
//        }
//    }
//    repositories {
//        // Example: publishing to the GTNH Maven repository
//        maven {
//            url = uri("https://nexus.gtnewhorizons.com/repository/releases/")
//            credentials {
//                username = System.getenv("MAVEN_USER") ?: "NONE"
//                password = System.getenv("MAVEN_PASSWORD") ?: "NONE"
//            }
//        }
//    }
//}

