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
