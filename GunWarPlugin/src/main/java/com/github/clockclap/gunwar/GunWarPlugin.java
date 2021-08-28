package com.github.clockclap.gunwar;

import com.github.clockclap.gunwar.commands.*;
import com.github.clockclap.gunwar.game.GameState;
import com.github.clockclap.gunwar.game.GunWarGame;
import com.github.clockclap.gunwar.game.gamemode.GwGameModes;
import com.github.clockclap.gunwar.item.GwItems;
import com.github.clockclap.gunwar.listeners.DamageListener;
import com.github.clockclap.gunwar.listeners.PlayerListener;
import com.github.clockclap.gunwar.listeners.ServerListener;
import com.github.clockclap.gunwar.mysql.GwMySQLPlayerDataUpdater;
import com.github.clockclap.gunwar.util.GwUtilities;
import com.github.clockclap.gunwar.util.GunWarPluginConfiguration;
import com.github.clockclap.gunwar.util.PlayerWatcher;
import com.github.clockclap.gunwar.util.TextUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import com.github.clockclap.gunwar.game.data.GunWarPermanentlyPlayerData;
import com.github.clockclap.gunwar.game.data.GunWarPlayerData;
import com.github.clockclap.gunwar.game.data.PermanentlyPlayerData;
import com.github.clockclap.gunwar.game.data.PlayerData;
import com.github.clockclap.gunwar.listeners.ItemListener;

import java.io.File;
import java.io.IOException;

public final class GunWarPlugin extends JavaPlugin {

    private GunWarPlugin plugin;
    private SimplePluginManager pluginManager;
    private GwUtilities utilities;
    private GunWarPluginConfiguration config;
    public static GwMySQLPlayerDataUpdater updater;
    private GunWarGame game;
    private BukkitRunnable runnable;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        GunWar.plugin = plugin;
        utilities = new GwUtilities(plugin);
        GunWar.utilities = utilities;
        config = new GunWarPluginConfiguration(plugin);
        GunWar.config = config;
        config.init();
        GwGameModes.registerDefault();
        game = new GunWarGame(plugin);
        GunWar.game = game;
        GwItems.a();
        pluginManager = (SimplePluginManager) Bukkit.getPluginManager();
        bossBar();
        registerListeners();
        registerCommands();
        for(Player p : Bukkit.getOnlinePlayers()) {
            try {
                GunWarPermanentlyPlayerData permanentlyPlayerData = new GunWarPermanentlyPlayerData(p.getUniqueId());
                File f = permanentlyPlayerData.getDefaultDataFile();
                if (!f.exists()) {
                    permanentlyPlayerData.save(f);
                } else {
                    permanentlyPlayerData.load(f);
                }
                ((GunWarGame) GunWar.getGame()).addPermanentlyPlayerData(permanentlyPlayerData);
            } catch(Throwable ex) {
                p.kickPlayer("" +
                        ChatColor.DARK_GREEN + "=== " + TextUtilities.MISC_TITLE + " ===\n" +
                        ChatColor.RED + TextUtilities.MISC_LOST_CONNECTION + "\n" +
                        ChatColor.GRAY + TextUtilities.MISC_CAUSE + ": " + ChatColor.WHITE + TextUtilities.ERROR_LOADING_PLAYERDATA + "\n" +
                        ChatColor.GRAY + TextUtilities.MISC_SOLUTION + ": " + ChatColor.WHITE + TextUtilities.MISC_PLEASE_REPORT.replaceAll("%CHANNEL%", ChatColor.BLUE + "#銃撃戦-バグ報告" + ChatColor.WHITE) + "\n" +
                        "\n" +
                        ChatColor.WHITE + TextUtilities.MISC_MORE + "\n" +
                        ChatColor.GOLD + "" + ChatColor.UNDERLINE + GunWar.getConfig().getConfig().getString("discord", "https://discord.gg/nbRUAmmypS"));
                ex.printStackTrace();
            }
            GunWarPlayerData data = new GunWarPlayerData(p);
            PlayerWatcher watcher = new PlayerWatcher(GunWar.getPlugin(), data);
            watcher.startWatch();
            watcher.startWatch10Ticks();
            data.setWatcher(watcher);
            ((GunWarGame) GunWar.getGame()).addPlayerData(data.getUniqueId(), data);
        }
        startWatch();
    }

    private void bossBar() {
        BossBar bar = Bukkit.getServer().createBossBar(TextUtilities.BOSSBAR_WAITING, BarColor.WHITE, BarStyle.SOLID);
        bar.setVisible(true);
        bar.setProgress(1.0);
        game.setBar(bar);
    }

    private void registerListeners() {
        pluginManager.registerEvents(new PlayerListener(), plugin);
        pluginManager.registerEvents(new ServerListener(), plugin);
        pluginManager.registerEvents(new ItemListener(), plugin);
        pluginManager.registerEvents(new DamageListener(), plugin);
    }

    private void registerCommands() {
        utilities.registerCommand(plugin.getName(), new GunWarReloadCommand());
        utilities.registerCommand(plugin.getName() + "-op", new GunWarItemCommand());
        utilities.registerCommand(plugin.getName(), new GWDebugCommand());
        utilities.registerCommand(plugin.getName() + "-game", new ShoutCommand());
        utilities.registerCommand(plugin.getName() + "-chat", new JapanizedTellCommand());
        utilities.registerCommand(plugin.getName() + "-chat", new ReplyCommand());
    }

    private void startWatch() {
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                int currentPlayers = Bukkit.getOnlinePlayers().size();
                int requiredPlayers = GunWar.getConfig().getConfig().getInt("game.required-players", 10);
                if(GunWar.getGame().getState() == GameState.WAITING && requiredPlayers <= currentPlayers) {
                    GunWar.getGame().start();
                }
            }
        };
        runnable.runTaskTimer(this, 0, 5);
    }

    private void cancelWatch() {
        if(runnable != null && !runnable.isCancelled()) {
            runnable.cancel();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        cancelWatch();
        for(Player p : Bukkit.getOnlinePlayers()) {
            PlayerData data = GunWar.getGame().getPlayerData(p);
            if(data != null) {
                PlayerWatcher watcher = data.getWatcher();
                watcher.stopWatch();
                watcher.stopWatch10Ticks();
                ((GunWarGame) GunWar.getGame()).removePlayerData(data.getUniqueId());
            }
            PermanentlyPlayerData data_ = GunWar.getGame().getPermanentlyPlayerData(p.getUniqueId());
            if(data_ != null) {
                try {
                    data_.save(data_.getDefaultDataFile());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ((GunWarGame) GunWar.getGame()).removePermanentlyPlayerData(p.getUniqueId());
            }
        }
        GwGameModes.clear();
        GwItems.clear();
    }
}
