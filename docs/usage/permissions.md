# Permissions

XPBottle uses explicit permission checks on GUI open, command subcommands, and redeem actions.

## Permission Nodes

| Node | Default | Used For |
| --- | --- | --- |
| `xpbottle.use` | `true` | Open GUI with `/xpbottle`. |
| `xpbottle.withdraw` | `true` | Run `/xpbottle withdraw <amount>`. |
| `xpbottle.reload` | `op` | Run `/xpbottle reload`. |
| `xpbottle.give` | `op` | Run `/xpbottle give <player> <amount>`. |
| `xpbottle.redeem` | `true` | Redeem plugin XP bottles by right-click. |

## Practical Notes

- Missing node behavior sends `messages.no-permission`.
- `help` has no explicit permission gate in command code.
- If `xpbottle.redeem` is denied, plugin bottle interaction is cancelled and no vanilla throw occurs.
