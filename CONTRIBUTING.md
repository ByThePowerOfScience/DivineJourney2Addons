If you're reading this because you're contributing to this project:
- First off: THANK YOU! That's such a big deal to me, and I really appreciate the help!
- Second: This project is very janky (which is unfortunately NECESSARY), so you'll need to learn some things about it before you start contributing.
  - (NOTE: I have spent over ***200 HOURS*** working on this stupid buildscript, and every piece of jank is only there because I literally could not figure out a way to do it otherwise.)

# ~~Buildscript~~ Outdated, subprojects have been unified
1. The Gradle "project" is actually just two subprojects: `:mod` and `:coremod`.
   - The coremod jar gets moved into the mod jar's "/META-INF/libraries" directory by a Python script after the `:mod:build` task. More on that later.
   - The only tasks you care about are in the `:mod` subproject, and the only outputs you care about are in `/mod/build/libs`. This is because this version of Gradle is kinda dumb.
   - This project MAY support RetroFuturaGradle, an alternative to ForgeGradle made by the GregTech: New Horizons team, but I remember encountering some errors when I tried it in 2024.  If you're from the future, it might be better now, so go ahead and try it.
2. The `/python` directory contains scripts to do what Gradle - for some bizarre reason - can't, including *shadowing the coremod jar*, which is just stupid that it can't do that right.
   A lot of paths are hardcoded as a result, so you may need to fix them for your own build to function.
    - First, make sure to change the `project.ext.pythonFile` property in [:base:build.gradle](./build.gradle) to point to your Python executable. 
        - If you're using IDEA, to make it resolve the packages properly you'll need to:
          1. Go to "Project Settings > Modules > Add > Create",
          2. Add a "Python" module,
          3. Set the `/python` dir as its sources root,
          4. Set the "Module SDK" to be your venv.
    - See [this script](./python/DJ2A_CommonBuildLogic.py) if you need to change relative paths for some reason, like if you change where the subprojects are located in the project directory.
    - See [this script](/python/ModOutput_ShadowCoreMod.py) for the shadow logic. It gets executed via the `:mod:jankShadow` task.
      - NOTE: it currently only targets the LATEST jar versions it can find, not necessarily the correct ones.  If you're having trouble, make sure the `/build/libs` folder of both subprojects is empty and rebuild.
    - There are a few other helper scripts that are included in the folder, such as [UpdateCFModpack.py](python/UpdateCFModpack.py), which is _entirely_ hardcoded, but it just copies the latest jar version to the mods folder so I can test in a real Minecraft runtime environment.

# Structure
- This project follows a "mixin, impl, api" directory structure.  For a given module, Mixins go in the `mixin` package, internal components go in the `impl` package, and interface components go in the `api` package.  If you've used Fabric, it's like that.
- Because of the nature of the project, the vast majority of this mod are Mixins.  However, I tried my best to move the actual _logic_ to methods **outside** the protected `mixin` package, specifically so other modders can Mixin into my Mixins to make a janked-out extravaganza. (AKA make sure my meddling doesn't stop other people from doing _their_ meddling.)
- Mixins are launched using MixinBooter, which means that "init"-phase mixin configs have to be listed in an IEarlyMixinLoader, which _has_ to be your coremod.
    - However, I can't actually move the [initmixins](mod/src/main/java/btpos/dj2addons/initmixins) package to the coremod, because it uses several things that only exist in the mod package.
    - Because it wouldn't let me just have a list of names of configs that don't exist in the coremod jar, this project doesn't _have_ an IEarlyMixinLoader, and instead [mixins.dj2addons.init.json](mod/src/main/resources/mixins.dj2addons.init.json) is activated through the _manifest_ in [:mod:build.gradle](mod/build.gradle).
    - All other Mixins are activated through the list in [LateMixinHolder.java](mod/src/main/java/btpos/dj2addons/LateMixinHolder.java).

# Explaining Coremod Jank
- [Thaumcraft classes](coremod/src/main/java/thaumcraft) are included in the coremod jar because we need to reference them long before the mod gets put on the classloader.  Since I doubt it's getting updated anytime soon, it's gonna stay like this.
- Speaking of, one of the APIs is adding blocks as Infusion Stabilizers. Because TC uses Interfaces instead of Capabilities, we have to monkey-patch the [IInfusionStabiliserExt](coremod/src/main/java/thaumcraft/api/crafting/IInfusionStabiliserExt.java) interface on using ASM, which is done [here](coremod/src/main/java/btpos/dj2addons/asm/api/thaumcraft/infusionstabilizers/InfusionStabilizerClassTransformer.java).
  - Because we're doing stuff so early that not even _Mixin_ is loaded, we have to use [the jankest config possible](coremod/src/main/java/btpos/dj2addons/asm/api/thaumcraft/infusionstabilizers/JankConfig.java), which is activated [here](coremod/src/main/java/btpos/dj2addons/core/DJ2APreStartHook.java).
- [DJ2AMixinConfig](coremod/src/main/java/btpos/dj2addons/core/DJ2AMixinConfig.java):
  - Why is `shouldApplyMixin` like that? 
    - Despite being a completely normal Mixin, TickProfiler causes [MWorld](mod/src/main/java/btpos/dj2addons/initmixins/patches/minecraft/MWorld.java) (a patch to stop automating Aether aerogel from crashing the server) to only find the class `amu` (the Notch name of `World`) when trying to apply, which makes the whole program crash.
    - After a few dozen hours of troubleshooting, I couldn't even find the _cause_, so I abandoned the idea and just disabled the patch if TickProfiler was present.
      - Tried: setting an alias in the Mixin target, using a custom refmap, implementing the patches through raw ASM, hijacking TickProfiler to stop it from applying its patch and applying it manually.

TODO finish