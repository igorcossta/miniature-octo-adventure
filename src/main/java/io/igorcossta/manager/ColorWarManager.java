package io.igorcossta.manager;

import io.igorcossta.Plugin;
import io.igorcossta.event.Cause;
import io.igorcossta.event.custom.*;
import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class ColorWarManager {
    private final Plugin plugin = Plugin.getInstance();
    private final Economy econ = Plugin.getEcon();
    private Set<String> participants = new HashSet<>();
    private boolean isRunning = false;
    private boolean isWarStarted = false;
    public static BukkitRunnable bukkitRunnable;

    public void playerJoin(Player player) {
        participants.add(player.getName());

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

        resetEventState();
        PlayerWinEvent playerWinEvent = new PlayerWinEvent(player);
        plugin.getServer().getPluginManager().callEvent(playerWinEvent);
    }

    public boolean isParticipating(String player) {
        return participants.contains(player);
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
        WarStopEvent warStopEvent = new WarStopEvent(participants, cause);
        plugin.getServer().getPluginManager().callEvent(warStopEvent);
    }

    public void startWar() {
        List<Player> participants = this.participants.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .toList();

        isWarStarted = true;
        WarStartEvent warStartEvent = new WarStartEvent(participants);
        plugin.getServer().getPluginManager().callEvent(warStartEvent);
    }

    public void openWar() {

        isRunning = true;
        WarOpenEvent warOpenEvent = new WarOpenEvent();
        plugin.getServer().getPluginManager().callEvent(warOpenEvent);
    }
}
