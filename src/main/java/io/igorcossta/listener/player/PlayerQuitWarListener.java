package io.igorcossta.listener.player;

import io.igorcossta.Plugin;
import io.igorcossta.config.GameConfigLocations;
import io.igorcossta.config.GameConfigMessages;
import io.igorcossta.manager.ColorWarManager;
import io.igorcossta.util.Broadcast;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitWarListener implements Listener {
    private final ColorWarManager colorWarManager = Plugin.getColorWarManager();
    private final GameConfigLocations locations = Plugin.getLocations();
    private final GameConfigMessages messages = Plugin.getMessages();
    private final Broadcast broadcast = Plugin.getBroadcast();

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        if (!colorWarManager.isRunning()) return; // if event is not running then do nothing
        Player player = event.getPlayer();
        if (!colorWarManager.isParticipating(player.getName()))
            return; // if player is not in the event then do nothing

        player.teleport(locations.getExitLocation());
        broadcast.toParticipants(messages.sendQuitMessage(player.getName()));

        player.getInventory().clear();
        colorWarManager.participantQuit(player.getName());
    }
}
