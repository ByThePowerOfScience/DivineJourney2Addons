### Example
```zenscript
#loader dj2addons
import dj2addons.totemic.Instruments;

// Changes the Eagle Bone Whistle to have 200 base music output and half the original maximum music.
Instruments.modifyInstrumentValues("totemic:eagle_bone_whistle", 200, 0.5);

// Changes the Flute to have the original base output and 200 maximum music.
Instruments.modifyInstrumentValues("totemic:flute", 1.0, 200);
```
### Command
Instrument IDs can be listed in-game with the command `/ct dj2addons totemic`.