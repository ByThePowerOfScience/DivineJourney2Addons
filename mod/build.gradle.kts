import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    id("dj2addons.java-conventions")
    idea
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.8"
    
}

version = rootProject.version

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
    mavenCentral()
}

val shadow: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

modUtils.enableMixins(
    "org.spongepowered:mixin:${project.properties["mixinVersion"]}",
    "mixins.dj2addons.refmap.json"
)

val mixinextras: String = project.properties["mixinextras"].toString()
dependencies {
    implementation(project(mapOf(
        "path" to ":coremod",
        "configuration" to "archives"
    )))
    
    shadow(project(mapOf(
        "path" to ":coremod",
        "configuration" to "reobfElements"
    )))
    shadow(mixinextras)
    
    implementation("CraftTweaker2:CraftTweaker2-MC1120-Main:1.12-+")
    
    implementation("mezz.jei:jei_1.12.2:4.16.1.302")
    
    implementation(rfg.deobf("curse.maven:ActuallyAdditions-228404:3117927"))
    compileOnly(rfg.deobf("curse.maven:DraconicEvolution-223565:3431261"))
    
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
    
//	compileOnly(
//			rfg.deobf("curse.maven:MysticalAgriculture-246640:2704562")
//	)
    
    compileOnly(rfg.deobf("curse.maven:MysticalAgriculture-246640:2704562"))
    
    compileOnly(rfg.deobf("curse.maven:MoreTweaker-336569:3226142"))
    compileOnly(rfg.deobf("curse.maven:MTLib-253211:3308160"))
    
    
    compileOnly(rfg.deobf("curse.maven:AppleSkin-248787:2987247"))
    
    compileOnly(rfg.deobf("curse.maven:ModularMachinery-270790:2713714"))
    compileOnly(rfg.deobf("curse.maven:ModularMagic-324318:2737623"))
    
    compileOnly(rfg.deobf("curse.maven:Thaumcraft-223628:2629023"))
    
    compileOnly(rfg.deobf("curse.maven:Aether-255308:3280119"))
    
    
    compileOnly(rfg.deobf("curse.maven:PackagedAuto-308380:3614585"))
    
    
    compileOnly(rfg.deobf("curse.maven:ThermalExpansion-69163:2926431"))
    compileOnly(rfg.deobf("curse.maven:ThermalFoundation-222880:2926428"))
    compileOnly(rfg.deobf("curse.maven:CoFHCore-69162:2920433"))

    compileOnly(
        rfg.deobf("curse.maven:Mekanism-268560:2835175")
    )
    
    compileOnly(rfg.deobf("curse.maven:RadixCore-77286:2483855"))
    
    // Dev Tools
    testImplementation( // For ZenDoc
        "org.reflections:reflections:0.10.2"
    )
}

tasks.jar.configure {
    doLast {
        metaInf {
            from(shadow)
            into("libs")
        }
    }
}


tasks.register<Exec>("injectCoremodJar") {
    commandLine(pythonFile, rootProject.file("python/ModOutput_InjectCoreMod.py"))
}

tasks["build"].finalizedBy("injectCoremodJar") // TODO: make this portable instead of completely hardcoded



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
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    repositories {
        // Example: publishing to the GTNH Maven repository
        maven {
            url = uri("https://nexus.gtnewhorizons.com/repository/releases/")
            credentials {
                username = System.getenv("MAVEN_USER") ?: "NONE"
                password = System.getenv("MAVEN_PASSWORD") ?: "NONE"
            }
        }
    }
}


tasks.processIdeaSettings.configure {
    dependsOn(tasks.injectTags)
}