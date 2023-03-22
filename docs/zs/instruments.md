
### Class

```zenscript
import dj2addons.totemic.Instruments;
```

Handles musical instruments in Totemic.

Must be run with the preinit loader, specified with `#loader preinit` at the top of the ZS file.


#### Methods

```zenscript
void modifyMusicValues(
  string instrumentName, // The resource name of the instrument, e.g. "totemic:flute".
  Number baseOutput,     // Sets or scales the base output music of the instrument.
  Number musicMaximum    // Sets or scales the cap on the total music this instrument type can produce.
);
```

Changes the stats of the base Totemic musical instruments.
Using integers will overwrite the original values, but using decimals (e.g. 1.2, 3.0) will multiply the original values by that amount.

---


### Example
```zenscript
#loader preinit
import dj2addons.totemic.Instruments;

// Changes the Eagle Bone Whistle to have 200 base music output and half the original maximum music.
Instruments.modifyMusicValues("totemic:eagle_bone_whistle", 200, 0.5);

// Changes the Flute to have the original base output and 200 maximum music.
Instruments.modifyMusicValues("totemic:flute", 1.0, 200);
```
### Command
Instrument IDs can be listed in-game with the command `/ct dj2addons totemic`.
