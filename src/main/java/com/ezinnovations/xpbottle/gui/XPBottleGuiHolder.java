package com.ezinnovations.xpbottle.gui;

import java.util.UUID;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class XPBottleGuiHolder implements InventoryHolder {

    private final UUID viewer;

    public XPBottleGuiHolder(UUID viewer) {
        this.viewer = viewer;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    public UUID getViewer() {
        return viewer;
    }
}
