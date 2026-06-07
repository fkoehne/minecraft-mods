# Funny Recipes Mod

A demo Fabric mod for Minecraft 26.1.2 adding two joke crafting recipes and randomized crafting chaos.

## Features

### Joke Recipes

| Name | Ingredients | Result |
|------|-------------|--------|
| Peasant's Diamond | 9x Dirt (shapeless, fill entire grid) | 1x Diamond |
| Bread Sword | Bread / Bread / Stick (sword shape) | Diamond Sword |

### Randomized Crafting

Every crafting action has a **50% chance** to replace the output with a completely random item from the entire item registry. Affects all crafting table recipes — including the joke ones above. The original item count is preserved (craft 4 planks → 4 of something random).

Implemented via a Mixin on `ResultSlot.onTake`.

## Build

```bat
.\gradlew.bat build
```

Output: `build\libs\funnyrecipes-1.0.0.jar`

Copy that jar to `%APPDATA%\.minecraft\mods\` alongside Fabric API, then launch the Fabric profile.

## Logs

You can check the log output here:
[latest.log](file://%APPDATA%/Roaming/.minecraft/logs/latest.log)

---

### Java compilation target
- `build.gradle` sets `options.release = 21` so the compiled mod bytecode targets Java 21 even though the build JDK is Java 25. This ensures the mod runs on any Java 21+ installation, which is the minimum Minecraft requires.
- Requires Gradle 9.4+ and Java 25.

