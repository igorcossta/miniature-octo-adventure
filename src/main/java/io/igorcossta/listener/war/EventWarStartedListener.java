package io.igorcossta.listener.war;

import io.igorcossta.Plugin;
import io.igorcossta.event.custom.WarStartEvent;
import io.igorcossta.manager.ColorWarManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class EventWarStartedListener implements Listener {
    private final ColorWarManager colorWarManager = Plugin.getColorWarManager();

    @EventHandler
    public void onWarStartEvent(WarStartEvent event) {
        List<Player> participants = event.getParticipants();

        participants.forEach(i -> {
            i.teleport(colorWarManager.getColorWarLocations().getBattleLocation());
            i.sendMessage(Component.text("The war is enabled"));
        });
    }
}
