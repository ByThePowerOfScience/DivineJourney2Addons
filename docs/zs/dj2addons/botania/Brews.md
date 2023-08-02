### Class

```zenscript
import dj2addons.botania.Brews;
```

#### Static Methods

```zenscript
BrewWrapper newBrew(
  string key,                    // The registry key to be assigned to the Brew.
  int cost,                      // The base mana cost of the brew. Amplified automatically for flasks, etc.
  IPotionEffect[] potionEffects, // A/an array of potion effects.
);
```

Creates a Brew instance and registers its existence with Botania, then returns it.
The key is set to "botania.brews.\<key\>" and the color is taken from the source potion.

```zenscript
BrewWrapper newBrew(
  string key,                    // The registry key to be assigned to the Brew.
  string name,                   // The display name of the Brew. e.g. "Flask of <name>"
  int cost,                      // The base mana cost of the brew. Amplified automatically for flasks, etc.
  int color,                     // The hexadecimal color of the brew.
  IPotionEffect[] potionEffects, // A/an array of potion effects.
);
```

Creates a Brew instance and registers its existence with Botania, then returns it.

```zenscript
void addOutputRestrictedBrewRecipe(
  BrewWrapper brew,               // The Brew instance to register a recipe for.
  IItemStack[] allowedContainers, // The containers that this brew recipe will be allowed for. (e.g. <botania:vial:0> = Managlass Vial, <botania:vial:1> = Alfglass Flask)
  IItemStack[] ingredients,       // An array of item ingredients to set as the recipe.
);
```

Registers the recipe for brew with a restricted set of valid containers.
Use in combination with ModTweaker's `mods.botania.Brew.removeRecipe()` to replace Botania's own brew recipes with output-specific versions.

```zenscript
void addStandardBrewRecipe(
  BrewWrapper brew,         // The Brew instance to register a recipe for.
  IItemStack[] ingredients, // An array of item ingredients to set as the recipe.
);
```

Registers the recipe for a given brew.

```zenscript
void enableWarpWardPendant();
```

Enables the Tainted Blood Pendant of Warp Ward. Only valid if Thaumcraft is installed.




---

### Class

```zenscript
import dj2addons.botania.Brew;
```

#### Instance Methods

```zenscript
BrewWrapper setDisableBloodPendant();
```

Disables the Tainted Blood Pendant recipe for this brew. Returns self.

```zenscript
BrewWrapper setDisableIncenseStick();
```

Disables the Incense Stick recipe for this brew. Returns self.



---

### Example
```zenscript
import dj2addons.botania.Brews;
import dj2addons.botania.Brew;

// Generates a Luck I brew with a mana cost of 200 and a default duration of 123 ticks under the key "botania.brews.luck".
val luckBrew as Brew = Brews.makeBrew("luck", 200, <potion:minecraft:luck>.makePotionEffect(123, 0));

// Disables the incense stick and tainted blood pendant recipes for this brew.
luckBrew.disableIncenseStick().disableBloodPendant();

// Registers the Luck I brew with a recipe.
Brews.addBrewRecipe(luckBrew, [<minecraft:nether_wart>, <minecraft:cooked_beef>]);
```
