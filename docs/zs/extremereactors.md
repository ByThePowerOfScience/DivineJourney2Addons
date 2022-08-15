
### Class

```zenscript
import dj2addons.extremereactors.ExtremeReactors;
```

Handles ExtremeReactors tweaks.

Must be run with the DJ2Addons loader, specified with `#loader dj2addons` at the top of the ZS file.


#### Methods

```zenscript
void setMaxEnergyStored(
  long value // The maximum energy stored.
);
```

Sets the maximum energy the reactor can store in its output buffer.

---


### Example
```zenscript
#loader dj2addons
import dj2addons.extremereactors.ExtremeReactors;

// Changes the maximum energy stored in the output buffer to 250.
ExtremeReactors.setMaxEnergyStored(250);
```
