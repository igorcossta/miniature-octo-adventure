package io.igorcossta.listener.player;

import io.igorcossta.Plugin;
import io.igorcossta.config.GameConfigLocations;
import io.igorcossta.config.GameConfigMessages;
import io.igorcossta.event.custom.PlayerWinEvent;
import io.igorcossta.util.Broadcast;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerWinListener implements Listener {
    private final GameConfigLocations locations = Plugin.getLocations();
    private final GameConfigMessages messages = Plugin.getMessages();
    private final Economy econ = Plugin.getEcon();
    private final Broadcast broadcast = Plugin.getBroadcast();

    @EventHandler
    public void onPlayerWinEvent(PlayerWinEvent event) {
        Player player = event.getPlayer();

        broadcast.toServer(messages.sendVictoryMessage(player.getName()));

        player.getInventory().clear();
        player.teleport(locations.getExitLocation());
        econ.depositPlayer(player, 99.999);
    }
}
