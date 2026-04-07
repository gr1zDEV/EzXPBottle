# PlaceholderAPI Integration

XPBottle can register placeholders when PlaceholderAPI is installed and enabled.

## Requirements

- PlaceholderAPI plugin installed on the server.
- XPBottle loaded after/with PlaceholderAPI available.

## What the Feature Does

- Registers `xpbottle` placeholder identifier at startup.
- Exposes player XP values for other plugins (scoreboards, chat, holograms).

## Available Placeholders

| Placeholder | Description |
| --- | --- |
| `%xpbottle_total_xp%` | Player total raw XP based on XPBottle math utility. |
| `%xpbottle_level%` | Current player level. |

## Runtime Conditions

- Expansion registers only if `PlaceholderAPI` is detected during `onEnable`.
- Offline or null player requests return `0`.
- Unknown placeholder params return `null` (handled by PlaceholderAPI chain).

## Practical Notes

- If PlaceholderAPI is missing, XPBottle still works; only placeholders are unavailable.
- Use `softdepend: [PlaceholderAPI]` ordering already defined in `plugin.yml`.

> Note: Confirm this behavior against the plugin source/config before publishing.
