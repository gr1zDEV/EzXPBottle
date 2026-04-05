# Configuration Overview

XPBottle uses four YAML files in `plugins/XPBottle/`.

- `config.yml`
- `gui.yml`
- `item.yml`
- `messages.yml`

Use `/xpbottle reload` after editing files.

## `config.yml`

Controls core behavior:

- GUI refresh after successful GUI withdraw (`gui.refresh-on-withdraw`)
- Shift-right-click redeem-all toggle (`redeem.shift-right-click-redeem-all`)
- Withdraw/redeem sounds and whether sounds are enabled

Example:

```yaml
gui:
  refresh-on-withdraw: true

redeem:
  shift-right-click-redeem-all: true

sounds:
  enabled: true
  withdraw: ENTITY_EXPERIENCE_ORB_PICKUP
  redeem: ENTITY_PLAYER_LEVELUP
```

## `gui.yml`

Controls `/xpbottle` GUI presentation and behavior:

- Inventory title and size
- Filler item
- Info item (supports `%total_xp%`, `%level%`)
- Close button slot and appearance
- Withdraw-all mode (`disabled` or `single_bottle`)
- Preset withdraw buttons (`amount`, `slot`, material, name, lore)

Example snippet:

```yaml
gui:
  size: 27
  withdraw-all:
    mode: single_bottle
    slot: 22
  preset-amounts:
    amount-100:
      slot: 14
      amount: 100
```

See [GUI Guide](gui.md).

## `item.yml`

Controls custom XP bottle appearance:

- Material
- Display name and lore (`%xp%` placeholder)
- Cosmetic glow effect
- Custom model data

Example:

```yaml
item:
  material: EXPERIENCE_BOTTLE
  glow: true
  custom-model-data: 0
  name: '&bXP Bottle &7(&f%xp% XP&7)'
```

## `messages.yml`

Stores user-facing messages and help text used by commands/listeners:

- Permission and validation errors
- Withdraw/redeem success messages
- Give/reload feedback
- Help lines displayed by `/xpbottle help`

This file is safe to fully localize.
