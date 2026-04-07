# Commands

Use `/xpbottle` for GUI access and XP bottle operations.

## Requirements

- Command registration from `plugin.yml`.
- Correct permission nodes per subcommand.

## Commands

| Command | Description | Permission |
| --- | --- | --- |
| `/xpbottle` | Opens the XPBottle GUI for the player. | `xpbottle.use` |
| `/xpbottle help` | Shows help lines from `messages.help-lines`. | None enforced in code |
| `/xpbottle withdraw <amount>` | Withdraws exact raw XP into one custom bottle. | `xpbottle.withdraw` |
| `/xpbottle give <player> <amount>` | Gives target one custom bottle with exact XP. | `xpbottle.give` |
| `/xpbottle reload` | Reloads `config.yml`, `gui.yml`, `item.yml`, and `messages.yml`. | `xpbottle.reload` |

## Practical Notes

- Main alias: `/xpb`.
- `withdraw` and `give` reject non-positive values.
- `/xpbottle` without args is player-only (console gets `messages.player-only`).
- Tab completion suggests:
  - Subcommands by permission.
  - Online player names for `give`.
  - Preset values (`10`, `50`, `100`, `500`) for amount arguments.
