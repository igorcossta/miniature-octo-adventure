package io.igorcossta.listener.war;

import io.igorcossta.Plugin;
import io.igorcossta.config.GameConfigMessages;
import io.igorcossta.event.custom.WarStopEvent;
import io.igorcossta.manager.ColorWarManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class EventExpiredListener implements Listener {
    private final ColorWarManager colorWarManager = Plugin.getColorWarManager();
    private final GameConfigMessages messages = Plugin.getConfigurationManager().getGameMessages();

    @EventHandler
    public void onWarStopEvent(WarStopEvent event) {
        List<Player> participants = event.getParticipants();

        participants.forEach(i -> {
            i.teleport(colorWarManager.getColorWarLocations().getExitLocation());
            i.getInventory().clear();
        });
        Bukkit.getServer().sendMessage(messages.sendWarIsClosedMessage(event.getCause().getMessage()));
    }
}
