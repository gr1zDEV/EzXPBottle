package com.ezinnovations.xpbottle;

import com.ezinnovations.xpbottle.command.XPBottleCommand;
import com.ezinnovations.xpbottle.config.ConfigManager;
import com.ezinnovations.xpbottle.config.MessageManager;
import com.ezinnovations.xpbottle.gui.XPBottleGuiManager;
import com.ezinnovations.xpbottle.item.XPBottleItemManager;
import com.ezinnovations.xpbottle.listener.PlayerRedeemListener;
import com.ezinnovations.xpbottle.listener.XPBottleGuiListener;
import com.ezinnovations.xpbottle.placeholder.XPBottlePlaceholderExpansion;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class XPBottlePlugin extends JavaPlugin {

    private ConfigManager configManager;
    private MessageManager messageManager;
    private XPBottleItemManager itemManager;
    private XPBottleGuiManager guiManager;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        this.configManager.loadAll();

        this.messageManager = new MessageManager(this, configManager);
        this.itemManager = new XPBottleItemManager(this, configManager, messageManager);
        this.guiManager = new XPBottleGuiManager(this, configManager, messageManager, itemManager);

        XPBottleCommand commandExecutor = new XPBottleCommand(this, configManager, messageManager, guiManager, itemManager);
        PluginCommand command = getCommand("xpbottle");
        if (command != null) {
            command.setExecutor(commandExecutor);
            command.setTabCompleter(commandExecutor);
        } else {
            getLogger().severe("Failed to register /xpbottle command from plugin.yml");
        }

        getServer().getPluginManager().registerEvents(new XPBottleGuiListener(guiManager), this);
        getServer().getPluginManager().registerEvents(new PlayerRedeemListener(configManager, messageManager, itemManager), this);

        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new XPBottlePlaceholderExpansion(this, itemManager).register();
            getLogger().info("PlaceholderAPI detected: XPBottle placeholders registered.");
        }
    }

    public void reloadPlugin() {
        configManager.loadAll();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public XPBottleItemManager getItemManager() {
        return itemManager;
    }

    public XPBottleGuiManager getGuiManager() {
        return guiManager;
    }
}
