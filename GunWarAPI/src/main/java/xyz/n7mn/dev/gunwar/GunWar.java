package xyz.n7mn.dev.gunwar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.gunwar.game.Game;
import xyz.n7mn.dev.gunwar.game.data.PlayerData;
import xyz.n7mn.dev.gunwar.item.GwItem;
import xyz.n7mn.dev.gunwar.util.GunWarConfiguration;
import xyz.n7mn.dev.gunwar.util.Utilities;

import java.util.UUID;

public final class GunWar {

    static Plugin plugin;
    static Utilities utilities;
    static GunWarConfiguration config;
    static Game game;

    public static Plugin getPlugin() {
        return plugin;
    }

    public static Utilities getUtilities() {
        return utilities;
    }

    public static GunWarConfiguration getConfig() {
        return config;
    }

    public static Game getGame() {
        return game;
    }

    public static PlayerData getPlayerData(Player player) {
        return getGame().getPlayerData(player);
    }

    public static PlayerData getPlayerData(UUID id) {
        return getGame().getPlayerData(Bukkit.getPlayer(id));
    }

    @Deprecated
    public static PlayerData getPlayerData(String name) {
        return getGame().getPlayerData(Bukkit.getPlayer(name));
    }

}


