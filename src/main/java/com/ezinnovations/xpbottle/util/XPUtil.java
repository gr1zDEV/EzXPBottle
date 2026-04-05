package com.ezinnovations.xpbottle.util;

import org.bukkit.entity.Player;

public final class XPUtil {

    private XPUtil() {
    }

    public static int getTotalExperience(Player player) {
        int levelBase = getExperienceAtLevel(player.getLevel());
        int progress = (int) Math.floor(player.getExp() * player.getExpToLevel());
        return Math.max(0, levelBase + progress);
    }

    public static void addExactExperience(Player player, int amount) {
        if (amount <= 0) {
            return;
        }
        player.giveExp(amount);
    }

    public static boolean removeExactExperience(Player player, int amount) {
        if (amount <= 0) {
            return false;
        }

        int total = getTotalExperience(player);
        if (total < amount) {
            return false;
        }

        setTotalExperience(player, total - amount);
        return true;
    }

    public static void setTotalExperience(Player player, int total) {
        int safeTotal = Math.max(0, total);
        player.setExp(0f);
        player.setLevel(0);
        player.setTotalExperience(0);
        player.giveExp(safeTotal);
    }

    private static int getExperienceAtLevel(int level) {
        if (level <= 16) {
            return level * level + (6 * level);
        }
        if (level <= 31) {
            return (int) Math.floor(2.5 * level * level - 40.5 * level + 360);
        }
        return (int) Math.floor(4.5 * level * level - 162.5 * level + 2220);
    }
}
