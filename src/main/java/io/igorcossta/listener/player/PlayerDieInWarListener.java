package io.igorcossta.listener.player;

import io.igorcossta.Plugin;
import io.igorcossta.config.GameConfigLocations;
import io.igorcossta.config.GameConfigMessages;
import io.igorcossta.event.custom.PlayerDieInWarEvent;
import io.igorcossta.manager.ColorWarManager;
import io.igorcossta.util.Broadcast;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerDieInWarListener implements Listener {
    private final ColorWarManager colorWarManager = Plugin.getColorWarManager();
    private final GameConfigLocations locations = Plugin.getLocations();
    private final GameConfigMessages messages = Plugin.getMessages();
    private final Broadcast broadcast = Plugin.getBroadcast();

    @EventHandler
    public void onPlayerDieInWarEvent(PlayerDieInWarEvent event) {
        Player player = event.getPlayer();

        player.teleport(locations.getExitLocation());
        broadcast.toParticipants(messages.sendDeathMessage(player.getName()));

        player.getInventory().clear();
        colorWarManager.participantDie(player.getName());
    }
}
