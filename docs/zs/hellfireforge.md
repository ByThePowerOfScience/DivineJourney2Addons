
### Class

```zenscript
import dj2addons.bloodmagic.HellfireForge;
```

#### Methods

```zenscript
void setCraftingTicksRequired(
  int ticksRequired // Number of ticks taken to craft any given item.
);
```

Sets the crafting speed for all Hellfire Forge crafts. Default is 100.

---


```zenscript
void setWorldWillTransferRate(
  double transferRate // Souls per tick.
);
```

Sets how many souls are transferred from the given chunk to the table's soul gem per tick. Default is 1.

---


```zenscript
void setCraftWithAllWillTypes(
  boolean enabled // True to enable.
);
```

Allows any Will type to be used in crafting in place of Raw. Default is false.

---

