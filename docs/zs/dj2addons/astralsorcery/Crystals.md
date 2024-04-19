### Class

```zenscript
import dj2addons.astralsorcery.Crystals;
```

#### Static Methods

```zenscript
void setStarmetalConversion(
  IItemStack block // The IItemStack to set the block to. Must be a Block type.
);
```

Sets the block that starmetal ore turns into when a celestial crystal is grown on top of it. Throws ClassCastException if `block` is not a Block.

```zenscript
void scaleGrowthTime(
  double scale // Value to multiply the time by.
);
```

Scales the time that celestial crystals take to grow. For example, `scale = 0.5` would halve the time required.

