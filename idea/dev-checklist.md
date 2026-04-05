# XPBottle Dev Checklist

Use this checklist whenever you change code/config so you can verify every command and core in-game behavior still works.

---

## 1) Local Build & Artifact Checks

- [ ] `mvn clean package`
- [ ] Confirm build succeeds with no compile errors.
- [ ] Confirm jar exists in `target/` (example: `XPBottle-<version>.jar`).

Optional quick commands:

```bash
mvn clean package
ls -lh target/XPBottle-*.jar
```

---

## 2) Test Server Setup

- [ ] Run on **Paper** or **Folia** with Java 21+.
- [ ] Copy built jar to `plugins/`.
- [ ] Start server and confirm plugin enables without errors.
- [ ] Confirm files generate in `plugins/XPBottle/`:
  - [ ] `config.yml`
  - [ ] `gui.yml`
  - [ ] `item.yml`
  - [ ] `messages.yml`

Recommended startup checks:

- [ ] No severe stack traces during enable.
- [ ] Console shows plugin version and successful load.

---

## 3) Command Coverage (Everything)

> Test with one normal player account and one OP/admin account.

### A) Base command + help

- [ ] `/xpbottle` opens GUI (player only).
- [ ] `/xpb` alias also opens GUI.
- [ ] `/xpbottle help` prints configured help lines.

### B) Withdraw command

- [ ] `/xpbottle withdraw 1` works with enough XP.
- [ ] `/xpbottle withdraw <valid amount>` removes exact XP and gives one custom bottle.
- [ ] `/xpbottle withdraw <amount>` with not enough XP shows correct failure message.
- [ ] `/xpbottle withdraw 0` is rejected.
- [ ] `/xpbottle withdraw -5` is rejected.
- [ ] `/xpbottle withdraw abc` is rejected.

### C) Give command (admin)

- [ ] `/xpbottle give <onlinePlayer> 1` gives one valid custom bottle.
- [ ] `/xpbottle give <onlinePlayer> <valid amount>` stores exact XP on bottle.
- [ ] `/xpbottle give <offlinePlayer> <amount>` fails with expected message.
- [ ] `/xpbottle give <player> 0` is rejected.
- [ ] `/xpbottle give <player> -5` is rejected.
- [ ] `/xpbottle give <player> abc` is rejected.

### D) Reload command

- [ ] `/xpbottle reload` succeeds for admin.
- [ ] Edit one value in each config file and confirm reload applies changes:
  - [ ] `config.yml`
  - [ ] `gui.yml`
  - [ ] `item.yml`
  - [ ] `messages.yml`

### E) Unknown subcommand

- [ ] `/xpbottle somethinginvalid` shows unknown-subcommand message.

### F) Tab completion sanity

- [ ] With permissions, tab includes: `withdraw`, `give`, `reload`.
- [ ] Without each permission, corresponding subcommand is hidden.

---

## 4) Permission Checks

Validate each node actually gates behavior:

- [ ] `xpbottle.use` required for `/xpbottle` GUI.
- [ ] `xpbottle.withdraw` required for `/xpbottle withdraw`.
- [ ] `xpbottle.give` required for `/xpbottle give`.
- [ ] `xpbottle.reload` required for `/xpbottle reload`.
- [ ] `xpbottle.redeem` required to redeem custom bottles by right-click.

Negative tests:

- [ ] Remove each permission and confirm proper no-permission behavior.

---

## 5) GUI Behavior Checks

Open GUI via `/xpbottle` and verify:

- [ ] Title, size, and filler render correctly.
- [ ] Info item appears and updates placeholders (`%total_xp%`, `%level%`).
- [ ] Preset buttons exist in expected slots.
- [ ] Clicking preset withdraws exact configured amount.
- [ ] Withdraw-all button behavior matches mode:
  - [ ] `disabled`
  - [ ] `single_bottle`
- [ ] Close button closes inventory.
- [ ] Top inventory click/drag is blocked (anti-item-theft).

Refresh behavior:

- [ ] If GUI auto-refresh is enabled, content refreshes after successful withdraw.

Slot/validation edge tests (if you changed `gui.yml`):

- [ ] Invalid slot entries are safely ignored.
- [ ] Non-positive preset amounts are skipped.

---

## 6) XP Bottle Item + Redeem Logic

### A) Standard redeem

- [ ] Right-click plugin bottle in main hand redeems exact XP.
- [ ] Right-click plugin bottle in offhand redeems exact XP.
- [ ] Vanilla throw is blocked for plugin bottle items.

### B) Redeem-all behavior

- [ ] With `redeem.shift-right-click-redeem-all: true`, sneaking redeems full stack in clicked hand.
- [ ] Ensure only clicked hand is redeemed (no double hand redeem).

### C) Inventory-full withdraw behavior

- [ ] Fill inventory, then withdraw/give a bottle.
- [ ] Bottle drops at player location and message indicates inventory-full handling.

### D) Invalid/corrupt bottle behavior

- [ ] Test plugin-marked bottle with bad/missing metadata.
- [ ] Redeem is blocked safely.
- [ ] One invalid bottle is consumed as expected.

### E) Spam protection / cooldown behavior

- [ ] Rapid right-click spam does not duplicate XP redemption.
- [ ] No double redeem from packet/event spam patterns.

---

## 7) PlaceholderAPI (Optional)

If PlaceholderAPI is installed:

- [ ] `%xpbottle_total_xp%` returns exact total XP.
- [ ] `%xpbottle_level%` returns current level.

If PlaceholderAPI is not installed:

- [ ] XPBottle still loads and runs normally.

---

## 8) Regression Smoke Pass (Quick)

Run this short flow before merging:

- [ ] `/xpbottle withdraw 250`
- [ ] Redeem bottle and confirm XP returns exactly.
- [ ] `/xpbottle give <testPlayer> 1000` and verify target redeem.
- [ ] `/xpbottle reload`
- [ ] Open GUI and use one preset button.
- [ ] Confirm no console errors after all actions.

---

## 9) Release Readiness

- [ ] Update docs/changelog if command behavior or config changed.
- [ ] Rebuild jar from clean state.
- [ ] Keep a copy of test notes (date, server version, Paper/Folia version).

