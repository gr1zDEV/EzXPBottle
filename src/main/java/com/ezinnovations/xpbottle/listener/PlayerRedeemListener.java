package com.ezinnovations.xpbottle.listener;

import com.ezinnovations.xpbottle.config.ConfigManager;
import com.ezinnovations.xpbottle.config.MessageManager;
import com.ezinnovations.xpbottle.item.XPBottleItemManager;
import java.util.Map;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerRedeemListener implements Listener {

    private final ConfigManager configManager;
    private final MessageManager messageManager;
    private final XPBottleItemManager itemManager;

    public PlayerRedeemListener(ConfigManager configManager, MessageManager messageManager, XPBottleItemManager itemManager) {
        this.configManager = configManager;
        this.messageManager = messageManager;
        this.itemManager = itemManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        if (!player.hasPermission("xpbottle.redeem")) {
            return;
        }

        ItemStack item = event.getItem();
        if (!itemManager.isPluginBottle(item)) {
            return;
        }

        event.setCancelled(true);

        FileConfiguration config = configManager.getMainConfig();
        boolean shiftRedeemAllEnabled = config.getBoolean("redeem.shift-right-click-redeem-all", true) && player.isSneaking();
        int redeemed = itemManager.redeemFromHand(player, shiftRedeemAllEnabled);
        if (redeemed <= 0) {
            return;
        }

        messageManager.send(player, "messages.redeemed-xp", Map.of("%xp%", String.valueOf(redeemed)));

        if (config.getBoolean("sounds.enabled", true)) {
            String soundName = config.getString("sounds.redeem", "ENTITY_PLAYER_LEVELUP");
            try {
                Sound sound = Sound.valueOf(soundName);
                player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
            } catch (IllegalArgumentException ignored) {
                // Invalid sound, safely skip.
            }
        }
    }
}
