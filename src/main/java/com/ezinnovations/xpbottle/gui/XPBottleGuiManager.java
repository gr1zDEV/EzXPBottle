package com.ezinnovations.xpbottle.gui;

import com.ezinnovations.xpbottle.XPBottlePlugin;
import com.ezinnovations.xpbottle.config.ConfigManager;
import com.ezinnovations.xpbottle.config.MessageManager;
import com.ezinnovations.xpbottle.item.XPBottleItemManager;
import com.ezinnovations.xpbottle.util.TextUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class XPBottleGuiManager {

    public enum WithdrawAllMode {
        DISABLED,
        SINGLE_BOTTLE;

        public static WithdrawAllMode from(String value) {
            if (value == null) {
                return DISABLED;
            }
            return switch (value.toLowerCase()) {
                case "single_bottle", "one_bottle", "all_in_one" -> SINGLE_BOTTLE;
                default -> DISABLED;
            };
        }
    }

    private final XPBottlePlugin plugin;
    private final ConfigManager configManager;
    private final MessageManager messageManager;
    private final XPBottleItemManager itemManager;

    public XPBottleGuiManager(XPBottlePlugin plugin,
                              ConfigManager configManager,
                              MessageManager messageManager,
                              XPBottleItemManager itemManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.messageManager = messageManager;
        this.itemManager = itemManager;
    }

    public void openMainGui(Player player) {
        FileConfiguration gui = configManager.getGuiConfig();
        String title = TextUtil.color(gui.getString("gui.title", "&8XP Bottle"));
        int size = sanitizeSize(gui.getInt("gui.size", 27));

        Inventory inventory = Bukkit.createInventory(new XPBottleGuiHolder(player.getUniqueId()), size, title);

        ItemStack filler = buildSimpleItem("gui.filler", Collections.emptyMap());
        if (filler != null) {
            for (int i = 0; i < size; i++) {
                inventory.setItem(i, filler.clone());
            }
        }

        Map<String, String> playerVars = new HashMap<>();
        playerVars.put("%total_xp%", String.valueOf(itemManager.getTotalXp(player)));
        playerVars.put("%level%", String.valueOf(player.getLevel()));
        inventory.setItem(4, buildSimpleItem("gui.info-item", playerVars));

        ConfigurationSection presets = gui.getConfigurationSection("gui.preset-amounts");
        if (presets != null) {
            for (String key : presets.getKeys(false)) {
                String path = "gui.preset-amounts." + key;
                int slot = gui.getInt(path + ".slot", -1);
                int xpAmount = gui.getInt(path + ".amount", -1);
                if (slot < 0 || slot >= size || xpAmount <= 0) {
                    continue;
                }
                Map<String, String> placeholders = new HashMap<>();
                placeholders.put("%xp%", String.valueOf(xpAmount));
                placeholders.put("%amount%", String.valueOf(xpAmount));
                inventory.setItem(slot, buildSimpleItem(path, placeholders));
            }
        }

        if (WithdrawAllMode.from(gui.getString("gui.withdraw-all.mode", "disabled")) == WithdrawAllMode.SINGLE_BOTTLE) {
            int slot = gui.getInt("gui.withdraw-all.slot", 22);
            if (slot >= 0 && slot < size) {
                inventory.setItem(slot, buildSimpleItem("gui.withdraw-all", Collections.emptyMap()));
            }
        }

        int closeSlot = gui.getInt("gui.close-button.slot", 26);
        if (closeSlot >= 0 && closeSlot < size) {
            inventory.setItem(closeSlot, buildSimpleItem("gui.close-button", Collections.emptyMap()));
        }

        player.openInventory(inventory);
    }

    public boolean handleGuiClick(Player player, int slot, ItemStack clicked) {
        FileConfiguration gui = configManager.getGuiConfig();

        if (slot == gui.getInt("gui.close-button.slot", 26)) {
            player.closeInventory();
            return true;
        }

        if (slot == 4 || isFillerSlot(clicked)) {
            return true;
        }

        WithdrawAllMode mode = WithdrawAllMode.from(gui.getString("gui.withdraw-all.mode", "disabled"));
        if (mode == WithdrawAllMode.SINGLE_BOTTLE && slot == gui.getInt("gui.withdraw-all.slot", 22)) {
            int total = itemManager.getTotalXp(player);
            if (total <= 0) {
                messageManager.send(player, "messages.not-enough-xp");
                return true;
            }
            if (itemManager.withdrawToBottle(player, total)) {
                sendWithdrawMessage(player, total);
                refreshIfEnabled(player);
            }
            return true;
        }

        Optional<Integer> preset = getPresetBySlot(slot);
        if (preset.isEmpty()) {
            return true;
        }

        int amount = preset.get();
        if (itemManager.getTotalXp(player) < amount) {
            messageManager.send(player, "messages.not-enough-xp");
            return true;
        }

        if (itemManager.withdrawToBottle(player, amount)) {
            sendWithdrawMessage(player, amount);
            refreshIfEnabled(player);
        }
        return true;
    }

    private void sendWithdrawMessage(Player player, int amount) {
        messageManager.send(player, "messages.withdrew-xp", Map.of("%xp%", String.valueOf(amount)));
        if (configManager.getMainConfig().getBoolean("sounds.enabled", true)) {
            String soundName = configManager.getMainConfig().getString("sounds.withdraw", "ENTITY_EXPERIENCE_ORB_PICKUP");
            Sound sound = parseSound(soundName);
            if (sound != null) {
                player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
            }
        }
    }

    private void refreshIfEnabled(Player player) {
        if (configManager.getMainConfig().getBoolean("gui.refresh-on-withdraw", true)) {
            openMainGui(player);
        }
    }

    private Optional<Integer> getPresetBySlot(int slot) {
        ConfigurationSection presets = configManager.getGuiConfig().getConfigurationSection("gui.preset-amounts");
        if (presets == null) {
            return Optional.empty();
        }

        for (String key : presets.getKeys(false)) {
            String path = "gui.preset-amounts." + key;
            if (configManager.getGuiConfig().getInt(path + ".slot", -1) == slot) {
                return Optional.of(configManager.getGuiConfig().getInt(path + ".amount", -1));
            }
        }
        return Optional.empty();
    }

    private ItemStack buildSimpleItem(String path, Map<String, String> placeholders) {
        FileConfiguration gui = configManager.getGuiConfig();
        String materialName = gui.getString(path + ".material", "GRAY_STAINED_GLASS_PANE");
        Material material = Material.matchMaterial(materialName);
        if (material == null) {
            material = Material.BARRIER;
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        String name = gui.getString(path + ".name", " ");
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            name = name.replace(entry.getKey(), entry.getValue());
        }
        meta.setDisplayName(TextUtil.color(name));

        if (gui.contains(path + ".lore")) {
            meta.setLore(gui.getStringList(path + ".lore").stream().map(line -> {
                String replaced = line;
                for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                    replaced = replaced.replace(entry.getKey(), entry.getValue());
                }
                return TextUtil.color(replaced);
            }).toList());
        }

        item.setItemMeta(meta);
        return item;
    }

    private boolean isFillerSlot(ItemStack clicked) {
        if (clicked == null || clicked.getType().isAir()) {
            return false;
        }
        String fillerType = configManager.getGuiConfig().getString("gui.filler.material", "GRAY_STAINED_GLASS_PANE");
        Material material = Material.matchMaterial(fillerType);
        return material != null && clicked.getType() == material;
    }

    private int sanitizeSize(int size) {
        if (size < 9) {
            return 9;
        }
        if (size > 54) {
            return 54;
        }
        int remainder = size % 9;
        return remainder == 0 ? size : size + (9 - remainder);
    }

    private Sound parseSound(String soundName) {
        try {
            return Sound.valueOf(soundName);
        } catch (IllegalArgumentException ex) {
            plugin.getLogger().warning("Invalid sound in config: " + soundName);
            return null;
        }
    }
}
