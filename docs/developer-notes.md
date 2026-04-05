# Developer Notes

Brief notes for contributors extending **XPBottle** by **EzInnovations**.

## Package and Entry Point

- Base package: `com.ezinnovations.xpbottle`
- Main class: `com.ezinnovations.xpbottle.XPBottlePlugin`

## Key Systems

- **Command system**: `XPBottleCommand`
  - Handles `/xpbottle`, `help`, `withdraw`, `give`, `reload`.
- **GUI system**: `XPBottleGuiManager` + `XPBottleGuiListener`
  - Builds config-driven inventory GUI and processes button clicks.
- **Bottle item system**: `XPBottleItemManager`
  - Creates, validates, withdraws, gives, and redeems custom bottles.
  - Uses `PersistentDataContainer` keys for marker/xp/version.
- **Redeem listener**: `PlayerRedeemListener`
  - Intercepts right-click for plugin bottles, resolves hand correctly, applies short redeem cooldown, and supports optional shift-redeem-all.
- **XP math utility**: `XPUtil`
  - Converts and mutates exact raw XP totals.
- **PlaceholderAPI expansion**: `XPBottlePlaceholderExpansion`
  - Registers `%xpbottle_total_xp%` and `%xpbottle_level%` when PlaceholderAPI is available.

## Configuration Loading

`ConfigManager` loads:

- `config.yml` via Bukkit config
- `gui.yml`
- `item.yml`
- `messages.yml`

`/xpbottle reload` calls `XPBottlePlugin#reloadPlugin()` which reloads all configuration files.

## Implementation Notes

- Plugin bottles require valid marker + version + bounded XP metadata.
- Invalid marked bottles are consumed safely during redeem attempts.
- GUI click/drag interactions in the top inventory are cancelled to prevent menu item theft.
