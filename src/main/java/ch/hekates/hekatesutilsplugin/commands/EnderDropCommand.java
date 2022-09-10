package ch.hekates.hekatesutilsplugin.commands;

import ch.hekates.hekatesutilsplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnderDropCommand implements CommandExecutor {

    Configuration config = Main.getPlugin().getConfig();
    private final String ED_PREFIX = "§5§lE§9D ";
    private String prefix = ED_PREFIX + Main.getPlugin().PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(prefix + ChatColor.RED + config.getString("no-player-message"));
                return true;
            }
            Player player = (Player) sender;

            if (!(player.hasPermission("hup.enderdrop"))){
                player.sendMessage(prefix + ChatColor.RED + config.getString("no-permission-message")
                        .replaceAll("%permission", "§lhup.enderdrop§r" + ChatColor.RED));
                return true;
            }

            if (args.length > 0) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    Player target = Bukkit.getPlayer(args[0]);
                    int transferAmount = player.getInventory().getItemInMainHand().getAmount();
                    ItemStack transferItem = new ItemStack(player.getInventory().getItemInMainHand().getType(), transferAmount);

                    String name = transferItem.getType().name().replaceAll("_"," ").toUpperCase();


                    if (target == player){
                        player.sendMessage( prefix + "§7" + config.get("enderdrop-self-delivery"));
                        return true;
                    }
                    if (transferItem.getType() == Material.AIR){
                        player.sendMessage( prefix + "§c" + config.get("enderdrop-no-item-in-hand"));
                        return true;
                    }

                    if (target.getInventory().firstEmpty() != -1) {
                        target.getInventory().addItem(transferItem);
                        player.getInventory().remove(transferItem);
                        player.sendMessage(prefix + "§7" + config.getString("enderdrop-delivered")
                                .replaceAll("%player", "§e" + target.getName().toString() + "§r§7")
                                .replaceAll("%enderdrop", "§5§lEnder§9Drop§r§7"));
                        target.sendMessage(prefix + "§7" + config.getString("enderdrop-recieved")
                                .replaceAll("%transferamount", "§e§l" + transferAmount)
                                .replaceAll("%itemname", name + "§r§7").replaceAll("%enderdrop", "§5§lEnder§9Drop§r§7")
                                .replaceAll("%sender", "§e" + player.getName() + "§7"));

                    } else {
                        player.sendMessage(prefix + "§cDu kannst dem Spieler: §e§l"
                                + args[0] + " §ckein Item übergeben, da dieser keinen freien Slot im Inventar hat!");
                    }
                } else {
                    player.sendMessage(prefix + "§cDer Spieler: §e§l" + args[0] + " §cist nicht online oder existiert nicht!");
                }
            } else{
                player.sendMessage(prefix + "§cDu musst eine Person angeben, der du das Item überreichen willst!");
                return true;
            }
        return false;
    }
}
