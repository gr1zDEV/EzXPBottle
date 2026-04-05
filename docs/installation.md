# Installation

## Requirements

- Java 17 or newer.
- A supported server implementation:
  - Paper
  - Folia

## Installation Steps

1. Build or download the plugin jar (`XPBottle-<version>.jar`).
2. Stop your server.
3. Put the jar file into your server's `plugins/` folder.
4. Start the server.

On first startup, XPBottle will create its data folder and generate configuration files.

## Generated Files

Inside `plugins/XPBottle/`, the plugin generates:

- `config.yml` (main behavior settings)
- `gui.yml` (GUI layout and buttons)
- `item.yml` (custom bottle appearance)
- `messages.yml` (chat messages and help text)

See [Configuration](configuration.md) for details.

## PlaceholderAPI (Optional)

XPBottle detects PlaceholderAPI automatically if it is installed and enabled.

- If PlaceholderAPI is present, XPBottle registers its placeholders.
- If PlaceholderAPI is not installed, XPBottle still works normally.

See [Placeholders](placeholders.md).
