package com.ezinnovations.xpbottle.util;

import org.bukkit.ChatColor;

public final class TextUtil {

    private TextUtil() {
    }

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text == null ? "" : text);
    }
}
