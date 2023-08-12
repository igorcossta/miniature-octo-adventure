package io.igorcossta.listener.war;

import io.igorcossta.Plugin;
import io.igorcossta.config.GameConfigLocations;
import io.igorcossta.config.GameConfigMessages;
import io.igorcossta.event.custom.WarStartEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class EventWarStartedListener implements Listener {
    private final GameConfigLocations locations = Plugin.getLocations();
    private final GameConfigMessages messages = Plugin.getMessages();

    @EventHandler
    public void onWarStartEvent(WarStartEvent event) {
        List<Player> participants = event.getParticipants();

        participants.forEach(i -> {
            i.teleport(locations.getBattleLocation());
            i.sendMessage(messages.sendWarIsStartedMessage());
        });
    }
}
