package io.igorcossta.manager;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Getter
public class ColorWarLocations {
    // the place where the player will be teleported to wait the event start
    private final Location joinLocation = new Location(Bukkit.getWorld("world"), -3.523, -62.000, -1.471);

    // the place where the player will be teleported when the war start
    private final Location battleLocation = new Location(Bukkit.getWorld("world"), -6.427, -62.000, -1.375);

    // the place where the player will be teleported when die or event end
    private final Location exitLocation = new Location(Bukkit.getWorld("world"), -9.553, -62.000, -1.373);
}
