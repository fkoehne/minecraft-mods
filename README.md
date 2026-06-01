# Funny Recipes Mod

A demo Fabric mod for Minecraft 1.21.1+ (tested on 26.1.2) adding two joke crafting recipes.

## Recipes

| Name | Ingredients | Result |
|------|-------------|--------|
| Peasant's Diamond | 9x Dirt (shapeless, fill entire grid) | 1x Diamond |
| Bread Sword | Bread / Bread / Stick (sword shape) | Diamond Sword |

## Build

```bat
.\gradlew.bat build
```

Output: `build\libs\funnyrecipes-1.0.0.jar`

Copy that jar to `%APPDATA%\.minecraft\mods\` alongside Fabric API, then launch the Fabric profile.

## Logs

[latest.log](file:///C:/Users/kof/AppData/Roaming/.minecraft/logs/latest.log)

---

## Learnings & Assumptions

### Java version
- **Assumption:** Fabric Loader targets Java 21. The system had Java 25 (Microsoft OpenJDK 25.0.3), which is fine for running Minecraft but creates toolchain friction during the build.
- **Learning:** Gradle 8.x ships with Groovy 3.x, which cannot parse class files compiled for Java 25 (class file major version 69). This causes a hard `BUG! Unsupported class file major version 69` error at configuration time — reproducible on Gradle 8.8, 8.14, and 8.14.3, all of which fail identically.
- **Fix:** Gradle 9.0.0 ships with Groovy 4.x, which handles Java 25 class files. Bumping the wrapper to `gradle-9.0.0-bin.zip` resolved this.

### Fabric Loom version
- **Assumption:** Loom `1.7-SNAPSHOT` is the documented version for Minecraft 1.21.1.
- **Learning:** Loom 1.7 calls `Problems.forNamespace()` from the Gradle Problems API, which changed incompatibly in Gradle 9. Upgrading to `1.9-SNAPSHOT` fixed this. Loom version is not hard-locked to a Minecraft version and can be bumped independently.

### Recipe data format (changed twice)
- Since 1.20.5 the recipe result format changed from `{"item": "..."}` to `{"id": "..."}`. Minecraft 1.21.1 requires the new `id` key; the old key silently breaks the recipe.
- Since 1.20.5 the data path also changed from `data/<namespace>/recipes/` to `data/<namespace>/recipe/` (singular). The new singular path is used here.
- **In Minecraft 26.1.2** the ingredient format changed again: `{"item": "minecraft:dirt"}` is no longer valid. Ingredients are now plain strings: `"minecraft:dirt"`. This applies to both shaped (`key` values) and shapeless (`ingredients` entries) recipes. The result format (`{"id": "..."}`) was not affected.

### Fabric API dependency
- The mod declares `"fabric-api": "*"` in `fabric.mod.json` but uses no Fabric API code — recipes are pure data-driven JSON. The dependency is declared so Fabric Loader does not print a misleading warning and because Iris users already have Fabric API installed anyway.
- If Fabric API is absent at runtime, Fabric Loader refuses to load the mod with a missing-dependency error.

### Gradle wrapper jar
- No Gradle was pre-installed on the machine. `gradle-wrapper.jar` was downloaded from the Fabric example mod repository on GitHub (`FabricMC/fabric-example-mod`, branch `1.21.1`), which contains a standard unmodified Gradle wrapper binary.

### Java compilation target
- `build.gradle` sets `options.release = 21` so the compiled mod bytecode targets Java 21 even though the build JDK is Java 25. This ensures the mod runs on any Java 21+ installation, which is the minimum Minecraft requires.
