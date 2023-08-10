package io.igorcossta.runnable;

import io.igorcossta.Plugin;
import io.igorcossta.event.Cause;
import io.igorcossta.manager.ColorWarManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class StartWarRunnable extends BukkitRunnable {
    private final ColorWarManager colorWarManager = Plugin.getColorWarManager();
    private LocalDateTime initiatedAt = LocalDateTime.now();

    @Override
    public void run() {
        if (!colorWarManager.isRunning()) {
            return; // Exit if the event is not running
        }

        long minutesPassed = getMinutesPassed();
        Bukkit.getLogger().info("[timer task] Event is open. Minutes passed: " + minutesPassed);

        if (!colorWarManager.isWarStarted()) {
            startWar(minutesPassed);
        } else {
            stopEvent(minutesPassed, Cause.TIME_EXPIRED);
        }
    }

    private long getMinutesPassed() {
        LocalDateTime now = LocalDateTime.now();
        return initiatedAt.until(now, ChronoUnit.MINUTES);
    }

    private void startWar(long minutesPassed) {
        if (minutesPassed >= 1) {

            if (colorWarManager.getParticipants().isEmpty()) {
                stopEvent(99, Cause.NON_PARTICIPANTS); // force the event to stop
                return;
            }

            if (colorWarManager.getParticipants().size() == 1) {
                stopEvent(99, Cause.ONLY_ONE_PARTICIPANT); // force the event to stop
                return;
            }

            Bukkit.getLogger().info("[timer task] Enabling PvP");
            colorWarManager.startWar();
        }
    }

    private void stopEvent(long minutesPassed, Cause cause) {
        if (minutesPassed >= 2) { // the event should be close after 10 minutes
            Bukkit.getLogger().info("[timer task] Closing event");
            this.initiatedAt = null;
            colorWarManager.stopWar(cause);
            this.cancel(); // Stop the task
        }
    }
}
