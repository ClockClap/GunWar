/*
 * Copyright (c) 2021, ClockClap. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.github.clockclap.gunwar;

import com.github.clockclap.gunwar.commands.*;
import com.github.clockclap.gunwar.game.GameState;
import com.github.clockclap.gunwar.game.GunWarGame;
import com.github.clockclap.gunwar.game.data.GunWarPermanentlyPlayerData;
import com.github.clockclap.gunwar.game.data.GunWarPlayerData;
import com.github.clockclap.gunwar.game.data.PermanentlyPlayerData;
import com.github.clockclap.gunwar.game.data.PlayerData;
import com.github.clockclap.gunwar.game.gamemode.GwGameModes;
import com.github.clockclap.gunwar.item.GwItems;
import com.github.clockclap.gunwar.listeners.DamageListener;
import com.github.clockclap.gunwar.listeners.ItemListener;
import com.github.clockclap.gunwar.listeners.PlayerListener;
import com.github.clockclap.gunwar.listeners.ServerListener;
import com.github.clockclap.gunwar.mysql.GwMySQLPlayerDataUpdater;
import com.github.clockclap.gunwar.util.GunWarPluginConfiguration;
import com.github.clockclap.gunwar.util.PlayerWatcher;
import com.github.clockclap.gunwar.util.TextReference;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

@GwPlugin
public final class GunWarPlugin extends JavaPlugin {

    private static GunWarPlugin plugin;
    private SimplePluginManager pluginManager;
    private CraftGunWarManager manager;
    private GunWarPluginConfiguration config;
    public static GwMySQLPlayerDataUpdater updater;
    private GunWarGame game;
    private BukkitRunnable runnable;

    public static GunWarGame getGame() {
        return plugin.game;
    }

    public static GunWarPlugin getPlugin() {
        return plugin;
    }

    public static CraftGunWarManager getManager() {
        return plugin.manager;
    }

    public static GunWarPluginConfiguration getPluginConfig() {
        return plugin.config;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        GunWar.plugin = plugin;
        manager = new CraftGunWarManager(plugin);
        GunWar.manager = manager;
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
                        ChatColor.DARK_GREEN + "=== " + TextReference.MISC_TITLE + " ===\n" +
                        ChatColor.RED + TextReference.MISC_LOST_CONNECTION + "\n" +
                        ChatColor.GRAY + TextReference.MISC_CAUSE + ": " + ChatColor.WHITE + TextReference.ERROR_LOADING_PLAYERDATA + "\n" +
                        ChatColor.GRAY + TextReference.MISC_SOLUTION + ": " + ChatColor.WHITE + TextReference.MISC_PLEASE_REPORT.replaceAll("%CHANNEL%", ChatColor.BLUE + "#銃撃戦-バグ報告" + ChatColor.WHITE) + "\n" +
                        "\n" +
                        ChatColor.WHITE + TextReference.MISC_MORE + "\n" +
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
        BossBar bar = Bukkit.getServer().createBossBar(TextReference.BOSSBAR_WAITING, BarColor.WHITE, BarStyle.SOLID);
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
        manager.registerCommand(plugin.getName(), new GunWarReloadCommand());
        manager.registerCommand(plugin.getName() + "-op", new GunWarItemCommand());
        manager.registerCommand(plugin.getName() + "-dev", new GWDebugCommand());
        manager.registerCommand(plugin.getName() + "-game", new ShoutCommand());
        manager.registerCommand(plugin.getName() + "-chat", new JapanizedTellCommand());
        manager.registerCommand(plugin.getName() + "-chat", new ReplyCommand());
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
