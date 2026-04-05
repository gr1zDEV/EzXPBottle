# Changelog

All notable changes to this project will be documented in this file.

## [1.0.3] - 2026-04-05

### Added
- Added a new `docs/` documentation set for wiki/docs-site use, including installation, commands, permissions, placeholders, configuration, GUI, XP bottle behavior, FAQ, and developer notes pages.
- Added internal Markdown navigation links across documentation pages and a docs landing page at `docs/index.md`.

### Changed
- Updated `README.md` with a docs navigation section linking to the new `docs/` files.

## [1.0.2] - 2026-04-05

### Fixed
- Added anti-spam redeem throttle (per-player, per-hand) to reduce duplicate packet/event redemption bursts.
- Hardened spoof protection by requiring valid item version metadata and bounded stored XP values.
- Invalid/corrupt plugin-marked bottles are now consumed safely instead of being repeatedly reusable for spam attempts.
- Added defensive fallback when item metadata creation fails while building custom bottles.
- Tightened tab-completion visibility by permission to avoid suggesting unauthorized command branches.

## [1.0.1] - 2026-04-05

### Fixed
- Fixed hand-resolution in redeem logic so redemption always consumes from the clicked hand and avoids dual-hand double-redeem edge cases.
- Added safe handling for invalid/corrupt bottle XP data during redeem.
- Fixed GUI filler-click detection so non-filler buttons sharing the same material are no longer blocked.
- Improved exact XP progress calculation by using floor semantics instead of rounding.
- Added explicit command usage/unknown-subcommand messaging for clearer command error handling.
- Added explicit inventory-full behavior messaging when bottles are dropped at the player's feet.
- Added defensive fallback logging for invalid GUI/item materials loaded from config.
- Removed an unused settings manager class.

## [1.0.0] - 2026-04-05

### Added
- Initial production-ready XPBottle release by EzInnovations.
- Paper + Folia-safe architecture with modular package layout.
- `/xpbottle` command suite (`help`, `withdraw`, `give`, `reload`) and GUI opening.
- Fully configurable GUI with presets, info item, filler, close button, and withdraw-all mode.
- Custom XP bottle item system using PersistentDataContainer markers + exact stored XP.
- Right-click redeem flow that restores exact raw XP and blocks vanilla behavior for plugin bottles.
- Optional shift-right-click redeem-all stack behavior.
- Exact XP utility handling across levels and progress.
- PlaceholderAPI integration for `%xpbottle_total_xp%` and `%xpbottle_level%`.
- Full docs and defaults: README, plugin.yml, config.yml, gui.yml, item.yml, messages.yml.
