package io.igorcossta.listener.player;

import io.igorcossta.Plugin;
import io.igorcossta.manager.ColorWarManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitWarListener implements Listener {
    private final ColorWarManager colorWarManager = Plugin.getColorWarManager();

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        if (!colorWarManager.isRunning()) return; // if event is not running then do nothing
        Player player = event.getPlayer();
        if (!colorWarManager.isParticipating(player.getName()))
            return; // if player is not in the event then do nothing

        player.teleport(colorWarManager.getColorWarLocations().getExitLocation());
        colorWarManager.broadcastToEvent("%s quit".formatted(player.getName()));

        colorWarManager.clearInventory(player);
        colorWarManager.participantQuit(player.getName());
    }
}
