package io.igorcossta.listener.war;

import io.igorcossta.Plugin;
import io.igorcossta.config.GameConfigLocations;
import io.igorcossta.config.GameConfigMessages;
import io.igorcossta.event.custom.WarStopEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class EventExpiredListener implements Listener {
    private final GameConfigLocations locations = Plugin.getLocations();
    private final GameConfigMessages messages = Plugin.getMessages();

    @EventHandler
    public void onWarStopEvent(WarStopEvent event) {
        List<Player> participants = event.getParticipants();

        participants.forEach(i -> {
            i.teleport(locations.getExitLocation());
            i.getInventory().clear();
        });
        Bukkit.getServer().sendMessage(messages.sendWarIsClosedMessage(event.getCause().getMessage()));
    }
}
