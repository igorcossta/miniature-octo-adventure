package io.igorcossta;

import io.igorcossta.command.registry.CommandRegistry;
import io.igorcossta.listener.registry.ListenerRegistry;
import io.igorcossta.manager.ColorWarManager;
import lombok.Getter;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.bukkit.command.executor.BukkitSchedulerExecutor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {
    @Getter
    private static Plugin instance;
    @Getter
    private static BukkitFrame bukkitFrame;
    @Getter
    private static Economy econ;
    @Getter
    private static ColorWarManager colorWarManager;

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            Bukkit.getLogger().severe("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        instance = this;
        bukkitFrame = new BukkitFrame(this);
        colorWarManager = new ColorWarManager();

        bukkitFrame.setExecutor(new BukkitSchedulerExecutor(this));
        CommandRegistry.of().register();
        ListenerRegistry.of().register();
    }

    @Override
    public void onDisable() {
        getColorWarManager().getTaskWar().cancel();
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
