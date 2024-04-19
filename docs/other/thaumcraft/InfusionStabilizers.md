Thaumcraft uses Interfaces instead of Capabilities to handle its infusion stabilizers.  Problem is, interfaces have to be patched on EXTREMELY early in the loading phase, _long_ before CraftTweaker or any Forge configs are available for use.

Because of that, we have to use a custom config file for this API.

### Instructions
1. Get the name of the class for the **Block** you wish to turn into an infusion stabilizer.
   * This can be done using `/ct dj2addons info classes` on a block in-game.
2. Create the file `<mc dir>\dj2addons\infusion_stabilizers.txt` (or `\overrides\dj2addons\infusion_stabilizers.txt` for a modpack zip).
3. On each line, put the name of the class, then a comma, then the decimal amount of stability it should have.
    * Comments can be included by starting them with `//`.
4. These blocks will now be included as infusion stabilizers. This can be verified using `/ct dj2addons info classes` on the block again in-game.
### Example
```
// infusion_stabilizers.txt:
name.of.OneBlock, 0.5
name.of.AnotherBlock, 2.54
```
