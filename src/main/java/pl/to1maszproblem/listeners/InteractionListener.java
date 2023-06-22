package pl.to1maszproblem.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.to1maszproblem.data.ItemData;

public class InteractionListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(player.getItemInHand().isSimilar(ItemData.load())) {
            ItemStack item = player.getItemInHand();
            ItemMeta itemMeta = item.getItemMeta();
            if (player.isGliding()) {
                player.setVelocity(player.getLocation().getDirection().multiply(2.0));
                player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1f, 1f);
            }
            event.setCancelled(true);
        }
    }
}
