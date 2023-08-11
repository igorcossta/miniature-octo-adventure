package io.igorcossta.manager;

import io.igorcossta.Plugin;
import io.igorcossta.config.GameConfigMessages;
import io.igorcossta.event.Cause;
import io.igorcossta.event.custom.PlayerJoinWarEvent;
import io.igorcossta.event.custom.WarOpenEvent;
import io.igorcossta.event.custom.WarStartEvent;
import io.igorcossta.event.custom.WarStopEvent;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class ColorWarManager {
    private final Plugin plugin = Plugin.getInstance();
    private final Economy econ = Plugin.getEcon();
    private final ColorWarLocations colorWarLocations = new ColorWarLocations();
    private final GameConfigMessages messages = Plugin.getConfigurationManager().getGameMessages();
    private Set<String> participants = new HashSet<>();
    private boolean isRunning = false;
    private boolean isWarStarted = false;

    public void playerJoin(Player player) {
        participants.add(player.getName());

        // call the event when player join to color war
        PlayerJoinWarEvent playerJoinWarEvent = new PlayerJoinWarEvent(player);
        plugin.getServer().getPluginManager().callEvent(playerJoinWarEvent);
    }

    public void participantDie(String player) {
        handleParticipantExit(player);
    }

    public void participantQuit(String player) {
        handleParticipantExit(player);
    }

    private void handleParticipantExit(String player) {
        participants.remove(player);
        checkAndDeclareWinner();
    }

    private void checkAndDeclareWinner() {
        if (participants.size() == 1) {
            declareParticipantWinner(participants.stream().findFirst().orElse(null));
        }
    }

    private void declareParticipantWinner(String winner) {
        if (winner == null) {
            return;
        }
        Player player = Bukkit.getPlayer(winner);
        Bukkit.getServer().sendMessage(messages.sendVictoryMessage(winner));

        clearInventory(player);
        player.teleport(colorWarLocations.getExitLocation());
        econ.depositPlayer(player, 1);
        resetEventState();
    }

    public void clearInventory(Player player) {
        player.getInventory().clear();
    }

    public boolean isParticipating(String player) {
        return participants.contains(player);
    }

    public void broadcastToEvent(Component message) {
        participants.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(player -> player.sendMessage(message));
    }

    private void resetEventState() {
        isRunning = false;
        isWarStarted = false;
        participants.clear();
    }

    public void stopWar(Cause cause) {
        List<Player> participants = this.participants.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .toList();

        resetEventState();
        // call the event when the color-war time expired
        WarStopEvent warStopEvent = new WarStopEvent(participants, cause);
        plugin.getServer().getPluginManager().callEvent(warStopEvent);
    }

    public void startWar() {
        List<Player> participants = this.participants.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .toList();

        isWarStarted = true;
        // call the event when the color-war pvp is enabled
        WarStartEvent warStartEvent = new WarStartEvent(participants);
        plugin.getServer().getPluginManager().callEvent(warStartEvent);
    }

    public void openWar() {

        isRunning = true;
        // call the event when the war is open to join
        WarOpenEvent warOpenEvent = new WarOpenEvent();
        plugin.getServer().getPluginManager().callEvent(warOpenEvent);
    }
}
