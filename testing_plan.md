# Testing requirements:
#### Setup
1. Copy test world to run/saves
2. Copy test.zs to run/scripts
3. Inject hook to know when the game has fully loaded and automatically load up testing world?
4. Run game with ALL mods enabled
5. Make a logger readout that logs the state of all tests? or just use JUnit?
6. Check that DJ2Addons is loaded, DJ2AddonsCore is loaded, and \<something\>
7. Run two checks on the world or just one? one at world load and one at ~500 ticks after, or just do the second one?

#### World checking for:
* Making sure datafixers change blocks/tiles properly with correct metadata and NBT,
  * Place blocks in defined positions and reference their hardcoded position
* Making sure recipes can work properly and machines aren't borked,
  * Bewitchment - Witches Oven, get minrep
  * 
* Checking behavior
  * Hellfire Forge input into output slot
    * Wire a hopper to try to insert into the output slot, code check if item exists in the output slot

#### Code checks
* Ensure mods are loaded properly (dj2addons and dj2addons core)
* Ensure all Mixin configs execute (can use [DJ2AMixinConfig] to validate that each mixin has been encountered)
* Registering items through CT API
  * validate by querying registry
* Validate state of altered objects
  * e.g. hellfire forge - crafting time