package xyz.n7mn.dev.gunwar;

import org.bukkit.Bukkit;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.n7mn.dev.gunwar.commands.AboutGunWarCommand;
import xyz.n7mn.dev.gunwar.listeners.PlayerListener;
import xyz.n7mn.dev.gunwar.listeners.ServerListener;
import xyz.n7mn.dev.gunwar.util.GwUtilities;
import xyz.n7mn.dev.gunwar.util.Utilities;

public final class NanamiGunWar extends JavaPlugin {

    private NanamiGunWar plugin;
    private SimplePluginManager pluginManager;
    private GwUtilities utilities;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        GunWar.plugin = plugin;
        utilities = new GwUtilities(this);
        GunWar.utilities = utilities;
        pluginManager = (SimplePluginManager) Bukkit.getPluginManager();
        registerListeners();
        registerCommands();
    }

    private void registerListeners() {
        pluginManager.registerEvents(new PlayerListener(), plugin);
        pluginManager.registerEvents(new ServerListener(), plugin);
    }

    private void registerCommands() {
        utilities.registerCommand(plugin.getName(), new AboutGunWarCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
