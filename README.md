# NoMoreMod-Whitelist

A client-side NeoForge 1.21.1 mod that provides a configurable derived mod and file list when ModWhiteList 2.0.0 requests a client scan.

## Requirements

- Minecraft 1.21.1
- NeoForge 21.1.219 or newer compatible release
- ModWhiteList `1.21.1-2.0.0`
- Cloth Config API 15.0.140 or newer compatible release

## Configuration

Use the Cloth Config screen or edit `config/no_more_mod_whitelist.json`:

```json
{
  "addedMods": [
    "example_mod"
  ],
  "removedMods": [
    "another_mod"
  ]
}
```

Removed mod IDs take priority over added IDs. When possible, the JAR file record belonging to a removed mod is also excluded from the derived file list. The mod always excludes its own ID and JAR record.

## Building

```bash
./gradlew build
```

The compiled JAR is written to `build/libs`.

## GitHub Actions

Run **Manual Build and Release** from the Actions tab:

- `build`: compile the project and upload the JAR as a workflow artifact.
- `release`: compile the project, create a `v<version>` GitHub Release, and attach the JAR.

## License

[MIT](LICENSE)

