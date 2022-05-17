import dj2addons.botania.Brews;
import dj2addons.botania.Brew;
import crafttweaker.item.IItemStack;


var meats as IItemStack[] = [<minecraft:cooked_beef>,
                         	<minecraft:nether_wart>];

val b1 as Brew = Brews.makeBrew(
                 		"luck",
                 		200,
                 		<potion:minecraft:luck>.makePotionEffect(10000, 0));

Brews.addBrewRecipe(
	b1,
	meats);

val b2 as Brew = Brews.makeBrew(
		"saturation",
		200,
		<potion:minecraft:saturation>.makePotionEffect(10000, 0, true, true));

Brews.addBrewRecipe(
	b2,
	<minecraft:sand>);


brewing.addBrew(<minecraft:glass_bottle>, <minecraft:sand>, <minecraft:potion>.withTag({Potion:"saturation"}));

