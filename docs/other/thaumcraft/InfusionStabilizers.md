Thaumcraft uses Interfaces instead of Capabilities to handle its infusion stabilizers.  Problem is, interfaces have to be patched on EXTREMELY early in the loading phase, _long_ before CraftTweaker or any Forge configs are available for use.

Because of that, we have to use a custom config file for this API.

### Instructions
1. Get the name of the class for the **Block** you wish to turn into an infusion stabilizer.
   * This can be done using `/ct dj2addons info classes` on a block in-game.
2. Add the class name to `config/dj2addons/features.cfg` in thaumcraft/"Infusion Stabilizers: Class Names"
3. Use the [CraftTweaker API](/docs/zs/dj2addons/thaumcraft/InfusionStabilizers.md) to set the infusion stabilizer value.

### Example
In `MCDIR/config/dj2addons/features.cfg`:
```
thaumcraft {
   S:"Infusion Stabilizers: Class Names" <
     net.minecraft.block.BlockDirt
   >
}
```

In `MCDir/scripts/myscript.zs`:
```zenscript
import crafttweaker.block.IBlockState;
import crafttweaker.block.IBlockStateMatcher;
import dj2addons.thaumcraft.InfusionStabilizers;

InfusionStabilizers.newInfusionStabilizer("net.minecraft.block.BlockDirt")
                  .forBlock(<minecraft:dirt:0>)
                     .stabilizationAmount(1.0f) // 1 point stabilization
                     .hasSymmetryPenalty(<minecraft:sand>) // has penalty if opposite sand
                     .symmetryPenalty(2.0f) // gives a 2 point stabilization penalty if opposite sand
                     .endBlock()
                  .build(); // registers logic
```
