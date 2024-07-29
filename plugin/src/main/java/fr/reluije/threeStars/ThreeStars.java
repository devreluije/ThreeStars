package fr.reluije.threeStars;

import fr.reluije.threeStars.commands.PlayerCommand;
import fr.reluije.threeStars.listeners.PlayerListener;
import fr.reluije.threeStars.web.WebAccess;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class ThreeStars extends JavaPlugin {

    private WebAccess webAccess;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        webAccess = new WebAccess();
        webAccess.load(getConfig());

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        registerCommand("player", new PlayerCommand(this));
    }

    @Override
    public void onDisable() {
        webAccess.close();
    }

    public WebAccess getWebAccess() {
        return webAccess;
    }

    @SuppressWarnings("SameParameterValue")
    private void registerCommand(String command, TabExecutor executor) {
        PluginCommand pluginCommand = getCommand(command);

        if (pluginCommand == null) {
            getLogger().log(Level.WARNING, "Command {} not found", command);
            return;
        }
        pluginCommand.setExecutor(executor);
        pluginCommand.setTabCompleter(executor);
    }
}
