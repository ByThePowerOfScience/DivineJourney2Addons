If you're reading this because you're contributing to this project:
- First off: THANK YOU! That's such a big deal to me, and I really appreciate the help!
- Second: This project is very janky (which is sadly _necessary_), so you'll need to learn some things about it before you start contributing.
  - (NOTE: I have spent over ***200 HOURS*** working on this stupid buildscript, and every piece of jank is only there because I literally could not figure out any way to do it otherwise.)

# Buildscript
1. "Why isn't the coremod separate from the mod jar? Isn't that recommended by Forge?"
   * Sadly, the number of issues I had when trying to stick to this made me finally throw in the towel after giving it far more effort than it deserved. Issues included:
     * The coremod not being detected,
     * The coremod stopping the mod from being detected,
     * Only _half_ of the coremod module's classes being detectable by the classloader somehow,
     * Forge's build system combining the jars anyway somehow,
     * Class circularity errors, and
     * The coremod stopping all of my registry events from firing on a consistently-inconsistent basis.
2. The `/python` directory contains scripts to do what Gradle - for some bizarre reason - can't, including *shadowing the coremod jar*, which is just stupid that it can't figure that out. (This is another issue solved by the combined jar.)
   A lot of paths are hardcoded as a result, so you may need to fix them for your own build to function.
    - First, make sure to change the `project.ext.pythonFile` property in [:base:build.gradle](./build.gradle.kts) to point to your Python executable. 
        - If you're using IDEA, to make it resolve the packages properly you'll need to:
          1. Go to "Project Settings > Modules > Add > Create",
          2. Add a "Python" module,
          3. Set the `/python` dir as its sources root,
          4. Set the "Module SDK" to be your venv.
    - See [this script](./python/DJ2A_CommonBuildLogic.py) if you need to change relative paths for some reason, like if you change where the subprojects are located in the project directory.
    - See [this script](./python/ModOutput_ShadowCoreMod.py) for the shadow logic. It gets executed via the `:mod:jankShadow` task.
      - NOTE: it currently only targets the LATEST jar versions it can find, not necessarily the correct ones.  If you're having trouble, make sure the `/build/libs` folder of both subprojects is empty and then rebuild.
    - There are a few other helper scripts that are included in the folder, such as [UpdateCFModpack.py](python/UpdateCFModpack.py), which is _entirely_ hardcoded, but it just copies the latest jar version to the mods folder of my personal modpack so I can test in a real Minecraft runtime environment.

# Structure
- The @Mod class is [here](src/main/java/btpos/dj2addons/core/DJ2Addons.java), not in the root like normal.
  - This is because as another blessing of the coremod jank, your Mod class cannot be detected unless it's in the same package as your coremod. 
  - Since this coremod _has_ to be an ASM exclusion for it to load properly - meaning it has to be in a subpackage to prevent the entire mod being blacklisted - the mod has to be there too.
- This project follows a "mixin, impl, api" directory structure, similar to the Fabric API.  For a given module, Mixins go in the `mixin` package, internal components go in the `impl` package, and components that are meant to be used outside the application go in the `api` package.
- Because of the nature of the project, the vast majority of this mod are Mixins.  However, I tried my best to move the actual _logic_ to methods **outside** the protected `mixin` package, specifically so other modders can Mixin into my Mixins to make a janked-out extravaganza. (AKA make sure my meddling doesn't stop other people from doing _their_ meddling.)
  - If there's stuff that's bad in my Mixins, check out the fabulous [MixinSquared](https://github.com/Bawnorton/MixinSquared) library by Bawnorton. It's just awesome.

# Explaining Coremod Jank
- [Thaumcraft classes](src/main/java/thaumcraft) are included in the jar because we need to reference them _long_ before the mod gets put on the classloader.  Since I doubt TC6 is getting updated anytime soon, it's probably gonna stay like this.
- Speaking of, one of the APIs is adding blocks as Infusion Stabilizers. Because TC uses Interfaces instead of Capabilities, we have to monkey-patch the [InfusionStabilizerDelegate](src/main/java/btpos/dj2addons/asmducks/InfusionStabilizerDelegateDuck.java) interface on using ASM, which is done [here](src/main/java/btpos/dj2addons/core/asm/api/thaumcraft/infusionstabilizers/InfusionStabilizerClassTransformer.java).
  - Because this happens long before CraftTweaker or any block names are initialized, we have to get the class names themselves and put them into a [config](src/main/java/btpos/dj2addons/config/CfgAPI.java).
- [DJ2AMixinConfig](src/main/java/btpos/dj2addons/core/DJ2AMixinConfig.java):
  - Why is `shouldApplyMixin` like that? 
    - Despite being a completely normal Mixin, TickProfiler causes [MWorld](src/main/java/btpos/dj2addons/initmixins/patches/minecraft/aether_legacy/aerogel/MWorld.java) (a patch to stop automating Aether aerogel from crashing the server) to only find the class `amu` (the Notch name of `World`) when trying to apply, which makes the whole program crash.
    - After a few dozen hours of troubleshooting, I couldn't even remotely find the cause, so I abandoned the idea and just disabled the patch if TickProfiler was present.
      - Tried: setting an alias in the Mixin target, using a custom refmap, implementing the patches through raw ASM, hijacking TickProfiler to stop it from applying its patch and applying it manually, and many more.

