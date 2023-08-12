package io.igorcossta.listener.war;

import io.igorcossta.Plugin;
import io.igorcossta.config.GameConfigMessages;
import io.igorcossta.event.custom.WarOpenEvent;
import io.igorcossta.util.Broadcast;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EventWarOpenListener implements Listener {
    private final GameConfigMessages messages = Plugin.getMessages();
    private final Broadcast broadcast = Plugin.getBroadcast();

    @EventHandler
    public void onWarOpenEvent(WarOpenEvent event) {
        broadcast.toServer(messages.sendWarIsOpenMessage());
    }
}
