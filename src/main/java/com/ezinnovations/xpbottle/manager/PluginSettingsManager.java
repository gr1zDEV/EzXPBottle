package com.ezinnovations.xpbottle.manager;

import com.ezinnovations.xpbottle.config.ConfigManager;
import com.ezinnovations.xpbottle.gui.XPBottleGuiManager;
import org.bukkit.configuration.file.FileConfiguration;

public class PluginSettingsManager {

    private final ConfigManager configManager;

    public PluginSettingsManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public boolean isGuiRefreshOnWithdraw() {
        return configManager.getMainConfig().getBoolean("gui.refresh-on-withdraw", true);
    }

    public boolean areSoundsEnabled() {
        return configManager.getMainConfig().getBoolean("sounds.enabled", true);
    }

    public boolean isShiftRedeemAllEnabled() {
        return configManager.getMainConfig().getBoolean("redeem.shift-right-click-redeem-all", true);
    }

    public XPBottleGuiManager.WithdrawAllMode getWithdrawAllMode() {
        FileConfiguration gui = configManager.getGuiConfig();
        return XPBottleGuiManager.WithdrawAllMode.from(gui.getString("gui.withdraw-all.mode", "disabled"));
    }
}
