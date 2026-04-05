package com.ezinnovations.xpbottle.item;

import com.ezinnovations.xpbottle.XPBottlePlugin;
import com.ezinnovations.xpbottle.config.ConfigManager;
import com.ezinnovations.xpbottle.config.MessageManager;
import com.ezinnovations.xpbottle.util.TextUtil;
import com.ezinnovations.xpbottle.util.XPUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class XPBottleItemManager {

    private static final int MAX_STORED_XP = 1_000_000_000;

    private final XPBottlePlugin plugin;
    private final ConfigManager configManager;
    private final MessageManager messageManager;
    private final NamespacedKey bottleKey;
    private final NamespacedKey xpKey;
    private final NamespacedKey versionKey;

    public XPBottleItemManager(XPBottlePlugin plugin, ConfigManager configManager, MessageManager messageManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.messageManager = messageManager;
        this.bottleKey = new NamespacedKey(plugin, "is_xpbottle");
        this.xpKey = new NamespacedKey(plugin, "stored_xp");
        this.versionKey = new NamespacedKey(plugin, "item_version");
    }

    public ItemStack createBottle(int xpAmount) {
        int safeXpAmount = Math.max(1, Math.min(MAX_STORED_XP, xpAmount));
        Material material = parseMaterial(configManager.getItemConfig().getString("item.material", "EXPERIENCE_BOTTLE"), Material.EXPERIENCE_BOTTLE, "item.material");

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            plugin.getLogger().warning("Failed to create XP bottle item meta. Falling back to vanilla EXPERIENCE_BOTTLE metadata.");
            item = new ItemStack(Material.EXPERIENCE_BOTTLE);
            meta = item.getItemMeta();
            if (meta == null) {
                return item;
            }
        }

        Map<String, String> placeholders = Map.of("%xp%", String.valueOf(safeXpAmount));
        String name = replacePlaceholders(configManager.getItemConfig().getString("item.name", "&bXP Bottle &7(&f%xp%&7)"), placeholders);
        List<String> lore = configManager.getItemConfig().getStringList("item.lore").stream()
            .map(line -> replacePlaceholders(line, placeholders))
            .map(TextUtil::color)
            .toList();

        meta.setDisplayName(TextUtil.color(name));
        meta.setLore(lore);

        if (configManager.getItemConfig().getBoolean("item.glow", true)) {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        int customModelData = configManager.getItemConfig().getInt("item.custom-model-data", 0);
        if (customModelData > 0) {
            meta.setCustomModelData(customModelData);
        }

        meta.getPersistentDataContainer().set(bottleKey, PersistentDataType.BYTE, (byte) 1);
        meta.getPersistentDataContainer().set(xpKey, PersistentDataType.INTEGER, safeXpAmount);
        meta.getPersistentDataContainer().set(versionKey, PersistentDataType.INTEGER, 1);

        item.setItemMeta(meta);
        return item;
    }

    public boolean isPluginBottle(ItemStack item) {
        if (item == null || item.getType().isAir() || !item.hasItemMeta()) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        Byte marker = meta.getPersistentDataContainer().get(bottleKey, PersistentDataType.BYTE);
        return marker != null && marker == (byte) 1;
    }

    public int getStoredXp(ItemStack item) {
        if (!isPluginBottle(item)) {
            return -1;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return -1;
        }

        Integer version = meta.getPersistentDataContainer().get(versionKey, PersistentDataType.INTEGER);
        if (version == null || version != 1) {
            return -1;
        }

        Integer xp = meta.getPersistentDataContainer().get(xpKey, PersistentDataType.INTEGER);
        if (xp == null || xp <= 0 || xp > MAX_STORED_XP) {
            return -1;
        }
        return xp;
    }

    public boolean giveBottle(Player player, int xpAmount) {
        if (xpAmount <= 0) {
            return false;
        }

        ItemStack bottle = createBottle(xpAmount);
        HashMap<Integer, ItemStack> leftovers = player.getInventory().addItem(bottle);
        if (!leftovers.isEmpty()) {
            leftovers.values().forEach(leftover -> player.getWorld().dropItemNaturally(player.getLocation(), leftover));
            messageManager.send(player, "messages.inventory-full-drop");
        }
        return true;
    }

    public boolean withdrawToBottle(Player player, int xpAmount) {
        if (xpAmount <= 0) {
            return false;
        }

        if (!XPUtil.removeExactExperience(player, xpAmount)) {
            return false;
        }

        giveBottle(player, xpAmount);
        return true;
    }

    public int redeemFromHand(Player player, EquipmentSlot hand, boolean redeemAllInClickedHand) {
        ItemStack target = getHandItem(player.getInventory(), hand);
        if (!isPluginBottle(target)) {
            return -1;
        }

        int xpPerBottle = getStoredXp(target);
        if (xpPerBottle <= 0) {
            return -1;
        }

        int redeemed;
        if (redeemAllInClickedHand) {
            int stackAmount = target.getAmount();
            try {
                redeemed = Math.multiplyExact(xpPerBottle, stackAmount);
            } catch (ArithmeticException ex) {
                return -1;
            }
            setHandItem(player.getInventory(), hand, null);
        } else {
            redeemed = xpPerBottle;
            int stackAmount = target.getAmount();
            if (stackAmount <= 1) {
                setHandItem(player.getInventory(), hand, null);
            } else {
                target.setAmount(stackAmount - 1);
                setHandItem(player.getInventory(), hand, target);
            }
        }

        XPUtil.addExactExperience(player, redeemed);
        return redeemed;
    }

    public boolean consumeInvalidBottleFromHand(Player player, EquipmentSlot hand) {
        ItemStack target = getHandItem(player.getInventory(), hand);
        if (!isPluginBottle(target)) {
            return false;
        }

        int stackAmount = target.getAmount();
        if (stackAmount <= 1) {
            setHandItem(player.getInventory(), hand, null);
        } else {
            target.setAmount(stackAmount - 1);
            setHandItem(player.getInventory(), hand, target);
        }
        return true;
    }

    private ItemStack getHandItem(PlayerInventory inventory, EquipmentSlot hand) {
        if (hand == EquipmentSlot.OFF_HAND) {
            return inventory.getItemInOffHand();
        }
        return inventory.getItemInMainHand();
    }

    private void setHandItem(PlayerInventory inventory, EquipmentSlot hand, ItemStack item) {
        if (hand == EquipmentSlot.OFF_HAND) {
            inventory.setItemInOffHand(item);
            return;
        }
        inventory.setItemInMainHand(item);
    }

    private String replacePlaceholders(String text, Map<String, String> placeholders) {
        String output = text;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            output = output.replace(entry.getKey(), entry.getValue());
        }
        return output;
    }

    private Material parseMaterial(String name, Material fallback, String pathForLog) {
        Material material = Material.matchMaterial(name);
        if (material == null) {
            plugin.getLogger().warning("Invalid material at " + pathForLog + ": " + name + ". Falling back to " + fallback);
            return fallback;
        }
        return material;
    }

    public int getTotalXp(Player player) {
        return XPUtil.getTotalExperience(player);
    }
}
