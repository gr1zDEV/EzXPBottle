# XPBottle

XPBottle is a production-ready Paper/Folia-compatible Minecraft plugin by **EzInnovations**.
It lets players withdraw exact raw XP points into custom XP bottle items and redeem those exact values later.

## Features

- `/xpbottle` GUI with configurable title, size, filler, info, close button, presets, and withdraw-all button.
- Exact XP handling (raw point math) for withdraw and redeem.
- Custom XP bottles identified with `PersistentDataContainer` keys (not lore-only).
- Right-click redeem behavior that cancels vanilla bottle use only for plugin bottles, with per-hand safety.
- Optional shift-right-click redeem-all-in-hand behavior (clicked hand only, no dual-hand double-redeem).
- Admin give command for preloaded custom bottles.
- Safe handling for invalid/corrupt bottle metadata and inventory-full drop behavior.
- Config split across `config.yml`, `gui.yml`, `item.yml`, `messages.yml`.
- PlaceholderAPI softdepend placeholders:
  - `%xpbottle_total_xp%`
  - `%xpbottle_level%`


## Documentation

Full project documentation is available in the [`docs/`](docs/) folder.

### Docs Navigation

- [Docs Home](docs/index.md)
- [Installation](docs/installation.md)
- [Commands](docs/commands.md)
- [Permissions](docs/permissions.md)
- [Configuration](docs/configuration.md)
- [GUI Guide](docs/gui.md)
- [XP Bottles](docs/xp-bottles.md)
- [Placeholders](docs/placeholders.md)
- [FAQ](docs/faq.md)
- [Developer Notes](docs/developer-notes.md)

## Commands

- `/xpbottle` - Open GUI.
- `/xpbottle withdraw <amount>` - Withdraw exact raw XP amount into one bottle.
- `/xpbottle give <player> <amount>` - Give player one custom bottle containing exact XP.
- `/xpbottle reload` - Reload plugin config files.
- `/xpbottle help` - Show help.

## Permissions

- `xpbottle.use`
- `xpbottle.withdraw`
- `xpbottle.reload`
- `xpbottle.give`
- `xpbottle.redeem`

## Placeholders

- `%xpbottle_total_xp%`
- `%xpbottle_level%`

## Configuration Files

- `config.yml` - General behavior, redeem options, sounds, GUI refresh behavior.
- `gui.yml` - GUI layout and button/preset setup.
- `item.yml` - Custom XP bottle visuals and metadata options.
- `messages.yml` - All user-facing messages.

## Build

Requirements:
- Java 21+
- Maven 3.8+

Build command:

```bash
mvn clean package
```

Output jar:
- `target/XPBottle-<version>.jar`

## Author

- **EzInnovations**
