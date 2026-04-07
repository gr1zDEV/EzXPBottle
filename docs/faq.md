# FAQ

## Requirements

- Access to server logs.
- Access to XPBottle config files.

## Common Questions

### Why does `/xpbottle` not open a menu?

- Check player has `xpbottle.use`.
- Verify command is not being executed from console.
- Confirm plugin enabled without startup errors.

### Why can players withdraw but not redeem?

- Check `xpbottle.redeem` permission.
- Ensure players are right-clicking XPBottle custom items, not vanilla bottles.

### Why do bottles drop on the ground?

- Inventory had no available slot during withdraw/give.
- Plugin sends `messages.inventory-full-drop` in this case.

### Do config changes require restart?

- Usually no. Use `/xpbottle reload`.
- Restart is safer after large YAML edits or dependency/plugin updates.

### Is PlaceholderAPI required?

- No.
- It is optional and only needed for `%xpbottle_*%` placeholders.
