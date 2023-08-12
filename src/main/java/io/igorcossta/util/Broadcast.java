package io.igorcossta.util;

import io.igorcossta.Plugin;
import io.igorcossta.manager.ColorWarManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

import java.util.Objects;

public class Broadcast {
    private final ColorWarManager manager = Plugin.getColorWarManager();

    public void toParticipants(Component component) {
        manager.getParticipants().stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(player -> player.sendMessage(component));
    }

    public void toServer(Component component) {
        Bukkit.getServer().sendMessage(component);
    }
}
