# XP Bottles

XPBottle uses **custom items** to store exact XP values.

These are not standard vanilla random XP bottles.

## How XP Is Stored

Each XPBottle item stores metadata in `PersistentDataContainer`, including:

- Plugin marker key (`is_xpbottle`)
- Stored XP integer (`stored_xp`)
- Item version (`item_version` currently `1`)

The plugin validates this metadata before redeeming.

## Withdraw Behavior

When withdrawing (command or GUI):

1. XPBottle removes the exact requested XP from the player.
2. It creates one custom bottle containing that exact value.
3. If inventory is full, the bottle is dropped at the player's location.

## Redeem Behavior

Right-clicking a custom XPBottle item:

- Cancels vanilla throw behavior for that item.
- Redeems from the **clicked hand** (main hand or offhand).
- Adds the exact stored XP value back to the player.

If `redeem.shift-right-click-redeem-all` is enabled and the player is sneaking, XPBottle redeems the entire stack in that same hand.

## Invalid/Corrupt Bottle Handling

If an item is plugin-marked but has invalid data (missing/invalid version or XP value), XPBottle blocks redeeming and consumes one invalid bottle safely to reduce exploit loops.

## Redeem Rate Protection

A short per-player, per-hand cooldown is applied during redeem handling to reduce duplicate packet/event spam.
