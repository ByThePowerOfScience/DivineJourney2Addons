import dj2addons.botania.Brews;
import crafttweaker.item.IItemStack;

var meats as IItemStack[] = [<minecraft:cooked_beef>,
                         	<minecraft:nether_wart>];

Brews.addBrewRecipe(
	Brews.addBrew(
		"luck",
		200,
		true,
		false,
		1,
		1800,
		<potion:minecraft:luck>),
	meats);

Brews.addBrewRecipe(
	Brews.addBrew(
		"saturation",
		200,
		true,
		false,
		1,
		1800,
		<potion:minecraft:saturation>),
	<minecraft:sand>);

