# Permissions

This page lists every permission node used by XPBottle.

## Permission Nodes

| Permission | Default | Purpose |
|---|---:|---|
| `xpbottle.use` | `true` | Allows opening the `/xpbottle` GUI. |
| `xpbottle.withdraw` | `true` | Allows `/xpbottle withdraw <amount>`. |
| `xpbottle.reload` | `op` | Allows `/xpbottle reload`. |
| `xpbottle.give` | `op` | Allows `/xpbottle give <player> <amount>`. |
| `xpbottle.redeem` | `true` | Allows redeeming custom XP bottles by right-clicking. |

## Notes

- `xpbottle.help` is **not** used; `/xpbottle help` is available without a dedicated permission node.
- Tab completion is filtered by permission for `withdraw`, `give`, and `reload` subcommands.
