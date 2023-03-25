
### Class

```zenscript
import dj2addons.Random;
```

Helper object for getting random numbers before world load.


#### Static Methods

```zenscript
CTRandom newRandom();
```

Creates a new Random object with a default seed.

```zenscript
CTRandom newRandom(
  int seed, // 
);
```

Creates a new Random object with a set seed.

#### Instance Methods

```zenscript
int nextInt();
```

Gets the next random integer.

```zenscript
int nextInt(
  int bound, // The maximum number the random int can be.
);
```

Gets the next random integer between 0 and the specified bound.

```zenscript
boolean nextBoolean();
```

Gets the next random boolean.

```zenscript
float nextFloat();
```

Gets the next random float.

