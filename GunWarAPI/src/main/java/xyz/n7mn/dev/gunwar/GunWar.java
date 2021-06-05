package xyz.n7mn.dev.gunwar;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.n7mn.dev.gunwar.util.GunWarConfiguration;
import xyz.n7mn.dev.gunwar.util.Utilities;

public final class GunWar {

    static Plugin plugin;
    static Utilities utilities;
    static GunWarConfiguration config;

    public static Plugin getPlugin() {
        return plugin;
    }

    public static Utilities getUtilities() {
        return utilities;
    }

    public static GunWarConfiguration getConfig() {
        return config;
    }

}


