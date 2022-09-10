package ch.hekates.hekatesutilsplugin.utils;

import ch.hekates.hekatesutilsplugin.Main;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class StatsUtil {

    public static String sendStats(Player player){
        StringBuilder stringbuilder = new StringBuilder();
        String stats = "§8--§e§lStatistics§r§8--§r\n";
        String prefix = Main.getPlugin().PREFIX;

        //Spawnpoint
        if (player.getBedSpawnLocation() != null) {
            stringbuilder.append(prefix + "§7Respawnpoint: §8x §e" + player.getBedSpawnLocation().getX() + " " + player.getBedSpawnLocation().getY() + " " + player.getBedSpawnLocation().getZ() + "\n");
        } else {
            stringbuilder.append(prefix + "§8>> §7Respawnpoint: §8x §c§lNICHT VORHANDEN\n");
        }
        //Ping
        stringbuilder.append(prefix + "§7Ping: §8x §e" + player.getPing() + " ms\n");

        //Playtime
        stringbuilder.append(prefix + "§7Playtime: §8x §e" + (PluginUtils.timeConvert(player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 1200)) + "\n");

        //Kills
        if (player.getStatistic(Statistic.PLAYER_KILLS) != 0) {
            stringbuilder.append(prefix + "§7Kills: §8x §e" + player.getStatistic(Statistic.PLAYER_KILLS) + "\n");
        } else {
            stringbuilder.append(prefix + "§7Kills: §8x §c§lKEINE KILLS VORHANDEN\n");
        }

        //Deaths
        if (player.getStatistic(Statistic.DEATHS) != 0) {
            stringbuilder.append(prefix + "§7Deaths: §8x §e" + player.getStatistic(Statistic.DEATHS) + "\n");
        } else {
            stringbuilder.append(prefix + "§7Deaths: §8x §c§lKEINE DEATHS VORHANDEN\n");
        }

        //Time since death
        if (player.getStatistic(Statistic.DEATHS) != 0) {
            stringbuilder.append(prefix + "§7Time since death: §8x §e" + (PluginUtils.timeConvert(player.getStatistic(Statistic.TIME_SINCE_DEATH) / 1200)) + "\n");
        } else {
            stringbuilder.append(prefix + "§7Time since death: §8x §c§lKEINE DEATHS VORHANDEN\n");
        }

        ///Build///
        stats = stringbuilder.toString();
        return stats;

    }
}