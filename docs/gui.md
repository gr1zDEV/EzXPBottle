# GUI Guide

The `/xpbottle` GUI is a config-driven inventory menu that lets players withdraw XP quickly.

## How It Opens

- Command: `/xpbottle`
- Requires: `xpbottle.use`
- Players only

## Main GUI Elements

### 1) Preset Withdraw Buttons
Defined under `gui.preset-amounts` in `gui.yml`.

Each preset has:

- `slot`
- `amount`
- `material`
- `name`
- `lore`

Clicking a preset withdraws that exact amount into one custom bottle (if the player has enough XP).

### 2) Info Item
By default shown at slot `4`.

Supports placeholders:

- `%total_xp%`
- `%level%`

This is informational and not a functional withdraw action.

### 3) Withdraw-All Button
Configured at `gui.withdraw-all`.

Supported mode values:

- `disabled`: hidden/disabled
- `single_bottle`: converts all current XP into one custom bottle

### 4) Close Button
Configured at `gui.close-button`.

Clicking it closes the menu.

## Layout and Behavior Notes

- GUI size is sanitized to a valid inventory size (9 to 54, rounded to a multiple of 9).
- Invalid slots or non-positive preset amounts are skipped.
- If enabled in `config.yml`, GUI automatically refreshes after successful withdraw.

## Anti-Item-Theft and Interaction Safety

XPBottle cancels clicks and drags in the top GUI inventory so players cannot move GUI components into their inventory.
