package io.igorcossta.config;

import de.exlll.configlib.Configuration;
import de.exlll.configlib.YamlConfigurations;
import io.igorcossta.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.nio.file.Path;

@Configuration
public class GameConfigLocations {
    private String joinLocation = serialize(new Location(Bukkit.getWorld("world"), 0, 0, 0, 0, 0));

    private String battleLocation = serialize(new Location(Bukkit.getWorld("world"), 0, 0, 0, 0, 0));

    private String exitLocation = serialize(new Location(Bukkit.getWorld("world"), 0, 0, 0, 0, 0));

    public Location getJoinLocation() {
        return deserialize(joinLocation);
    }

    public Location getBattleLocation() {
        return deserialize(battleLocation);
    }

    public Location getExitLocation() {
        return deserialize(exitLocation);
    }

    private void setJoinLocation(Location newJoinLocation) {
        this.joinLocation = serialize(newJoinLocation);
    }

    private void setBattleLocation(Location newBattleLocation) {
        this.battleLocation = serialize(newBattleLocation);
    }

    private void setExitLocation(Location newExitLocation) {
        this.exitLocation = serialize(newExitLocation);
    }

    public boolean setLocation(String location, Location coords) {
        if (location.equalsIgnoreCase("lobby")) {
            setJoinLocation(coords);
            return true;
        } else if (location.equalsIgnoreCase("arena")) {
            setBattleLocation(coords);
            return true;
        } else if (location.equalsIgnoreCase("exit")) {
            setExitLocation(coords);
            return true;
        } else {
            return false;
        }
    }

    public void update() {
        Path path = new File(Plugin.getInstance().getDataFolder(), "locations.yaml").toPath();
        YamlConfigurations.save(path, GameConfigLocations.class, this);
    }

    private String serialize(Location location) {
        String worldName = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        return worldName + ";" + x + ";" + y + ";" + z + ";" + yaw + ";" + pitch;
    }

    private Location deserialize(String s) {
        String[] split = s.split(";");
        World world = Bukkit.getWorld(split[0]);
        double x = Double.parseDouble(split[1]);
        double y = Double.parseDouble(split[2]);
        double z = Double.parseDouble(split[3]);
        float yaw = Float.parseFloat(split[4]);
        float pitch = Float.parseFloat(split[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }

}
