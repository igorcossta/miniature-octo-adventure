package io.igorcossta.listener.player;

import io.igorcossta.Plugin;
import io.igorcossta.manager.ColorWarManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerInventoryClickListener implements Listener {
    private final ColorWarManager colorWarManager = Plugin.getColorWarManager();

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (!colorWarManager.isRunning()) return; // if event is not running then do nothing
        if (!colorWarManager.isParticipating(event.getWhoClicked().getName()))
            return; // if player is not in the event then do nothing

        event.setCancelled(true);
    }
}
