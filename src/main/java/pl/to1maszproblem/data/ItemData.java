package pl.to1maszproblem.data;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.to1maszproblem.config.ConfigStorage;
import pl.to1maszproblem.utils.TextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemData {

    public static ItemStack load() {
        ItemStack fireworkNoLimit = new ItemStack(Material.FIREWORK_ROCKET);
        if (ConfigStorage.FIREWORK_GLOW) {
            fireworkNoLimit.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            ItemMeta itemMeta1 = fireworkNoLimit.getItemMeta();
            assert itemMeta1 != null;
            itemMeta1.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            fireworkNoLimit.setItemMeta(itemMeta1);
        }
        ItemMeta itemMeta = fireworkNoLimit.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(TextUtil.fixColor(ConfigStorage.FIREWORK_NAME));
        List<String> lore = new ArrayList<>(ConfigStorage.FIREWORK_LORE);
        itemMeta.setLore(lore.stream().map(TextUtil::fixColor).collect(Collectors.toList()));
        fireworkNoLimit.setItemMeta(itemMeta);
        return fireworkNoLimit;
    }
}
