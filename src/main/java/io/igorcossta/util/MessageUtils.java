package io.igorcossta.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class MessageUtils {
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static Component parse(String input, String prefix) {
        return miniMessage.deserialize(prefix + " " + input);
    }
}
