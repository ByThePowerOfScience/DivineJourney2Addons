
### Class

```zenscript
import dj2addons.extremereactors.ReactorInterior;
```

Exposes API for interior blocks/fluids.


#### Methods

```zenscript
void registerBlock(
  IOreDictEntry arg0,
  float arg1,        
  float arg2,        
  float arg3,        
  float arg4,        
);
```

Registers a fluid for use in the reactor's interior as a coolant.

---


```zenscript
void deregisterBlock(
  IOreDictEntry arg0,
);
```

Deregisters a previously-registered valid reactor interior block.

---


```zenscript
void registerFluid(
  ILiquidStack arg0,
  float arg1,       
  float arg2,       
  float arg3,       
  float arg4,       
);
```

Registers a fluid for use in the reactor's interior as a coolant.

---


```zenscript
void deregisterFluid(
  ILiquidStack arg0,
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
