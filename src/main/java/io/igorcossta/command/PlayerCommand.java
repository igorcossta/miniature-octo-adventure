package io.igorcossta.command;

import io.igorcossta.Plugin;
import io.igorcossta.manager.ColorWarManager;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class PlayerCommand {
    private final ColorWarManager colorWarManager = Plugin.getColorWarManager();

    @Command(
            name = "join",
            usage = "/join",
            description = "command to join in to color war event",
            target = CommandTarget.PLAYER
    )
    public void joinCommand(Context<Player> context) {
        Player p = context.getSender();

        if (!colorWarManager.isRunning()) {
            p.sendMessage(Component.text("The Color War event is not open. Try later!"));
            return;
        }

        if (colorWarManager.isParticipating(p.getName())) {
            p.sendMessage(Component.text("You are already participating"));
            return;
        }

        if (colorWarManager.isWarStarted()) {
            p.sendMessage(Component.text("You can't join in the event anymore"));
            return;
        }

        if (!p.getInventory().isEmpty()) {
            p.sendMessage(Component.text("You must empty your inventory before join the event."));
            return;
        }

        colorWarManager.playerJoin(p);
    }
}
