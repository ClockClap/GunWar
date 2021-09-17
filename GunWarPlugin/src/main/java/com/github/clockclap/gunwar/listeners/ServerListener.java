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

import com.github.clockclap.gunwar.GwPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import com.github.clockclap.gunwar.GunWar;
import com.github.clockclap.gunwar.event.GunWarCommandRegisterEvent;
import com.github.clockclap.gunwar.game.GameState;
import com.github.clockclap.gunwar.game.data.ItemData;
import com.github.clockclap.gunwar.game.data.PlayerData;
import com.github.clockclap.gunwar.game.gamemode.GwGameMode;
import com.github.clockclap.gunwar.game.gamemode.Shoutable;
import com.github.clockclap.gunwar.item.GwWeaponItem;
import com.github.clockclap.gunwar.item.WeaponType;
import com.github.clockclap.gunwar.util.TextUtilities;

@GwPlugin
public class ServerListener implements Listener {

    @EventHandler
    public void onRegisterCommand(GunWarCommandRegisterEvent e) {
        if(e.getCommand().getName().equalsIgnoreCase("gunwarreload") ||
                e.getCommand().getName().equalsIgnoreCase("gunwaritem")) {
            if(e.getThrowable() != null) {
                e.catchThrowable();
                GunWar.getPlugin().getLogger().info("Failed to register command: /" + e.getCommand().getName());
                return;
            }
            GunWar.getPlugin().getLogger().info("Succeeded registering command: /" + e.getCommand().getName());
        }
    }

    @EventHandler
    public void onChatAsync(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        if(GunWar.getGame().getState() == GameState.PLAYING) {
            GwGameMode mode = GunWar.getGame().getGameMode();
            PlayerData data = GunWar.getPlayerData(e.getPlayer());
            String prefix = "";
            ChatColor color = ChatColor.GRAY;
            if (mode.getTeamNames().containsKey(data.getTeam())) prefix = mode.getTeamNames().get(data.getTeam());
            if (mode.getTeamColors().containsKey(data.getTeam())) color = mode.getTeamColors().get(data.getTeam());
            if(data.isSpectator()) {
                for(PlayerData d : GunWar.getOnlinePlayerData()) {
                    if(d.isSpectator())
                        d.sendMessage(ChatColor.DARK_GRAY + "(Spectator Chat) " + TextUtilities.chat(color, prefix, e.getPlayer().getName(), e.getMessage()));
                }
            } else if(mode instanceof Shoutable) {
                for(PlayerData d : GunWar.getOnlinePlayerData()) {
                    if(d.getTeam() == data.getTeam())
                        d.sendMessage(ChatColor.BLUE + "(Team Chat) " + TextUtilities.chat(color, prefix, e.getPlayer().getName(), e.getMessage()));
                }
            } else {
                Bukkit.broadcastMessage(TextUtilities.chat(color, prefix, e.getPlayer().getName(), e.getMessage()));
            }
        } else {
            Bukkit.broadcastMessage(TextUtilities.chat(e.getPlayer().getName(), e.getMessage()));
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if(p != null) {
            if(!p.isOp()) {
                e.setCancelled(true);
            } else {
                if(p.getGameMode() == GameMode.SURVIVAL) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if(p != null) {
            if(p.getGameMode() == GameMode.SURVIVAL && GunWar.getGame().getState() == GameState.PLAYING) {
                if (p.getInventory().getItemInMainHand() != null) {
                    ItemData data = GunWar.getGame().getItemData(p.getInventory().getItemInMainHand());
                    if (data.getGwItem() instanceof GwWeaponItem && ((GwWeaponItem) data.getGwItem()).getWeaponType() == WeaponType.KNIFE) {
                        if (e.getBlock() != null && e.getBlock().getType() == Material.GLASS) {
                            return;
                        }
                    }
                }
            }
            if(!p.isOp()) {
                e.setCancelled(true);
            } else {
                if(p.getGameMode() == GameMode.SURVIVAL) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
