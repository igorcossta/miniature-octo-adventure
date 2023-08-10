package io.igorcossta.command.registry;

import io.igorcossta.Plugin;
import io.igorcossta.command.AdminCommand;
import io.igorcossta.command.PlayerCommand;
import lombok.Data;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.command.message.MessageHolder;
import me.saiintbrisson.minecraft.command.message.MessageType;
import org.bukkit.Bukkit;

@Data(staticConstructor = "of")
public class CommandRegistry {
    private final BukkitFrame bukkitFrame = Plugin.getBukkitFrame();

    public void register() {
        try {
            bukkitFrame.registerCommands(
                    new PlayerCommand(),
                    new AdminCommand()
            );

            MessageHolder messageHolder = bukkitFrame.getMessageHolder();
            messageHolder.setMessage(MessageType.NO_PERMISSION, "You don't have permission to execute this command!");
            messageHolder.setMessage(MessageType.INCORRECT_TARGET, "You are not allowed to execute this command (only {target})!");
            messageHolder.setMessage(MessageType.INCORRECT_USAGE, "Try: {usage}");
            messageHolder.setMessage(MessageType.ERROR, "Something went wrong with this command. Contact an operator.");

        } catch (Exception e) {
            Bukkit.getLogger().warning("[ColorWar] something wrong registering commands!");
        }
    }
}
