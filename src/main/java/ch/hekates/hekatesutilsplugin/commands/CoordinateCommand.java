package ch.hekates.hekatesutilsplugin.commands;

import ch.hekates.hekatesutilsplugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

public class CoordinateCommand implements CommandExecutor {

    static Configuration config = Main.getPlugin().getConfig();
    private static final String COORD_PREFIX = "§bCOORDS ";
    private static String prefix = COORD_PREFIX + Main.getPlugin().PREFIX;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + ChatColor.RED + config.getString("no-player-message"));
            return true;
        }
        if (!sender.hasPermission("hup.coordinates")) {
            sender.sendMessage(prefix + ChatColor.RED + config.getString("no-permission-message")
                    .replaceAll("%permission", "§lhup.coordinates§r" + ChatColor.RED));
            return true;
        }
        Player player = (Player)sender;

        return true;
    }
}