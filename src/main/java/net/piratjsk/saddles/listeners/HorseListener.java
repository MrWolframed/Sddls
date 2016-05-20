package net.piratjsk.saddles.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import net.piratjsk.saddles.Saddles;

public class HorseListener implements Listener {

    @EventHandler
    public void onHorseAccess(final PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Horse) {
            final Horse horse = (Horse) event.getRightClicked();
            final ItemStack saddle = horse.getInventory().getSaddle();
            if (saddle!=null && ! Saddles.hasAccess(saddle, event.getPlayer())) {
                event.setCancelled(true);
                String owner = Saddles.getOwner(saddle).getName();
                if (Saddles.getOwner(saddle).isOnline())
                    owner = ((Player) Saddles.getOwner(saddle)).getDisplayName();
                event.getPlayer().sendMessage("Ten wierzchowiec nalezy do gracza " + owner + ".");
            }
        }
    }

    @EventHandler
    public void onHorseDamage(final EntityDamageEvent event) {
        if (event.getEntityType().equals(EntityType.HORSE)) {
            final Horse horse = (Horse) event.getEntity();
            final ItemStack saddle = horse.getInventory().getSaddle();
            if (saddle.getItemMeta().hasLore()) {
                if (horse.getPassenger()!=null) {
                    Entity damager = null;
                    if (event instanceof EntityDamageByEntityEvent)
                        damager = ((EntityDamageByEntityEvent) event).getDamager();
                    if (damager != null && damager instanceof Player)
                        event.setCancelled(true);
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

}
