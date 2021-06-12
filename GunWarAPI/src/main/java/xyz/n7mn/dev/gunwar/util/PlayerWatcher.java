package xyz.n7mn.dev.gunwar.util;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.n7mn.dev.gunwar.game.data.PlayerData;

public class PlayerWatcher {

    private final Plugin plugin;
    private final PlayerData data;
    private BukkitRunnable runnable1tick;
    private BukkitRunnable runnable10tick;

    public PlayerWatcher(Plugin plugin, PlayerData data) {
        this.plugin = plugin;
        this.data = data;
    }

    public PlayerData getOwner() {
        return data;
    }

    public void startWatch10Ticks() {
        runnable10tick = new BukkitRunnable() {
            @Override
            public void run() {

            }
        };
        runnable10tick.runTaskTimer(plugin, 0, 10);
    }

    public void startWatch() {
        runnable1tick = new BukkitRunnable() {
            @Override
            public void run() {

            }
        };
        runnable1tick.runTaskTimer(plugin, 0, 1);
    }

    public void stopWatch10Ticks() {
        runnable10tick.cancel();
    }

    public void stopWatch() {
        runnable1tick.cancel();
    }

}
