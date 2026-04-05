package com.ezinnovations.xpbottle.config;

import com.ezinnovations.xpbottle.XPBottlePlugin;
import com.ezinnovations.xpbottle.util.TextUtil;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageManager {

    private final XPBottlePlugin plugin;
    private final ConfigManager configManager;

    public MessageManager(XPBottlePlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    public String getMessage(String path) {
        FileConfiguration messages = configManager.getMessagesConfig();
        String raw = messages.getString(path, "&cMissing message: " + path);
        return TextUtil.color(raw);
    }

    public List<String> getMessageList(String path) {
        List<String> rawList = configManager.getMessagesConfig().getStringList(path);
        if (rawList.isEmpty()) {
            return Collections.singletonList(TextUtil.color("&cMissing message list: " + path));
        }
        return rawList.stream().map(TextUtil::color).toList();
    }

    public void send(CommandSender sender, String path) {
        send(sender, path, Collections.emptyMap());
    }

    public void send(CommandSender sender, String path, Map<String, String> placeholders) {
        String message = getMessage(path);
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            message = message.replace(entry.getKey(), entry.getValue());
        }
        sender.sendMessage(message);
    }

    public XPBottlePlugin getPlugin() {
        return plugin;
    }
}
