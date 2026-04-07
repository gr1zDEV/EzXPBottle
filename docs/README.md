# XPBottle Documentation

XPBottle is a Paper/Folia plugin that lets players withdraw exact raw XP into custom bottles and redeem that exact XP later.

## Features

- Exact raw XP storage and redemption (not approximate level math).
- GUI-based withdraw workflow on `/xpbottle` (alias: `/xpb`).
- Command-based withdraw, admin give, help, and reload subcommands.
- Plugin bottle validation using `PersistentDataContainer` metadata.
- Optional shift-right-click stack redeem via `redeem.shift-right-click-redeem-all`.
- Optional PlaceholderAPI placeholders: `%xpbottle_total_xp%`, `%xpbottle_level%`.
- Split configuration files: `config.yml`, `gui.yml`, `item.yml`, `messages.yml`.

## Downloads

| Channel | Artifact | Notes |
| --- | --- | --- |
| Build from source | `target/XPBottle-<version>.jar` | Build with `mvn clean package`. |
| Release package | `<replace-with-release-link>` | Add your public release URL before publishing. |

> Note: Confirm this behavior against the plugin source/config before publishing.

## Requirements

| Requirement | Value |
| --- | --- |
| Server software | Paper or Folia |
| Minecraft API target | `1.21` (`api-version`) |
| Java | `21+` |
| Main command | `/xpbottle` |
| Aliases | `/xpb` |
| Optional dependency | PlaceholderAPI |

## Documentation Map

- [Installation](getting-started/installation.md)
- [Commands](usage/commands.md)
- [Permissions](usage/permissions.md)
- [Troubleshooting](usage/troubleshooting.md)
- [Exact XP Bottles](features/exact-xp-bottles.md)
- [PlaceholderAPI Integration](features/placeholderapi-integration.md)
- [FAQ](faq.md)
- [Changelog](changelog.md)
