### Class

```zenscript
import dj2addons.bewitchment.Rituals;
```

Handles Bewitchment ritual tweaks.

Must be run with the preinit loader, specified with `#loader preinit` at the top of the ZS file.


#### Methods

```zenscript
void removeRitual(
  string name // The name of the ritual to remove. e.g. "biome_shift".
);
```

Removes a Bewitchment ritual by name.

---


### Example
```zenscript
import dj2addons.bewitchment.Rituals;

// Removes the Ritual of Biome Shift.
Rituals.removeRitual("biome_shift");
```
### Command
Ritual IDs can be listed in-game with the command `/ct dj2addons bewitchment`.
