package pl.to1maszproblem.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.to1maszproblem.config.ConfigStorage;
import pl.to1maszproblem.data.ItemData;
import pl.to1maszproblem.utils.ChatType;
import pl.to1maszproblem.utils.TextUtil;

import java.util.ArrayList;
import java.util.List;

public class FireworkCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission(ConfigStorage.PERMISSION_PERMISSION)) {
                TextUtil.sendMessage(ChatType.CHAT, player, ConfigStorage.PERMISSION_MESSAGE);
                return true;
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("give")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        sender.sendMessage(TextUtil.fixColor("&cPodany gracz nie jest online!"));
                        return true;
                    }
                    if (!isInt(args[2])) {
                        sender.sendMessage(TextUtil.fixColor("&cMusisz podac liczbe calkowita!"));
                        return true;
                    }
                    int amount = Integer.parseInt(args[2]);
                    ItemStack itemStack = ItemData.load();
                    itemStack.setAmount(amount);
                    target.getInventory().addItem(itemStack);
                    TextUtil.sendMessage(ChatType.CHAT, player, "&aPomyslnie nadano przedmiot graczowi &f" + target.getName());
                }
            } else {
                TextUtil.sendMessage(ChatType.CHAT, player, ConfigStorage.USAGE$MESSAGE);
            }
        }
        return false;
    }

    private boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> argscmd = new ArrayList<>();
            argscmd.add("give");
            return argscmd;
        }
        if (args.length == 2) return sender.getServer().getOnlinePlayers().stream().map(Player::getName).toList();
        if (args.length == 3) {
            List<String> argscmd = new ArrayList<>();
            argscmd.add("1");
            argscmd.add("10");
            argscmd.add("50");
            argscmd.add("100");
            return  argscmd;
        }
        return null;
    }
}
