package io.igorcossta.command;

import io.igorcossta.Plugin;
import io.igorcossta.event.Cause;
import io.igorcossta.manager.ColorWarManager;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class AdminCommand {
    private final Plugin plugin = Plugin.getInstance();
    private final ColorWarManager colorWarManager = Plugin.getColorWarManager();

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
            p.sendMessage(Component.text("The war is already open."));
            return;
        }

        p.sendMessage(Component.text("starting the color war event"));
        colorWarManager.setRunning(true);

        colorWarManager.getTaskWar().runTaskTimer(plugin, 0, 20 * 5); // initiate the war after 5 minutes
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
            p.sendMessage(Component.text("The war is not happening."));
            return;
        }

        p.sendMessage(Component.text("Stopping the war"));
        colorWarManager.stopWar(Cause.CLOSE_BY_OPERATOR);

        colorWarManager.getTaskWar().cancel();
    }
}
