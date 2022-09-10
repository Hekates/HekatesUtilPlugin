package ch.hekates.hekatesutilsplugin.commands;


import ch.hekates.hekatesutilsplugin.Main;
import ch.hekates.hekatesutilsplugin.utils.StatsUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {

    static Configuration config = Main.getPlugin().getConfig();
    private static final String STATS_PREFIX = "§eSTATS ";
    private static String prefix = STATS_PREFIX + Main.getPlugin().PREFIX;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(prefix + ChatColor.RED + config.getString("no-player-message"));
                return true;
            }
        if (!sender.hasPermission("hup.statistics")) {
            sender.sendMessage(prefix + ChatColor.RED + config.getString("no-permission-message")
                    .replaceAll("%permission", "§lhup.statistics§r" + ChatColor.RED));
            return true;
        }
            Player player = (Player)sender;
            //Sendet die Stats aus dem Stats util
            player.sendMessage(StatsUtil.sendStats(player));
        return true;
    }
}