# Installation

Install XPBottle by copying the plugin jar and verifying config + permissions on first boot.

## Requirements

- Paper or Folia server.
- Java `21+` runtime.
- Built or downloaded `XPBottle-<version>.jar`.
- (Optional) PlaceholderAPI if you need `%xpbottle_total_xp%` or `%xpbottle_level%`.

## Steps

1. Stop the server.
2. Place `XPBottle-<version>.jar` into `/plugins`.
3. Start the server once so XPBottle generates default files:
   - `plugins/XPBottle/config.yml`
   - `plugins/XPBottle/gui.yml`
   - `plugins/XPBottle/item.yml`
   - `plugins/XPBottle/messages.yml`
4. Stop the server (or keep it running and use `/xpbottle reload` after edits).
5. Adjust config values as needed.
6. Start the server and test with `/xpbottle`.

> Warning: If you edit YAML with invalid indentation or tabs, plugin load/reload can fail.

> Tip: Keep `gui.size` as a multiple of 9 to match inventory rows cleanly.

## Updating

1. Stop the server.
2. Replace the old XPBottle jar with the new jar.
3. Start server and check startup logs for migration or key changes.
4. Compare old config files with new defaults, then merge any new keys.
5. Run `/xpbottle reload` after final edits.

## Uninstalling

1. Stop the server.
2. Remove `XPBottle-<version>.jar` from `/plugins`.
3. (Optional) Remove `plugins/XPBottle/` to delete configs and messages.
4. Start the server.

> Warning: Deleting `plugins/XPBottle/` removes all local customization.
