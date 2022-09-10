package ch.hekates.hekatesutilsplugin;

import ch.hekates.hekatesutilsplugin.commands.EnderChestCommand;
import ch.hekates.hekatesutilsplugin.commands.EnderDropCommand;
import ch.hekates.hekatesutilsplugin.commands.InvseeCommand;
import ch.hekates.hekatesutilsplugin.commands.StatsCommand;
import ch.hekates.languify.Languify;
import ch.hekates.languify.language.LangLoader;
import ch.hekates.languify.language.Text;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class Main extends JavaPlugin {

    private static Main plugin;

    public final String PREFIX = "§8>> ";

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        ConsoleCommandSender console = plugin.getServer().getConsoleSender();

        Languify.setup(plugin, plugin.getDataFolder().getAbsolutePath());
        LangLoader.loadLanguage(getConfig().getString("language"));

        getCommand("enderdrop").setExecutor(new EnderDropCommand());
        getCommand("enderchest").setExecutor(new EnderChestCommand());
        getCommand("invsee").setExecutor(new InvseeCommand());
        getCommand("statistics").setExecutor(new StatsCommand());

        try {
            console.sendMessage(Text.get("hi"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Main getPlugin() {return plugin; }
}
