
### Class

```zenscript
import dj2addons.bewitchment.WitchesAltar;
```

Adds upgrades to the Witches Altar.


#### Methods

```zenscript
void addUpgradeCup(
  IItemStack arg0,
  int arg1,       
  double arg2,    
);
```


---


```zenscript
void addUpgradeCup(
  IOreDictEntry arg0,
  int arg1,          
  double arg2,       
);
```


---


```zenscript
void addUpgradePentacle(
  IItemStack arg0,
  int arg1,       
);
```


---


```zenscript
void addUpgradePentacle(
  IOreDictEntry arg0,
  int arg1,          
);
```


---


```zenscript
void addUpgradeWand(
  IItemStack arg0,
  double arg1,    
);
```


---


```zenscript
void addUpgradeWand(
  IOreDictEntry arg0,
  double arg1,       
);
```


---


```zenscript
void addUpgradeSword(
  IItemStack arg0,
  double arg1,    
);
```


---


```zenscript
void addUpgradeSword(
  IOreDictEntry arg0,
  double arg1,       
);
```


---


```zenscript
void removeUpgrade(
  IItemStack arg0,
);
```

Removes altar upgrades matching the item parameter.

---


```zenscript
void removeUpgrade(
  IOreDictEntry arg0,
);
```

Removes altar upgrades matching an oredict entry.

---


```zenscript
void removeUpgradeItem(
  IItemStack arg0,
);
```

Removes altar upgrades matching the default metadata of the given ItemStack parameter. Used if the default method does not work.

---

