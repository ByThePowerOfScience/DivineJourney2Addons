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
```