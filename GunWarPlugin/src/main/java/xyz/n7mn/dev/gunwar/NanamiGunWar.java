package xyz.n7mn.dev.gunwar;

import org.bukkit.Bukkit;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.n7mn.dev.api.NanamiNetwork;
import xyz.n7mn.dev.api.Role;
import xyz.n7mn.dev.api.data.RoleData;
import xyz.n7mn.dev.gunwar.commands.AboutGunWarCommand;
import xyz.n7mn.dev.gunwar.listeners.PlayerListener;
import xyz.n7mn.dev.gunwar.listeners.ServerListener;
import xyz.n7mn.dev.gunwar.util.GwUtilities;
import xyz.n7mn.dev.gunwar.util.NanamiGunWarConfiguration;

import java.util.List;

public final class NanamiGunWar extends JavaPlugin {

    private NanamiGunWar plugin;
    private SimplePluginManager pluginManager;
    private GwUtilities utilities;
    private NanamiGunWarConfiguration config;
    public static Role role;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        GunWar.plugin = plugin;
        utilities = new GwUtilities(plugin);
        GunWar.utilities = utilities;
        config = new NanamiGunWarConfiguration(plugin);
        GunWar.config = config;
        config.init();
        role = new Role(config.getConfig().getString("mysql.host", "localhost"),
                config.getConfig().getInt("mysql.port", 3306),
                config.getConfig().getString("mysql.database", ""),
                config.getConfig().getString("mysql.option", "?allowPublicKeyRetrieval=true&useSSL=false"),
                config.getConfig().getString("mysql.username", ""),
                config.getConfig().getString("mysql.password", ""));
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
