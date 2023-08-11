package io.igorcossta;

import de.exlll.configlib.ConfigLib;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import io.igorcossta.command.registry.CommandRegistry;
import io.igorcossta.config.GameConfigMessages;
import io.igorcossta.listener.registry.ListenerRegistry;
import io.igorcossta.manager.ColorWarManager;
import io.igorcossta.manager.ConfigurationManager;
import lombok.Getter;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.bukkit.command.executor.BukkitSchedulerExecutor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.Path;

public final class Plugin extends JavaPlugin {
    @Getter
    private static Plugin instance;
    @Getter
    private static BukkitFrame bukkitFrame;
    @Getter
    private static Economy econ;
    @Getter
    private static ColorWarManager colorWarManager;
    @Getter
    private static ConfigurationManager configurationManager;

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            Bukkit.getLogger().severe("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // set up the config
        YamlConfigurationProperties properties = ConfigLib.BUKKIT_DEFAULT_PROPERTIES.toBuilder().build();
        Path gameConfigMessagesPath = new File(getDataFolder(), "config.yaml").toPath();
        GameConfigMessages gameConfigMessages = YamlConfigurations.update(gameConfigMessagesPath, GameConfigMessages.class, properties);
        configurationManager = new ConfigurationManager(gameConfigMessages, gameConfigMessagesPath);

        instance = this;
        bukkitFrame = new BukkitFrame(this);
        colorWarManager = new ColorWarManager();

        bukkitFrame.setExecutor(new BukkitSchedulerExecutor(this));
        CommandRegistry.of().register();
        ListenerRegistry.of().register();
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
