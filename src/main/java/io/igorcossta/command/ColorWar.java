package io.igorcossta.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import io.igorcossta.Plugin;
import io.igorcossta.config.GameConfigLocations;
import io.igorcossta.config.GameConfigMessages;
import io.igorcossta.event.Cause;
import io.igorcossta.manager.ColorWarManager;
import io.igorcossta.runnable.StartWarRunnable;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("cw")
@Description("The command's for Color War plugin")
@CommandPermission("cw.command")
@Conditions("player")
@Getter
public class ColorWar extends BaseCommand {
    private final ColorWarManager colorWarManager = Plugin.getColorWarManager();
    private final GameConfigMessages messages = Plugin.getMessages();
    private final GameConfigLocations locations = Plugin.getLocations();

    @Default
    @Description("show all color war command's")
    public void onDefault(CommandSender sender) {
        sender.sendMessage("You got " + sender.getName());
    }

    @Subcommand("join")
    @Description("Join to Color War event.")
    @CommandPermission("cw.command.join")
    public void onJoin(CommandSender sender) {
        Player player = (Player) sender;
        if (!colorWarManager.isRunning()) {
            player.sendMessage(messages.sendWarNotOpenMessage());
            return;
        }

        if (colorWarManager.isParticipating(player.getName())) {
            player.sendMessage(messages.sendAlreadyInWarMessage());
            return;
        }

        if (colorWarManager.isWarStarted()) {
            player.sendMessage(messages.sendTooLateToJoinMessage());
            return;
        }

        if (!player.getInventory().isEmpty()) {
            player.sendMessage(messages.sendMustEmptyInventoryMessage());
            return;
        }

        colorWarManager.playerJoin(player);
    }

    @Subcommand("start")
    @Description("Open the Color War for participation.")
    @CommandPermission("cw.command.start")
    public void onStart(CommandSender sender) {
        Player player = (Player) sender;
        if (colorWarManager.isRunning()) {
            player.sendMessage(messages.sendCantStartBecauseRunningMessage());
            return;
        }

        colorWarManager.openWar();
        ColorWarManager.bukkitRunnable = new StartWarRunnable();
        ColorWarManager.bukkitRunnable.runTaskTimer(Plugin.getInstance(), 0, 20 * 5);
    }

    @Subcommand("stop")
    @Description("Close the Color War event.")
    @CommandPermission("cw.command.stop")
    public void onStop(CommandSender sender) {
        Player player = (Player) sender;
        if (!colorWarManager.isRunning()) {
            player.sendMessage(messages.sendCantStopBecauseNotRunningMessage());
            return;
        }

        colorWarManager.stopWar(Cause.CLOSE_BY_OPERATOR);
        ColorWarManager.bukkitRunnable.cancel();
        ColorWarManager.bukkitRunnable = null;
    }

    @Subcommand("set")
    @Description("Set the Color War event location's.")
    @CommandCompletion("lobby|arena|exit")
    @CommandPermission("cw.command.set")
    public void onSetLocation(CommandSender sender, String location) {
        Player player = (Player) sender;
        boolean isSaved = locations.setLocation(location, player.getLocation());

        if (isSaved) {
            player.sendMessage(messages.sendNewLocationSet(location));
            locations.update();
        } else {
            player.sendMessage(messages.sendLocationNotFound(location));
        }

    }
}
