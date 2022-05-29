Various Mixins and mod-tweaks for the purpose of supporting the Divine Journey 2 modpack.  All CraftTweaker functions are documented in the `docs/zs` folder.

Changes:
* Custom
  * Added "Saturegen" status effect: an effect similar to Regeneration but it affects Hunger instead of Health.
* Blood Magic
  * Hellfire Forge
    * Items can no longer be input into the output slot of the forge.
    * Added CraftTweaker methods for crafting time, will transfer rate, and crafting with all will types.
* Bewitchment
  * Added CraftTweaker hook to remove Rituals by name.
  * Added CraftTweaker hook for adding Altar upgrades.
* Industrial Foregoing
  * Made the Plant Interactor take drops directly from AgriCraft crops instead of dropping the items on the ground.
* MoreTweaker
  * Changed MT's internal methods to use CraftTweaker's API instead, allowing recipes to respect NBT data.
* Roots
  * Unending Bowl now checks if the requested liquid type is valid before giving the requester infinite of it. (Automatically disabled in Roots 3.1.5+)
* Botania
  * Added CraftTweaker Brew creation API that allows incense sticks and blood pendants.
* Aether
  * Fixed crashes when automating turning lava into Aerogel (hopefully).
* Totemic
  * Added CraftTweaker hook to change the default Instrument music values.
