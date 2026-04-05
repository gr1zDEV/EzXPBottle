# Changelog

All notable changes to this project will be documented in this file.

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
