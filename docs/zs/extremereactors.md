
### Class

```zenscript
import dj2addons.extremereactors.ExtremeReactors;
```

Handles general ExtremeReactors tweaks.

Must be run with the preinit loader, specified with `#loader preinit` at the top of the ZS file.


#### Methods

```zenscript
void setMaxEnergyStored(
  long arg0,
);
```

Sets the maximum energy the reactor can store in its output buffer.

---


### Example
```zenscript
#loader preinit
import dj2addons.extremereactors.ExtremeReactors;

// Changes the output buffer capacity to 250 RF/Tesla.
ExtremeReactors.setMaxEnergyStored(250);
```
