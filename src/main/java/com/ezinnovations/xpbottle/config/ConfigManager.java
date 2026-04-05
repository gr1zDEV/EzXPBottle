package com.ezinnovations.xpbottle.config;

import com.ezinnovations.xpbottle.XPBottlePlugin;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {

    private final XPBottlePlugin plugin;
    private FileConfiguration mainConfig;
    private FileConfiguration guiConfig;
    private FileConfiguration itemConfig;
    private FileConfiguration messagesConfig;

    public ConfigManager(XPBottlePlugin plugin) {
        this.plugin = plugin;
    }

    public void loadAll() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        this.mainConfig = plugin.getConfig();

        this.guiConfig = loadNamedConfig("gui.yml");
        this.itemConfig = loadNamedConfig("item.yml");
        this.messagesConfig = loadNamedConfig("messages.yml");
    }

    private FileConfiguration loadNamedConfig(String name) {
        File file = new File(plugin.getDataFolder(), name);
        if (!file.exists()) {
            plugin.saveResource(name, false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getMainConfig() {
        return mainConfig;
    }

    public FileConfiguration getGuiConfig() {
        return guiConfig;
    }

    public FileConfiguration getItemConfig() {
        return itemConfig;
    }

    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }
}
