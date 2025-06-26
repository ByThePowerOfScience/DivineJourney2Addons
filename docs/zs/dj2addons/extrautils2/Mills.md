### Class

```zenscript
import dj2addons.extrautils2.Mills;
```

Handles Grid Power (GP) generator/mill tweaks.

Must be run with the preinit loader, specified with `#loader preinit` at the top of the file.


#### Static Methods

```zenscript
void setScaling(
  string millName,          // The name of the mill to change.
  Map<Float, Float> values, // An associative array of [Grid Power threshold : production percentage]. See example.
);
```

Sets mill power scaling.
Use `/dj2addons extrautils2` in-game to print all mill names.

```zenscript
void setBaseValue(
  string millName, // The name of the mill to change.
  Float value,     // The amount of Grid Power the mill should provide.
);
```

Roughly sets the maximum amount of power that each mill provides.
Note that due to how the Lava and Water mills are calculated, the value provided is a theoretical maximum and will not likely be reached.
Use `/dj2addons extrautils2` in-game to print all mill names.



---

### Example
```zenscript
// Sets the Dragon Egg Mill to this scaling:
//      Progressive Efficiency Loss:
//      0 GP to 100,000 GP: 0%
//      100,000 GP to 125,000 GP: 50%
//      Above 125,000 GP: 75%
Mills.setScaling("DRAGON_EGG", {
	100000.0: 0.5,
	125000.0: 0.25
});
```
