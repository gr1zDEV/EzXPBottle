# Exact XP Bottles

This feature stores and redeems exact raw XP values in custom bottle items.

## Requirements

- Permission `xpbottle.withdraw` for withdraw operations.
- Permission `xpbottle.redeem` for right-click redeem operations.
- Valid `item.yml` and `messages.yml` formatting.

## What the Feature Does

- Creates plugin-owned XP bottle items containing an exact integer XP value.
- Prevents vanilla throwable XP behavior for plugin bottles.
- Redeems bottle XP directly to player total XP.

## Config Keys

- `item.material`
- `item.glow`
- `item.custom-model-data`
- `item.name`
- `item.lore`
- `redeem.shift-right-click-redeem-all`
- `sounds.enabled`
- `sounds.withdraw`
- `sounds.redeem`
- `gui.refresh-on-withdraw`

## Runtime Conditions

- Right-click with plugin bottle in hand triggers redeem path.
- If player is sneaking and `redeem.shift-right-click-redeem-all: true`, entire matching stack in clicked hand is redeemed.
- If bottle is invalid/corrupt, no XP is granted; plugin can consume item and notify player.
- If inventory is full during withdraw/give, bottle is dropped at player location with message `messages.inventory-full-drop`.

## Reload Behavior

- `/xpbottle reload` reloads config and message files.
- Updated key values apply to future GUI opens, bottle creation, and messaging.
