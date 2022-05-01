Various Mixins and mod-tweaks for the purpose of supporting the Divine Journey 2 modpack.

Changes:
* Blood Magic:
  * Hellfire Forge
    * Tweaks
      * Can no longer input into the output slot of the forge.
    * CraftTweaker Methods (`mods.dj2addons.bloodmagic.HellfireForge`)
      * `setCraftingTicksRequired(int)`: Sets universal crafting time.
      * `setWorldWillTransferRate(double)`: Sets number of souls per tick transferred from the chunk to a gem placed in the hellfire forge.
      * `setCraftWithAllWillTypes(boolean)`: Allows crafting with all non-"Raw" will types, such as Steadfast.
* Bewitchment
  * Rituals
    * Removed Ritual of Biome Shift
  * Witches' Altar
    * CraftTweaker Methods (`mods.dj2addons.bewitchment.WitchesAltar`)
      * `addUpgradeCup(Item/Oredict, int mpBonus, double regenMultiplier)`
      * `addUpgradePentacle(Item/Oredict, int mpBonus)`
      * `addUpgradeSword(Item/Oredict, double mpMultiplier)`
      * `addUpgradeWand(Item/Oredict, double regenFlatMultiplier)`
* Industrial Foregoing
  * Tweaks
    * Made the Plant Interactor take drops directly from AgriCraft crops instead of dropping the items on the ground.
* MoreTweaker
  * Tweaks
    * Changed MT's internal methods to use the CraftTweaker API's implementations instead, allowing recipes to respect NBT data.
* Roots
  * Tweaks
    * Unending Bowl now checks if the requested liquid type is valid before giving the requester infinite of it. (Not needed in Roots 3.1.5+)