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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class XPBottleItemManager {

    private final ConfigManager configManager;
    private final MessageManager messageManager;
    private final NamespacedKey bottleKey;
    private final NamespacedKey xpKey;
    private final NamespacedKey versionKey;

    public XPBottleItemManager(XPBottlePlugin plugin, ConfigManager configManager, MessageManager messageManager) {
        this.configManager = configManager;
        this.messageManager = messageManager;
        this.bottleKey = new NamespacedKey(plugin, "is_xpbottle");
        this.xpKey = new NamespacedKey(plugin, "stored_xp");
        this.versionKey = new NamespacedKey(plugin, "item_version");
    }

    public ItemStack createBottle(int xpAmount) {
        Material material = Material.matchMaterial(configManager.getItemConfig().getString("item.material", "EXPERIENCE_BOTTLE"));
        if (material == null) {
            material = Material.EXPERIENCE_BOTTLE;
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        Map<String, String> placeholders = Map.of("%xp%", String.valueOf(xpAmount));
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
        meta.getPersistentDataContainer().set(xpKey, PersistentDataType.INTEGER, xpAmount);
        meta.getPersistentDataContainer().set(versionKey, PersistentDataType.INTEGER, 1);

        item.setItemMeta(meta);
        return item;
    }

    public boolean isPluginBottle(ItemStack item) {
        if (item == null || item.getType().isAir() || !item.hasItemMeta()) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        Byte marker = meta.getPersistentDataContainer().get(bottleKey, PersistentDataType.BYTE);
        return marker != null && marker == (byte) 1;
    }

    public int getStoredXp(ItemStack item) {
        if (!isPluginBottle(item)) {
            return -1;
        }

        ItemMeta meta = item.getItemMeta();
        Integer xp = meta.getPersistentDataContainer().get(xpKey, PersistentDataType.INTEGER);
        return xp == null ? -1 : xp;
    }

    public boolean giveBottle(Player player, int xpAmount) {
        if (xpAmount <= 0) {
            return false;
        }

        ItemStack bottle = createBottle(xpAmount);
        HashMap<Integer, ItemStack> leftovers = player.getInventory().addItem(bottle);
        leftovers.values().forEach(leftover -> player.getWorld().dropItemNaturally(player.getLocation(), leftover));
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

    public int redeemFromHand(Player player, boolean redeemAllSameBottleFromHand) {
        PlayerInventory inventory = player.getInventory();
        ItemStack main = inventory.getItemInMainHand();
        ItemStack off = inventory.getItemInOffHand();

        ItemStack target = isPluginBottle(main) ? main : isPluginBottle(off) ? off : null;
        if (target == null) {
            return -1;
        }

        int xp = getStoredXp(target);
        if (xp <= 0) {
            return -1;
        }

        int redeemed = xp;
        if (redeemAllSameBottleFromHand) {
            int count = target.getAmount();
            redeemed = xp * count;
            target.setAmount(0);
        } else {
            int amount = target.getAmount();
            if (amount <= 1) {
                target.setAmount(0);
            } else {
                target.setAmount(amount - 1);
            }
        }

        XPUtil.addExactExperience(player, redeemed);
        return redeemed;
    }

    private String replacePlaceholders(String text, Map<String, String> placeholders) {
        String output = text;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            output = output.replace(entry.getKey(), entry.getValue());
        }
        return output;
    }

    public int getTotalXp(Player player) {
        return XPUtil.getTotalExperience(player);
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }
}
