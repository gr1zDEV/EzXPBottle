package com.ezinnovations.xpbottle.listener;

import com.ezinnovations.xpbottle.config.ConfigManager;
import com.ezinnovations.xpbottle.config.MessageManager;
import com.ezinnovations.xpbottle.item.XPBottleItemManager;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.ThrownExpBottle;

public class PlayerRedeemListener implements Listener {

    private static final long REDEEM_COOLDOWN_MS = 75L;

    private final ConfigManager configManager;
    private final MessageManager messageManager;
    private final XPBottleItemManager itemManager;
    private final Map<UUID, Long> lastRedeemByPlayer = new ConcurrentHashMap<>();

    public PlayerRedeemListener(ConfigManager configManager, MessageManager messageManager, XPBottleItemManager itemManager) {
        this.configManager = configManager;
        this.messageManager = messageManager;
        this.itemManager = itemManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        EquipmentSlot hand = event.getHand();
        if (hand == null) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null) {
            item = (hand == EquipmentSlot.OFF_HAND)
                    ? player.getInventory().getItemInOffHand()
                    : player.getInventory().getItemInMainHand();
        }
        if (!itemManager.isPluginBottle(item)) {
            return;
        }

        if (!player.hasPermission("xpbottle.redeem")) {
            event.setCancelled(true);
            messageManager.send(player, "messages.no-permission");
            return;
        }

        if (isRedeemOnCooldown(player.getUniqueId())) {
            event.setCancelled(true);
            return;
        }

        event.setCancelled(true);
        event.setUseItemInHand(org.bukkit.event.Event.Result.DENY);
        event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);

        FileConfiguration config = configManager.getMainConfig();
        boolean shiftRedeemAllEnabled = config.getBoolean("redeem.shift-right-click-redeem-all", true) && player.isSneaking();
        int redeemed = itemManager.redeemFromHand(player, hand, shiftRedeemAllEnabled);
        if (redeemed <= 0) {
            boolean consumed = itemManager.consumeInvalidBottleFromHand(player, hand);
            if (consumed) {
                messageManager.send(player, "messages.invalid-bottle-data");
            }
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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof ThrownExpBottle bottle)) {
            return;
        }
        if (!(bottle.getShooter() instanceof Player player)) {
            return;
        }

        ItemStack thrownItem = bottle.getItem();
        if (!itemManager.isPluginBottle(thrownItem)) {
            return;
        }

        event.setCancelled(true);
        bottle.remove();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onExpBottle(ExpBottleEvent event) {
        if (itemManager.isPluginBottle(event.getEntity().getItem())) {
            event.setExperience(0);
            event.setShowEffect(false);
        }
    }

    private boolean isRedeemOnCooldown(UUID playerId) {
        long now = System.currentTimeMillis();
        Long last = lastRedeemByPlayer.get(playerId);
        if (last != null && (now - last) < REDEEM_COOLDOWN_MS) {
            return true;
        }
        lastRedeemByPlayer.put(playerId, now);
        return false;
    }
}
