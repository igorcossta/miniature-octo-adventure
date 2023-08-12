package io.igorcossta.listener.player;

import io.igorcossta.Plugin;
import io.igorcossta.config.GameConfigLocations;
import io.igorcossta.config.GameConfigMessages;
import io.igorcossta.event.custom.PlayerJoinWarEvent;
import io.igorcossta.manager.ColorWarManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class PlayerJoinWarListener implements Listener {
    private final ColorWarManager colorWarManager = Plugin.getColorWarManager();
    private final GameConfigLocations locations = Plugin.getLocations();
    private final GameConfigMessages messages = Plugin.getMessages();

    @EventHandler
    public void onPlayerJoinWarEvent(PlayerJoinWarEvent event) {
        Player p = event.getPlayer();
        p.teleport(locations.getJoinLocation());
        colorWarManager.broadcastToEvent(messages.sendJoinMessage(p.getName()));

        // Create a blue leather helmet
        ItemStack helmet = createColoredArmor(Material.LEATHER_HELMET, 0, 0, 0xFF);
        // Create a red leather chestplate
        ItemStack chestplate = createColoredArmor(Material.LEATHER_CHESTPLATE, 0xFF, 0, 0);
        // Create a yellow leather leggings
        ItemStack leggings = createColoredArmor(Material.LEATHER_LEGGINGS, 0xFF, 0xFF, 0);
        // Create a green leather boots
        ItemStack boots = createColoredArmor(Material.LEATHER_BOOTS, 0, 0xFF, 0);

        // Give the player the armor
        p.getInventory().setHelmet(helmet);
        p.getInventory().setChestplate(chestplate);
        p.getInventory().setLeggings(leggings);
        p.getInventory().setBoots(boots);

        // Give the player the dye's
        p.getInventory().addItem(new ItemStack(Material.BLUE_DYE, 1));
        p.getInventory().addItem(new ItemStack(Material.RED_DYE, 1));
        p.getInventory().addItem(new ItemStack(Material.YELLOW_DYE, 1));
        p.getInventory().addItem(new ItemStack(Material.LIME_DYE, 1));
    }

    private ItemStack createColoredArmor(Material armorType, int red, int green, int blue) {
        ItemStack armor = new ItemStack(armorType);
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) armor.getItemMeta();
        armorMeta.setColor(org.bukkit.Color.fromRGB(red, green, blue));
        armor.setItemMeta(armorMeta);
        return armor;
    }
}
