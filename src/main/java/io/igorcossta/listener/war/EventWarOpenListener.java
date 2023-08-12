package io.igorcossta.listener.war;

import io.igorcossta.Plugin;
import io.igorcossta.config.GameConfigMessages;
import io.igorcossta.event.custom.WarOpenEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EventWarOpenListener implements Listener {
    private final GameConfigMessages messages = Plugin.getMessages();

    @EventHandler
    public void onWarOpenEvent(WarOpenEvent event) {
        Bukkit.getServer().sendMessage(messages.sendWarIsOpenMessage());
    }
}
