
### Class

```zenscript
import dj2addons.extremereactors.ReactorInterior;
```

Exposes API for interior blocks/fluids.


#### Methods

```zenscript
void registerBlock(
  IOreDictEntry oreDict, // The OreDict tag to register as a valid interior block.
  float absorption,      // How much radiation this material absorbs and converts to heat. 0.0 = none, 1.0 = all.
  float heatEfficiency,  // How efficiently radiation is converted to heat. 0 = no heat, 1 = all heat.
  float moderation,      // How well this material moderates radiation. This is a divisor; should not be below 1.
  float heatConductivity // How well this material conducts heat to other blocks. Use `ReactorInterior.HeatConductivity`.
);
```

Registers a fluid for use in the reactor's interior as a coolant.

---


```zenscript
void deregisterBlock(
  IOreDictEntry oreDict // The oredict entry to remove.
);
```

Deregisters a previously-registered valid reactor interior block.

---


```zenscript
void registerFluid(
  ILiquidStack oreDict,  // The OreDict tag to register as a valid interior block.
  float absorption,      // How much radiation this material absorbs and converts to heat. 0.0 = none, 1.0 = all.
  float heatEfficiency,  // How efficiently radiation is converted to heat. 0 = no heat, 1 = all heat.
  float moderation,      // How well this material moderates radiation. This is a divisor; should not be below 1.
  float heatConductivity // How well this material conducts heat to other blocks. Use `ReactorInterior.HeatConductivity`.
);
```

Registers a fluid for use in the reactor's interior as a coolant.

---


```zenscript
void deregisterFluid(
  ILiquidStack fluid // The fluid to deregister.
);
```

Deregisters a previously-valid coolant fluid.

---


### Example
```zenscript
import dj2addons.extremereactors.ReactorInterior;
import dj2addons.extremereactors.ReactorInterior.HeatConductivity as HeatConductivity;

ReactorInterior.registerBlock(<ore:blockStone>, 0.5, 0.5, 0.5, HeatConductivity.stone);
```

### Class

```zenscript
import dj2addons.extremereactors.HeatConductivity;
```

### Static Properties

```zenscript
HeatConductivity.ambientHeat // 

HeatConductivity.air // 

HeatConductivity.rubber // 

HeatConductivity.water // 

HeatConductivity.stone // 

HeatConductivity.glass // 

HeatConductivity.iron // 

HeatConductivity.copper // 

HeatConductivity.silver // 

HeatConductivity.gold // 

HeatConductivity.emerald // 

HeatConductivity.diamond // 

HeatConductivity.graphene // 

```
