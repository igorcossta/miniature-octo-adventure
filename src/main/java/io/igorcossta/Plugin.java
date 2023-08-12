package io.igorcossta;

import co.aikar.commands.BukkitCommandIssuer;
import co.aikar.commands.ConditionFailedException;
import co.aikar.commands.PaperCommandManager;
import de.exlll.configlib.ConfigLib;
import de.exlll.configlib.NameFormatters;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import io.igorcossta.command.ColorWar;
import io.igorcossta.config.GameConfigLocations;
import io.igorcossta.config.GameConfigMessages;
import io.igorcossta.listener.registry.ListenerRegistry;
import io.igorcossta.manager.ColorWarManager;
import io.igorcossta.util.Broadcast;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class Plugin extends JavaPlugin {
    @Getter
    private static Plugin instance;
    @Getter
    private static Economy econ;
    @Getter
    private static ColorWarManager colorWarManager;
    @Getter
    private static GameConfigMessages messages;
    @Getter
    private static GameConfigLocations locations;
    @Getter
    private static Broadcast broadcast;

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            Bukkit.getLogger().severe("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // set up the config
        YamlConfigurationProperties properties = ConfigLib.BUKKIT_DEFAULT_PROPERTIES.toBuilder()
                .setNameFormatter(NameFormatters.UPPER_UNDERSCORE)
                .build();
        Path path;
        for (String cf : List.of("config", "locations")) {
            path = new File(getDataFolder(), cf + ".yaml").toPath();
            if (!Files.exists(path)) {
                if (messages == null)
                    messages = YamlConfigurations.update(path, GameConfigMessages.class, properties);
                else locations = YamlConfigurations.update(path, GameConfigLocations.class, properties);
            } else {
                if (messages == null)
                    messages = YamlConfigurations.load(path, GameConfigMessages.class);
                else locations = YamlConfigurations.load(path, GameConfigLocations.class);
            }
        }

        instance = this;
        colorWarManager = new ColorWarManager();
        broadcast = new Broadcast();

        ListenerRegistry.of().register();

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new ColorWar());
        manager.getCommandConditions().addCondition("player", context -> {
            BukkitCommandIssuer issuer = context.getIssuer();
            if (!issuer.isPlayer()) {
                throw new ConditionFailedException("sender must be a player");
            }
        });
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
