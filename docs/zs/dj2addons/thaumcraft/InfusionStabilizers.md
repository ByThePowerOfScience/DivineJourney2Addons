### Class

```zenscript
import dj2addons.thaumcraft.InfusionStabilizers;
```

Utilities for blocks that stabilize Thaumcraft's Runic Matrix crafting operations.


#### Static Methods

```zenscript
InfusionStabilizerBuilder newInfusionStabilizer(string className);
```

Start building the stabilization logic for the class you registered in the [config](/docs/other/thaumcraft/InfusionStabilizers.md).




---

### Class

```zenscript
import dj2addons.thaumcraft.InfusionStabilizerBuilder;
```

Builds an infusion stabilizer's logic for a class specified in the config.


#### Instance Methods

```zenscript
BlockBuilder forBlock(IBlockState target);
```

Start building logic for a specific blockstate under this class name.

```zenscript
void build();
```

Register this Infusion Stabilizer logic.



---

### Class

```zenscript
import dj2addons.thaumcraft.InfusionStabilizers.BlockBuilder;
```

Builds the infusion stabilization behavior for a specific blockstate.


#### Instance Methods

```zenscript
BlockBuilder stabilizationAmount(float amount);
```

How much this block stabilizes the infusion on its own.

```zenscript
BlockBuilder hasSymmetryPenalty(
  IBlockStateMatcher predicate // Takes in the opposing blockstate and returns 'true' if it should add a penalty.
);
```

Set logic for symmetry: check if the block opposite it is ok, or if it should add a symmetry penalty to the infusion for not being symmetrical.
Defaults to false.

```zenscript
BlockBuilder symmetryPenalty(float penalty);
```

Set the strength of the symmetry penalty if one is applied.

```zenscript
InfusionStabilizerBuilder endBlock();
```

Finish building this block's logic and return to the main builder.

