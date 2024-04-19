## Description
Various Mixins and mod-tweaks supporting the Divine Journey 2 modpack. 

All APIs are documented [here](/docs/docs.md).

I wrote this to be fully compatible with other modpacks as well, so please let me know if it doesn't work outside of Divine Journey 2!  Eventually I'd like to make this a full library of bugfixes and APIs for 1.12.2, so any suggestions for mod changes would be very appreciated!

## Non-API Changes:

### Custom
* Added "Saturegen" status effect: an effect similar to Regeneration but it affects Hunger instead of Health.

### Patches
* Aether Legacy
	* Fixed crashes when automating turning lava into Aerogel, specifically if TickProfiler is not installed. (That mod
	  breaks the Mixin required to patch it perfectly, but a cursory patch is still applied.) [[MWorld](mod/src/main/java/btpos/dj2addons/initmixins/patches/minecraft/MWorld.java), [MItemBucket](mod/src/main/java/btpos/dj2addons/initmixins/patches/minecraft/MItemBucket.java), [MAetherEventHandler](mod/src/main/java/btpos/dj2addons/patches/mixin/aether_legacy/MAetherEventHandler.java)]
    * Made the Enchanter correctly consume buckets in recipes. [[MEnchanter](mod/src/main/java/btpos/dj2addons/patches/mixin/aether_legacy/MEnchanter.java)]
* Bewitchment
	* Fixed crashes when crafting with the tool protection poppet in an autocrafter. [[MPoppetHandler](mod/src/main/java/btpos/dj2addons/patches/mixin/bewitchment/MPoppetHandler.java)]
* Blood Magic
	* Restricted items from entering the output slot of the Hellfire Forge. [[MSoulForge](mod/src/main/java/btpos/dj2addons/patches/mixin/bloodmagic/MSoulForge.java)]
* Immersive Engineering:
  * Fixed client-side crashes with some multiblocks. (Thanks [C0D3-M4513R](https://github.com/ByThePowerOfScience/DivineJourney2Addons/pull/9/commits/07f0ce2cf561eb4996bf297216b57131ce1191f3)!)
* Industrial Foregoing
	* Made the Plant Interactor take drops directly from AgriCraft crops instead of dropping the items on the ground. [[MPlantInteractor](mod/src/main/java/btpos/dj2addons/patches/mixin/industrialforegoing/MPlantInteractor.java)]
* Modular Magic
	* Fixed crash when removing a blood orb from an LP supplier before a craft finishes.[[MRequirementLifeEssence](mod/src/main/java/btpos/dj2addons/patches/mixin/modularmagic/MRequirementLifeEssence.java)] (Fixes <a href="https://github.com/Divine-Journey-2/Divine-Journey-2/issues/668">Divine-Journey-2#668</a>.)
* MoreTweaker
	* Changed MT's internal methods to use CraftTweaker's API instead, allowing recipes to respect NBT data. [[MInputs](mod/src/main/java/btpos/dj2addons/patches/mixin/moretweaker/MInputs.java)]
* PackagedAuto
  * Fixed issue with recipes not requiring input items to have the correct NBT. [[MMiscUtil](mod/src/main/java/btpos/dj2addons/patches/mixin/packagedauto/MMiscUtil.java)]
* RFTools
  * Forced RFTools to use NBT-matching ingredients in autocrafting recipes. [[MNBTMatchingRecipe](mod/src/main/java/btpos/dj2addons/patches/mixin/rftools/MNBTMatchingRecipe.java)] (Fixes <a href="https://github.com/Divine-Journey-2/Divine-Journey-2/issues/717">Divine-Journey-2#717</a>.)

### Optimizations
* Actually Additions: Completely overhauled laser relay network handling, saving ~15% of client time for large bases and 1.5% of the total server time.
* Aether: Stopped the accessories handler from destroying memory usage by creating a thousand objects per frame. (Only observed happening once, but now it'll never happen again.)
* AgriCraft: Prevent Crop Sticks from checking neighbors for valid soil if they will never be spread to. (Saves another ~1.5% server time.)
* EnderIO:
  * Streamlined error handling, preventing debilitating lag if errors are constantly thrown.
  * Cached regex pattern in EIO's profiler. (Saves ~0.5% server time.)

### Tweaks
* Extra Utilities 2
  * Made Mechanical Users act with the Thaumcraft research of the person who placed them. [[MTileUse](mod/src/main/java/btpos/dj2addons/tweaks/mixin/extrautils2/MTileUse.java)]
  * Gave Mechanical Users a comparator output. [[MBlockMechanicalUser](mod/src/main/java/btpos/dj2addons/tweaks/mixin/extrautils2/MBlockMechanicalUser.java)]
* Thaumcraft
  * Allowed infusions to output item stacks larger than 1. [[MArcanePedestal](mod/src/main/java/btpos/dj2addons/tweaks/mixin/thaumcraft/MArcanePedestal.java)]
  * Emptying Essentia Transfusers no longer draw from Modular Magic's Aspect Provider blocks. [[MTileEssentiaOutput](mod/src/main/java/btpos/dj2addons/tweaks/mixin/thaumcraft/MTileEssentiaOutput.java)]
