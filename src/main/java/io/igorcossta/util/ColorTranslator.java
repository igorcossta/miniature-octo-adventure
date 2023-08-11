package io.igorcossta.util;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

@Deprecated(forRemoval = true, since = "1.0-SNAPSHOT")
public class ColorTranslator {
    public static String colored(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String[] colored(String... messages) {
        for (int i = 0; i < messages.length; i++) {
            messages[i] = colored(messages[i]);
        }
        return messages;
    }

    public static List<String> colored(List<String> description) {
        return description.stream()
                .map(ColorTranslator::colored)
                .collect(Collectors.toList());
    }
}
