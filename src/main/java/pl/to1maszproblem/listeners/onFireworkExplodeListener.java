package pl.to1maszproblem.listeners;

import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FireworkExplodeEvent;
import pl.to1maszproblem.data.ItemData;

public class onFireworkExplodeListener implements Listener {
    @EventHandler
    public void onFireworkExplode(FireworkExplodeEvent event) {
        Firework firework = (Firework) event.getEntity();
        String meta = firework.getFireworkMeta().getDisplayName();
        if (meta.equals(ItemData.load().getItemMeta().getDisplayName())) {
            event.setCancelled(true);
        }
    }
}
