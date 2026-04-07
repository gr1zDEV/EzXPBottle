# Troubleshooting

Use this page for common operator-side issues during setup and runtime.

## What Happens On Command Failure

- `Unknown subcommand` -> sent when subcommand does not match `help|reload|withdraw|give`.
- `invalid-amount` -> sent when amount is non-numeric or `<= 0`.
- `not-enough-xp` -> sent when player raw XP is below requested amount.
- `player-only` -> sent when console uses player-only flow.

## What Happens On Redeem Issues

- If item metadata is invalid for a plugin bottle, XP is not granted.
- Invalid bottle data can be consumed and message `messages.invalid-bottle-data` is sent.
- A short internal cooldown (`75ms`) blocks accidental double-redeems.

## Practical Notes

- If sounds fail, verify `sounds.withdraw` / `sounds.redeem` are valid Bukkit `Sound` enum names.
- If GUI appears malformed, verify `gui.size` and slot indexes in `gui.yml`.
- If PlaceholderAPI values return nothing, confirm PlaceholderAPI is installed and enabled before server start.

> Tip: Run `/xpbottle reload` after YAML edits instead of full restarts while testing.

> Warning: `/xpbottle reload` reloads files, but does not validate semantic correctness of every custom value.
