# FAQ

## Why does XPBottle use custom bottles?

XPBottle stores exact values in item metadata so the redeemed XP matches the withdrawn XP precisely.

## Does XPBottle work on Folia?

Yes. XPBottle is designed for Paper and Folia environments.

## Does XPBottle support PlaceholderAPI?

Yes, optionally. If PlaceholderAPI is installed, XPBottle registers `%xpbottle_total_xp%` and `%xpbottle_level%`.

## Why does one bottle give exact XP instead of vanilla random XP?

Because XPBottle cancels vanilla bottle behavior for its own marked items and redeems the exact stored integer XP value.

## What happens if my inventory is full when I withdraw or get a bottle?

XPBottle drops the custom bottle at your feet and sends an inventory-full message.

## Can I change GUI amounts and button layout?

Yes. Edit `gui.yml` (`gui.preset-amounts`, slot values, button materials/names/lore, and withdraw-all settings).

## Can players redeem from offhand?

Yes. Redeem logic uses the hand from the click event and consumes/redeems from that specific hand.

## Can players redeem a whole stack at once?

Yes, if `redeem.shift-right-click-redeem-all` is enabled in `config.yml` and the player is sneaking.
