package io.igorcossta.listener.registry;

import io.igorcossta.Plugin;
import io.igorcossta.listener.player.*;
import io.igorcossta.listener.war.EventWarOpenListener;
import io.igorcossta.listener.war.EventWarStartedListener;
import io.igorcossta.listener.war.EventWarStopListener;
import lombok.Data;
import org.bukkit.plugin.PluginManager;

@Data(staticConstructor = "of")
public class ListenerRegistry {
    private final PluginManager pluginManager = Plugin.getInstance().getServer().getPluginManager();
    private final Plugin plugin = Plugin.getInstance();

    public void register() {
        pluginManager.registerEvents(new PlayerDamageListener(), plugin);
        pluginManager.registerEvents(new PlayerDieInWarListener(), plugin);
        pluginManager.registerEvents(new PlayerInventoryClickListener(), plugin);
        pluginManager.registerEvents(new PlayerJoinWarListener(), plugin);
        pluginManager.registerEvents(new PlayerQuitWarListener(), plugin);
        pluginManager.registerEvents(new EventWarStopListener(), plugin);
        pluginManager.registerEvents(new EventWarStartedListener(), plugin);
        pluginManager.registerEvents(new EventWarOpenListener(), plugin);
        pluginManager.registerEvents(new PlayerWinListener(), plugin);
    }
}
