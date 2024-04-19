### Class

```zenscript
import dj2addons.bewitchment.WitchesAltar;
```

Adds upgrades to the Witches Altar.


#### Static Methods

```zenscript
void addUpgradeCup(
  IItemStack itemStack, // The Item to add. Will be a TileEntityPlacedItem on the Altar.
  int gain,             // Amount added to Recharge Rate.
  double multiplier,    // Multiplier applied to total MP.
);
```


```zenscript
void addUpgradeCup(
  IOreDictEntry oreDict, // The OreDict key to add.
  int gain,              // Amount added to Recharge Rate.
  double multiplier,     // Multiplier applied to total MP.
);
```


```zenscript
void addUpgradeCup(
  IBlock block,      // The Block key to add.
  int gain,          // Amount added to Recharge Rate.
  double multiplier, // Multiplier applied to total MP.
);
```


```zenscript
void addUpgradePentacle(
  IItemStack itemStack, // The Item to add.
  int bonus,            // Amount added to recharge rate.
);
```


```zenscript
void addUpgradePentacle(
  IOreDictEntry oreDict, // The OreDict key to add.
  int bonus,             // Amount added to recharge rate.
);
```


```zenscript
void addUpgradePentacle(
  IBlock block, // The Block to add.
  int bonus,    // Amount added to recharge rate.
);
```


```zenscript
void addUpgradeWand(
  IItemStack itemStack, // The Item to add.
  double bonus,         // Flat bonus to MP.
);
```


```zenscript
void addUpgradeWand(
  IOreDictEntry oreDict, // The OreDict key to add.
  double bonus,          // Flat bonus to MP.
);
```


```zenscript
void addUpgradeWand(
  IBlock block, // The Block to add.
  double bonus, // Flat bonus to MP.
);
```


```zenscript
void addUpgradeSword(
  IItemStack itemStack, // The Item to add.
  double multiplier,    // Flat multiplier to MP.
);
```


```zenscript
void addUpgradeSword(
  IOreDictEntry oreDict, // The OreDict key to add.
  double multiplier,     // Flat multiplier to MP.
);
```


```zenscript
void removeAllUpgrades();
```

Removes all registered altar upgrades.

```zenscript
void addUpgradePentacle(
  IBlock block,      // The Block to add.
  double multiplier, // Flat multiplier to MP.
);
```


```zenscript
void removeUpgrade(iItemStack);
```

Removes altar upgrades matching the item parameter.

```zenscript
void removeUpgrade(oreDictEntry);
```

Removes altar upgrades matching an oredict entry.

```zenscript
void removeUpgrade(block);
```

Removes altar upgrades matching a block state.

