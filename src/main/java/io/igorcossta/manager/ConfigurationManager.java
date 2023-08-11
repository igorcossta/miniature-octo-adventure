package io.igorcossta.manager;

import io.igorcossta.config.GameConfigMessages;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;

@RequiredArgsConstructor
public class ConfigurationManager {
    @Getter
    private final GameConfigMessages gameMessages;
    private final Path gameConfigMessagesPath;

//    public void save() {
//        YamlConfigurations.save(gameConfigMessagesPath, GameConfigMessages.class, gameMessages);
//    }
}
