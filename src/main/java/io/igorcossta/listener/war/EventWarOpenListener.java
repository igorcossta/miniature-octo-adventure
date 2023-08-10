package io.igorcossta.listener.war;

import io.igorcossta.Plugin;
import io.igorcossta.event.custom.WarOpenEvent;
import io.igorcossta.manager.ColorWarManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EventWarOpenListener implements Listener {
    private final ColorWarManager colorWarManager = Plugin.getColorWarManager();

    @EventHandler
    public void onWarOpenEvent(WarOpenEvent event) {
        Bukkit.getServer().sendMessage(Component.text("The event is open!"));
    }
}
