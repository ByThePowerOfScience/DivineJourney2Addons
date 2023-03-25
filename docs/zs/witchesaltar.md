
### Class

```zenscript
import dj2addons.bewitchment.WitchesAltar;
```

Adds upgrades to the Witches Altar.


#### Static Methods

```zenscript
void addUpgradeCup(
  IItemStack itemStack, // The OreDict key to add.
  int gain,             // Flat bonus to Magical Power.
  double multiplier,    // Multiplicative multiplier applied to MP.
);
```


```zenscript
void addUpgradeCup(
  IOreDictEntry oreDict, // The OreDict key to add.
  int gain,              // Flat bonus to Magical Power.
  double totalMult,      // Multiplicative multiplier applied to MP.
);
```


```zenscript
void addUpgradePentacle(
  IItemStack itemStack, // The Item to add.
  int bonus,            // Flat bonus to Magical Power.
);
```


```zenscript
void addUpgradePentacle(
  IOreDictEntry oreDict, // The OreDict key to add.
  int bonus,             // Flat bonus to Magical Power.
);
```


```zenscript
void addUpgradeWand(
  IItemStack itemStack, // The Item to add.
  double multBoost,     // Additive multiplier to MP.
);
```


```zenscript
void addUpgradeWand(
  IOreDictEntry oreDict, // The OreDict key to add.
  double multBoost,      // Additive multiplier to MP.
);
```


```zenscript
void addUpgradeSword(
  IItemStack itemStack, // The Item to add.
  double multiplier,    // Additive multiplier to MP.
);
```


```zenscript
void addUpgradeSword(
  IOreDictEntry oreDict, // The OreDict key to add.
  double multiplier,     // Additive multiplier to MP.
);
```


```zenscript
void removeUpgrade(
  IItemStack iItemStack, // 
);
```

Removes altar upgrades matching the item parameter.

```zenscript
void removeUpgrade(
  IOreDictEntry oreDictEntry, // 
);
```

Removes altar upgrades matching an oredict entry.

