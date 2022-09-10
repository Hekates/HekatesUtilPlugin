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

public class EnderChestCommand implements CommandExecutor {

    static Configuration config = Main.getPlugin().getConfig();
    private static final String EC_PREFIX = "§5EC ";
    private static String prefix = EC_PREFIX + Main.getPlugin().PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(prefix + ChatColor.RED + config.getString("no-player-message"));
                return true;
            }
        if (!sender.hasPermission("hup.enderchest")) {
            sender.sendMessage(prefix + ChatColor.RED + config.getString("no-permission-message")
                    .replaceAll("%permission", "§lhup.enderchest§r" + ChatColor.RED));
            return true;
        }
            if (args.length > 0) {
                if (!sender.hasPermission("hup.enderchest.others")) {
                    sender.sendMessage(prefix + ChatColor.RED + config.getString("no-permission-message")
                            .replaceAll("%permission", "§lhup.enderchest.others§r" + ChatColor.RED));
                } else if (args.length == 2) {
                    if (Objects.equals(args[1], "-s") && Bukkit.getPlayer(args[0]) != null) {
                        if (!sender.hasPermission("hup.enderchest.silent")){
                            sender.sendMessage(prefix + ChatColor.RED + config.getString("no-permission-message")
                                            .replaceAll("%permission", "§lhup.enderchest.silent§r" + ChatColor.RED));
                            return true;
                        }
                        openEnderchest((Player) sender, Bukkit.getPlayer(args[0]), true);
                    } else {
                        Player player = (Player) sender;
                        player.sendMessage(prefix + ChatColor.RED + config.getString("false-player-error")
                                .replaceAll("%player", args[0]));
                    }
                } else if(Bukkit.getPlayer(args[0]) != null) {
                    openEnderchest((Player) sender, Bukkit.getPlayer(args[0]), false);
                }else {
                    Player player = (Player) sender;
                    player.sendMessage(prefix + ChatColor.RED + config.getString("false-player-error")
                            .replaceAll("%player", args[0]));
                }
            } else {
                openEnderchest((Player) sender, (Player) sender, true);
            }
        return false;
    }

    public static void openEnderchest(Player sender, Player target, boolean silent) {
        sender.openInventory(target.getEnderChest());
        sender.playSound(sender.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
        sender.playEffect(sender.getLocation(), Effect.ENDER_SIGNAL, 0);
        String zusatz = " §8§osilent";
        if (sender != target) {
            sender.sendMessage(prefix + ChatColor.GRAY + config.getString("enderchest-confirm")
                    .replaceAll("%enderchest", "§5Enderchest" + ChatColor.GRAY)
                    .replaceAll("%player", ChatColor.YELLOW + target.getName().toString() + zusatz + ChatColor.GRAY));
            if (!silent) {
                sender.sendMessage(prefix + ChatColor.GRAY + config.getString("enderchest-confirm")
                        .replaceAll("%enderchest", "§5Enderchest" + ChatColor.GRAY)
                        .replaceAll("%player",  ChatColor.YELLOW + target.getDisplayName() + ChatColor.GRAY));
                target.sendMessage(prefix + ChatColor.GRAY + config.getString("enderchest-notify")
                        .replaceAll("%enderchest", "§5Enderchest" + ChatColor.GRAY)
                        .replaceAll("%player", ChatColor.YELLOW + sender.getDisplayName() + ChatColor.GRAY));
            }
        } else {
            sender.sendMessage(prefix + ChatColor.GRAY + config.getString("enderchest-self")
                    .replaceAll("%enderchest", "§5Enderchest" + ChatColor.GRAY));
        }
    }
}