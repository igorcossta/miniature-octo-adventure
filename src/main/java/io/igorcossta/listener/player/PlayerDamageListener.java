package io.igorcossta.listener.player;

import io.igorcossta.Plugin;
import io.igorcossta.event.custom.PlayerDieInWarEvent;
import io.igorcossta.manager.ColorWarManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class PlayerDamageListener implements org.bukkit.event.Listener {
    private final Plugin plugin = Plugin.getInstance();
    private final ColorWarManager colorWarManager = Plugin.getColorWarManager();

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (!colorWarManager.isRunning()) return; // if event is not running then do nothing
        if (!(event.getEntity() instanceof Player victim) || !(event.getDamager() instanceof Player attacker))
            return;

        if (!colorWarManager.isParticipating(victim.getName()) || !colorWarManager.isParticipating(attacker.getName()))
            return; // if victim's and attacker's is not in the event then do nothing

        // when participants are in the lobby the PvP is not allowed
        if (!colorWarManager.isWarStarted()) {
            event.setCancelled(true);
            attacker.sendMessage(Component.text("The war is not started yet!"));
            return;
        }

        event.setDamage(0);
        ItemStack attackerItemInMainHand = attacker.getInventory().getItemInMainHand(); // the attacker's weapon
        ItemStack[] playerWhoSufferAttackArmor = victim.getInventory().getArmorContents(); // the victim's armor set

        Color attackerDyeColor = dyeColorToBukkitColor(attackerItemInMainHand.getType());
        if (attackerDyeColor == null) {
            return; // Attacker's item is not a dye
        }

        // if victim's there's no equipment then remove him from the event
        if (isVictimWearingNothing(victim)) {
            Bukkit.getLogger().info("Player " + victim.getName() + " is not wearing any armor.");
            // call the event when player die in color war
            PlayerDieInWarEvent playerDieInWarEvent = new PlayerDieInWarEvent(victim);
            plugin.getServer().getPluginManager().callEvent(playerDieInWarEvent);
            return;
        }

        // reduce the durability of the victim's armor based on the attacker's color dye
        for (ItemStack armorPiece : playerWhoSufferAttackArmor) {

            if (isColoredLeatherArmor(armorPiece)) {
                LeatherArmorMeta armorMeta = (LeatherArmorMeta) armorPiece.getItemMeta();
                Color armorColor = armorMeta.getColor();

                if (armorColor.equals(attackerDyeColor)) {
                    reduceArmorDurability(armorPiece, 20); // Reduce durability by 5
                    Bukkit.getLogger().info("Attacker " + attacker.getName() + " used a matching dye item against " + victim.getName() + "'s armor: " + armorPiece.getType().name());
                }
            }

        }
    }

    private boolean isVictimWearingNothing(Player victim) {
        ItemStack helmet = victim.getInventory().getHelmet();
        ItemStack chestplate = victim.getInventory().getChestplate();
        ItemStack leggings = victim.getInventory().getLeggings();
        ItemStack boots = victim.getInventory().getBoots();

        return helmet == null && chestplate == null && leggings == null && boots == null;
    }

    private void reduceArmorDurability(ItemStack armorPiece, int amount) {
        if (armorPiece.getType().getMaxDurability() - armorPiece.getDurability() <= amount) {
            armorPiece.setAmount(0); // Remove the armor piece
        } else {
            armorPiece.setDurability((short) (armorPiece.getDurability() + amount));
        }
    }

    private boolean isColoredLeatherArmor(ItemStack itemStack) {
        return checkArmor(itemStack);
    }

    private boolean checkArmor(ItemStack itemStack) {
        return itemStack != null && itemStack.getType().name().contains("LEATHER_");
    }

    private Color dyeColorToBukkitColor(Material dyeType) {
        return switch (dyeType) {
            case BLUE_DYE -> Color.fromRGB(0, 0, 255);
            case YELLOW_DYE -> Color.fromRGB(255, 255, 0);
            case LIME_DYE -> Color.fromRGB(0, 255, 0);
            case RED_DYE -> Color.fromRGB(255, 0, 0);
            default -> null;
        };
    }
}
