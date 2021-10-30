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

package com.github.clockclap.gunwar.listeners;

import com.github.clockclap.gunwar.GunWar;
import com.github.clockclap.gunwar.GunWarPlugin;
import com.github.clockclap.gunwar.GwPlugin;
import com.github.clockclap.gunwar.game.GunWarGame;
import com.github.clockclap.gunwar.game.data.CraftPermanentPlayerData;
import com.github.clockclap.gunwar.game.data.CraftPlayerData;
import com.github.clockclap.gunwar.game.data.PermanentPlayerData;
import com.github.clockclap.gunwar.game.data.PlayerData;
import com.github.clockclap.gunwar.mysql.GwMySQLDataPath;
import com.github.clockclap.gunwar.util.PlayerWatcher;
import com.github.clockclap.gunwar.util.TextReference;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@GwPlugin
public class PlayerListener implements Listener {

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent e) {
        try {
            CraftPermanentPlayerData permanentlyPlayerData = new CraftPermanentPlayerData(e.getUniqueId());
            File f = permanentlyPlayerData.getDefaultDataFile();
            if (!f.exists()) {
                permanentlyPlayerData.save(f);
            } else {
                permanentlyPlayerData.load(f);
            }
            GunWarPlugin.getGame().addPermanentlyPlayerData(permanentlyPlayerData);
        } catch(Throwable ex) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "" +
                    ChatColor.DARK_GREEN + "=== " + TextReference.MISC_TITLE + " ===\n" +
                    ChatColor.RED + TextReference.MISC_FAILED_TO_CONNECT + "\n" +
                    ChatColor.GRAY + TextReference.MISC_CAUSE + ": " + ChatColor.WHITE + TextReference.ERROR_ON_LOGGING_IN + "\n" +
                    ChatColor.GRAY + TextReference.MISC_SOLUTION + ": " + ChatColor.WHITE + TextReference.MISC_PLEASE_REPORT.replaceAll("%CHANNEL%", ChatColor.BLUE + "#銃撃戦-バグ報告" + ChatColor.WHITE) + "\n" +
                    "\n" +
                    ChatColor.WHITE + TextReference.MISC_MORE + "\n" +
                    ChatColor.GOLD + "" + ChatColor.UNDERLINE + GunWar.getPluginConfigs().getConfig().getString("discord", "https://discord.gg/nbRUAmmypS"));
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(p.hasPlayedBefore()) {
            e.setJoinMessage((GunWar.getPluginConfigs().getLang().getBoolean("chat.player_join.raw") ? "" : TextReference.CHAT_PREFIX + " ") +
                    TextReference.CHAT_PLAYER_JOIN.replaceAll("%PLAYER%", p.getName()));
        } else {
            e.setJoinMessage((GunWar.getPluginConfigs().getLang().getBoolean("chat.player_first_join.raw") ? "" : TextReference.CHAT_PREFIX + " ") +
                    TextReference.CHAT_PLAYER_FIRST_JOIN.replaceAll("%PLAYER%", p.getName()));
        }
        CraftPlayerData data = new CraftPlayerData(p);
        PlayerWatcher watcher = new PlayerWatcher(GunWar.getPlugin(), data);
        watcher.startWatch();
        watcher.startWatch10Ticks();
        data.setWatcher(watcher);
        GunWarPlugin.getGame().addPlayerData(data.getUniqueId(), data);
        data.detail().setName(ChatColor.GRAY + data.detail().getOriginalName());
        p.setPlayerListName(ChatColor.GRAY + data.detail().getOriginalName());
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for(Player pl : players) {
            data.detail().hide(pl);
            data.detail().show(pl);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage((GunWar.getPluginConfigs().getLang().getBoolean("chat.player_quit.raw") ? "" : TextReference.CHAT_PREFIX + " ") +
                TextReference.CHAT_PLAYER_QUIT.replaceAll("%PLAYER%", e.getPlayer().getName()));
        PlayerData data = GunWar.getGame().getPlayerData(e.getPlayer());
        if(data != null) {
            data.detail().resetName();
            PlayerWatcher watcher = data.getWatcher();
            watcher.stopWatch();
            watcher.stopWatch10Ticks();
            ((GunWarGame) GunWar.getGame()).removePlayerData(data.getUniqueId());
        }
        PermanentPlayerData data_ = GunWar.getGame().getPermanentPlayerData(e.getPlayer().getUniqueId());
        if(data_ != null) {
            try {
                data_.save(data_.getDefaultDataFile());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            GunWarPlugin.getGame().removePermanentlyPlayerData(e.getPlayer().getUniqueId());
        }
        GwMySQLDataPath.delete(e.getPlayer().getUniqueId());
    }

}
