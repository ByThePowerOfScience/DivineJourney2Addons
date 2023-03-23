
### Class

```zenscript
import dj2addons.extrautils2.Mills;
```

Handles Grid Power (GP) generator (mill) tweaks.

Must be run with the preinit loader, specified with `#loader preinit` at the top of the file.


#### Methods

```zenscript
void setScaling(
  string arg0,
  Map arg1,   
);
```

Sets mill power scaling.
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
