# Welcome to the documentation section!

This is where all APIs are documented: primarily CraftTweaker extensions, but also some other "proprietary" methods due to the information being required *
way* too early in the Forge loading process for CraftTweaker to load. (This also means that you can technically use those without CraftTweaker! :D)

## Navigation:
* `/docs/include`: Ignore this; all information here is included in other files.
* `/docs/zs`: CraftTweaker documentation
* `/docs/other`: Documentation of non-CraftTweaker configuration methods
* `/docs/api`: Programmer-focused APIs

## Commands:
We also have some commands provided to aid in modpack development.

Commands are executed in-game using `/ct dj2addons [option]`

### Options:
* `hand`: Gives information relevant to this mod about either a) the item in your hand or b) the block the caller is looking at.
* `info [option] (x) (y) (z)`: Gives information about blocks or tile entities. Can use either the block you are looking at or a set of coordinates.
  * `blockmeta`: Prints blockstate metadata, such as which blockstate you're looking at
  * `class`: Prints class information for the target block and its tile entity, such as the declaring class, superclass, and all interfaces the class implements. This information is needed for the non-CraftTweaker APIs.
  * `capabilities`: Lists all capabilities (e.g. inventory or fluid handling) of the target tile entity by side.
  * `dumptile (num superclasses to dump) (x) (y) (z)`: Admin-only. Dumps the target object's state in text form to the CraftTweaker log.
* `bewitchment`: Prints data relevant to Bewitchment CraftTweaker extensions.
* `totemic`: Prints data relevant to Totemic CraftTweaker extensions.
* `extrautils2`: Prints data relevant to Extra Utilities 2 CraftTweaker extensions.
* `mods`: Executes all mod-specific subcommands.