package io.igorcossta.listener.war;

import io.igorcossta.Plugin;
import io.igorcossta.event.custom.WarStopEvent;
import io.igorcossta.manager.ColorWarManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class EventExpiredListener implements Listener {
    private final ColorWarManager colorWarManager = Plugin.getColorWarManager();

    @EventHandler
    public void onWarStopEvent(WarStopEvent event) {
        List<Player> participants = event.getParticipants();

        participants.forEach(i -> {
            i.teleport(colorWarManager.getColorWarLocations().getExitLocation());
            i.getInventory().clear();
        });
        Bukkit.getServer().sendMessage(Component.text(event.getCause().getMessage()));
    }
}
