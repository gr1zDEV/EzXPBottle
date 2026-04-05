# Commands

Primary command: `/xpbottle` (alias: `/xpb`).

## Command Reference

| Command | Permission | Description |
|---|---|---|
| `/xpbottle` | `xpbottle.use` | Opens the XPBottle GUI. Players only. |
| `/xpbottle help` | *(none)* | Shows help lines from `messages.yml`. |
| `/xpbottle withdraw <amount>` | `xpbottle.withdraw` | Withdraws exact XP into one custom bottle. Players only. |
| `/xpbottle give <player> <amount>` | `xpbottle.give` | Gives one custom bottle containing exact XP to an online player. |
| `/xpbottle reload` | `xpbottle.reload` | Reloads `config.yml`, `gui.yml`, `item.yml`, and `messages.yml`. |

## Syntax

```text
/xpbottle
/xpbottle help
/xpbottle withdraw <amount>
/xpbottle give <player> <amount>
/xpbottle reload
```

## Usage Examples

```text
/xpbottle
/xpbottle withdraw 250
/xpbottle give Notch 1000
/xpbottle reload
```

## Validation Notes

- `amount` must be a positive integer.
- `withdraw` checks that the player has enough total XP first.
- `give` targets online players using exact name lookup.
- Unknown subcommands return the configured unknown-subcommand message.

For access control details, see [Permissions](permissions.md).
