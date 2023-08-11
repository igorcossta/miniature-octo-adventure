package io.igorcossta.listener.player;

import io.igorcossta.Plugin;
import io.igorcossta.config.GameConfigMessages;
import io.igorcossta.event.custom.PlayerDieInWarEvent;
import io.igorcossta.manager.ColorWarManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerDieInWarListener implements Listener {
    private final ColorWarManager colorWarManager = Plugin.getColorWarManager();
    private final GameConfigMessages messages = Plugin.getConfigurationManager().getGameMessages();

    @EventHandler
    public void onPlayerDieInWarEvent(PlayerDieInWarEvent event) {
        Player player = event.getPlayer();

        player.teleport(colorWarManager.getColorWarLocations().getExitLocation());
        colorWarManager.broadcastToEvent(messages.sendDeathMessage(player.getName()));

        colorWarManager.clearInventory(player);
        colorWarManager.participantDie(player.getName());
    }
}
