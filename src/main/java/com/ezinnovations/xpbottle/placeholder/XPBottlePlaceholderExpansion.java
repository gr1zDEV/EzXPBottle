package com.ezinnovations.xpbottle.placeholder;

import com.ezinnovations.xpbottle.XPBottlePlugin;
import com.ezinnovations.xpbottle.item.XPBottleItemManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XPBottlePlaceholderExpansion extends PlaceholderExpansion {

    private final XPBottlePlugin plugin;
    private final XPBottleItemManager itemManager;

    public XPBottlePlaceholderExpansion(XPBottlePlugin plugin, XPBottleItemManager itemManager) {
        this.plugin = plugin;
        this.itemManager = itemManager;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "xpbottle";
    }

    @Override
    public @NotNull String getAuthor() {
        return "EzInnovations";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null || !player.isOnline() || player.getPlayer() == null) {
            return "0";
        }

        return switch (params.toLowerCase()) {
            case "total_xp" -> String.valueOf(itemManager.getTotalXp(player.getPlayer()));
            case "level" -> String.valueOf(player.getPlayer().getLevel());
            default -> null;
        };
    }
}
