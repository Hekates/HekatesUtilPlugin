package ch.hekates.hekatesutilsplugin.commands;

import ch.hekates.hekatesutilsplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class InvseeCommand implements CommandExecutor {

    static Configuration config = Main.getPlugin().getConfig();
    private static final String INV_PREFIX = "§aINV ";
    private static String prefix = INV_PREFIX + Main.getPlugin().PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + ChatColor.RED + config.getString("no-player-message"));
            return true;
        }
        if (!sender.hasPermission("hup.invsee")) {
            sender.sendMessage(prefix + ChatColor.RED + config.getString("no-permission-message")
                    .replaceAll("%permission", "§lhup.invsee§r" + ChatColor.RED));
            return true;
        }
        if (args.length > 0) {
            if (args.length == 2) {
                if (Objects.equals(args[1], "-s") && Bukkit.getPlayer(args[0]) != null) {
                    openInventory((Player) sender, Bukkit.getPlayer(args[0]), true);
                } else {
                    Player player = (Player) sender;
                    player.sendMessage(prefix + ChatColor.RED + config.getString("false-player-error")
                            .replaceAll("%player", args[0]));
                }
            } else if (Bukkit.getPlayer(args[0]) != null) {
                openInventory((Player) sender, Bukkit.getPlayer(args[0]), false);
            } else {
                Player player = (Player) sender;
                player.sendMessage(prefix + ChatColor.RED + config.getString("false-player-error")
                        .replaceAll("%player", args[0]));
            }
        } else {
            openInventory((Player) sender, (Player) sender, true);
        }
        return false;
    }

    public static void openInventory(Player sender, Player target, boolean silent) {
        String zusatz = "";

        if (silent) {
            zusatz = " §8§osilent";
        }

        if (sender != target) {
            sender.sendMessage(prefix + ChatColor.GRAY + config.getString("invsee-notify")
                    .replaceAll("%inventory", "§5Inventar" + zusatz + ChatColor.GRAY)
                    .replaceAll("%player", ChatColor.YELLOW + target.getDisplayName() + ChatColor.GRAY));
            sender.openInventory(target.getInventory());
            sender.playSound(sender.getLocation(), Sound.ITEM_AXE_WAX_OFF, 1, 0);
            sender.playEffect(sender.getLocation(), Effect.COPPER_WAX_OFF, 0);
            if (!silent) {
                target.sendMessage(prefix + ChatColor.GRAY + config.getString("invsee-notify")
                        .replaceAll("%inventory", "§aInventar" + ChatColor.GRAY)
                        .replaceAll("%player", ChatColor.YELLOW + sender.getDisplayName() + ChatColor.GRAY));
            }
        } else {
            sender.sendMessage(prefix + ChatColor.RED + config.getString("invsee-self-error")
                    .replaceAll("%inventory", "§aInventar" + ChatColor.RED));
        }
    }
}
