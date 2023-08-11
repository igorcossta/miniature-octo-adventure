package io.igorcossta.command;

import io.igorcossta.Plugin;
import io.igorcossta.config.GameConfigMessages;
import io.igorcossta.manager.ColorWarManager;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

public class PlayerCommand {
    private final ColorWarManager colorWarManager = Plugin.getColorWarManager();
    private final GameConfigMessages messages = Plugin.getConfigurationManager().getGameMessages();

    @Command(
            name = "join",
            usage = "/join",
            description = "command to join in to color war event",
            target = CommandTarget.PLAYER
    )
    public void joinCommand(Context<Player> context) {
        Player p = context.getSender();

        if (!colorWarManager.isRunning()) {
            p.sendMessage(messages.sendWarNotOpenMessage());
            return;
        }

        if (colorWarManager.isParticipating(p.getName())) {
            p.sendMessage(messages.sendAlreadyInWarMessage());
            return;
        }

        if (colorWarManager.isWarStarted()) {
            p.sendMessage(messages.sendTooLateToJoinMessage());
            return;
        }

        if (!p.getInventory().isEmpty()) {
            p.sendMessage(messages.sendMustEmptyInventoryMessage());
            return;
        }

        colorWarManager.playerJoin(p);
    }
}
