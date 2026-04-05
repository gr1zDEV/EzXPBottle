# Placeholders

XPBottle includes optional PlaceholderAPI support.

## Dependency Behavior

- PlaceholderAPI is a **soft dependency**.
- XPBottle only registers placeholders when PlaceholderAPI is installed and enabled.

## Registered Placeholders

Identifier: `xpbottle`

| Placeholder | Returns |
|---|---|
| `%xpbottle_total_xp%` | The player's current total XP points (exact raw XP total). |
| `%xpbottle_level%` | The player's current Minecraft level. |

## Offline Behavior

When PlaceholderAPI requests data for an offline player context, XPBottle returns `0` for its placeholders.
