### Class

```zenscript
import dj2addons.botania.Brews;
```

APIs for handling Botania's brews, including adding and removing recipes, creating new brews, and making recipes that are only valid for specific output containers only.


#### Static Methods

```zenscript
Brew getBrew(
  string key // The key for the brew, e.g. "botania.brews.warpWard". Use "/ct dj2addons brews" in-game to get the keys for all registered brews.
);
```

Gets a registered brew by name.

```zenscript
Brew newBrew(
  string key,                    // The registry key to be assigned to the Brew.
  int cost,                      // The base mana cost of the brew. Amplified automatically for flasks, etc.
  IPotionEffect[] potionEffects, // An array of potion effects.
);
```

Creates a Brew instance and registers its existence with Botania, then returns it.
The key is set to "botania.brews.[key]" and the color is taken from the source potion.

```zenscript
Brew newBrew(
  string key,                    // The registry key to be assigned to the Brew.
  string name,                   // The translation key for the display name of the Brew. e.g. "Flask of <name>"
  int cost,                      // The base mana cost of the brew. Amplified automatically by Botania for flasks, etc.
  int color,                     // The hexadecimal color of the brew.
  IPotionEffect[] potionEffects, // An array of potion effects.
);
```

Creates a Brew instance and registers its existence with Botania, then returns it.

```zenscript
void removeRecipe(
  string key // The key for the brew, e.g. "botania.brews.warpWard". Use "/ct dj2addons brews" in-game to get the keys for all registered brews.
);
```

Remove all recipes for the given brew.

```zenscript
void removeRecipe(
  string key,           // The key for the brew, e.g. "botania.brews.warpWard". Use "/ct dj2addons brews" in-game to get the keys for all registered brews.
  Object[] ingredients, // The set of ItemStacks/OreDict keys of the recipe to remove.
);
```

Removes a registered brew recipe by name and ingredients.

```zenscript
void addStandardBrewRecipe(
  Brew brew,            // The Brew instance to register a recipe for.
  Object[] ingredients, // An array of item ingredients/oredict keys to set as the recipe.
);
```

Registers the recipe for a given brew.

```zenscript
void addOutputRestrictedBrewRecipe(
  Brew brew,                      // The Brew instance to register a recipe for.
  IItemStack[] allowedContainers, // The containers that this brew recipe will be allowed for. (e.g. <botania:vial:0> = Managlass Vial, <botania:vial:1> = Alfglass Flask)
  IItemStack[] ingredients,       // An array of item ingredients/oredict keys to set as the recipe.
);
```

Registers the recipe for brew with a restricted set of valid containers.
Use in combination with ModTweaker's `mods.botania.Brew.removeRecipe()` to replace Botania's own brew recipes with output-specific versions.

```zenscript
void enableWarpWardPendant();
```

Enables the Tainted Blood Pendant of Warp Ward. Only valid if Thaumcraft is installed.




---

### Class

```zenscript
import dj2addons.botania.Brew;
```

Represents a Brew for use in CraftTweaker.


#### Instance Methods

```zenscript
Brew setDisableBloodPendant();
```

Disables the Tainted Blood Pendant recipe for this brew. Returns self.

```zenscript
Brew setDisableIncenseStick();
```

Disables the Incense Stick recipe for this brew. Returns self.



---

### Example
```zenscript
import dj2addons.botania.Brews;
import dj2addons.botania.Brew;

// Generates a Luck I brew with a mana cost of 200 and a default duration of 123 ticks under the key "botania.brews.luck".
val luckBrew as Brew = Brews.newBrew("luck", 200, <potion:minecraft:luck>.makePotionEffect(123, 0));

// Disables the incense stick and tainted blood pendant recipes for this brew.
luckBrew.disableIncenseStick().disableBloodPendant();

// Registers the Luck I brew with a recipe.
Brews.addBrewRecipe(luckBrew, [<minecraft:nether_wart>, <minecraft:cooked_beef>]);


// Enable the Tainted Blood Pendant of Warp Ward
Brews.enableWarpWardPendant();


// Give the Tainted Blood Pendant of Warp Ward a different recipe than the bottled versions:

// Get the Warp Ward brew
val warpWardBrew as Brew = Brews.getByName("botania.brews.warpWard");
// Remove the original recipe
Brews.removeRecipe("botania.brews.warpWard"); 

// Reregister the original recipe for non-pendants
Brews.addOutputRestrictedBrewRecipe( 
	warpWardBrew,
	[<botania:vial:0>, <botania:vial:1>, <botania:incensestick>],
	[<minecraft:nether_wart>, <thaumcraft:amber>, <thaumcraft:salis_mundus>, <thaumcraft:bath_salts>]
);

// Register a more difficult recipe for the pendant specifically
Brews.addOutputRestrictedBrewRecipe( 
	warpWardBrew,
	[<botania:bloodpendant>],
	[<minecraft:nether_star>, <minecraft:nether_wart>, <thaumcraft:amber>, <thaumcraft:salis_mundus>, <thaumcraft:bath_salts>]
);
```
