package io.igorcossta.command;

import io.igorcossta.Plugin;
import io.igorcossta.config.GameConfigMessages;
import io.igorcossta.event.Cause;
import io.igorcossta.manager.ColorWarManager;
import io.igorcossta.runnable.StartWarRunnable;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AdminCommand {
    private final Plugin plugin = Plugin.getInstance();
    private final ColorWarManager colorWarManager = Plugin.getColorWarManager();
    private final GameConfigMessages messages = Plugin.getConfigurationManager().getGameMessages();
    private BukkitRunnable bukkitRunnable;

    @Command(
            name = "colorwar",
            aliases = {"war"},
            usage = "/colorwar",
            description = "command to show all commands",
            permission = "colorwar.help",
            target = CommandTarget.PLAYER
    )
    public void helpCommand(Context<Player> context) {
        Player p = context.getSender();
        p.sendMessage(Component.text("COLOR-WAR HELPER"));
    }

    @Command(
            name = "colorwar.start",
            aliases = {"war"},
            usage = "/colorwar start",
            description = "command to start the color war event",
            permission = "colorwar.start",
            target = CommandTarget.PLAYER
    )
    public void startCommand(Context<Player> context) {
        Player p = context.getSender();

        if (colorWarManager.isRunning()) {
            p.sendMessage(messages.sendCantStartBecauseRunningMessage());
            return;
        }

        colorWarManager.openWar();
        bukkitRunnable = new StartWarRunnable();
        bukkitRunnable.runTaskTimer(plugin, 0, 20 * 5);
    }

    @Command(
            name = "colorwar.stop",
            aliases = {"war"},
            usage = "/colorwar stop",
            description = "command to stop the color war event",
            permission = "colorwar.stop",
            target = CommandTarget.PLAYER
    )
    public void stopCommand(Context<Player> context) {
        Player p = context.getSender();

        if (!colorWarManager.isRunning()) {
            p.sendMessage(messages.sendCantStopBecauseNotRunningMessage());
            return;
        }

        colorWarManager.stopWar(Cause.CLOSE_BY_OPERATOR);
        bukkitRunnable.cancel();
    }
}
