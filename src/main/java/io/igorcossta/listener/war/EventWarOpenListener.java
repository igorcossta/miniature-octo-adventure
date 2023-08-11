package io.igorcossta.listener.war;

import io.igorcossta.Plugin;
import io.igorcossta.config.GameConfigMessages;
import io.igorcossta.event.custom.WarOpenEvent;
import io.igorcossta.manager.ColorWarManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EventWarOpenListener implements Listener {
    private final ColorWarManager colorWarManager = Plugin.getColorWarManager();
    private final GameConfigMessages messages = Plugin.getConfigurationManager().getGameMessages();

    @EventHandler
    public void onWarOpenEvent(WarOpenEvent event) {
        Bukkit.getServer().sendMessage(messages.sendWarIsOpenMessage());
    }
}
