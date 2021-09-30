/*
 * Copyright (c) 2021-2021. ClockClap. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
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

import com.github.clockclap.gunwar.event.GunWarCommandRegisterEvent;
import com.github.clockclap.gunwar.mysql.GwMySQLPlayerData;
import com.github.clockclap.gunwar.mysql.NanamiServer;
import com.github.clockclap.gunwar.nanamiserver.permission.NanamiServerRoleData;
import com.github.clockclap.gunwar.util.PermissionInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.SQLException;
import java.util.List;

@GwPlugin
public class CraftGunWarManager implements GunWarManager {

    private final Plugin plugin;

    public CraftGunWarManager(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void registerCommand(String fallbackPrefix, Command command) {
        try {
            CommandMap map = Bukkit.getServer().getCommandMap();
            map.getKnownCommands().put(command.getName(), command);
            for (String alias : command.getAliases()) {
                map.getKnownCommands().put(alias, command);
            }
            Bukkit.getServer().getCommandMap().register(fallbackPrefix, command);
            GunWarCommandRegisterEvent event = new GunWarCommandRegisterEvent(command, null);
            Bukkit.getPluginManager().callEvent(event);
        } catch(Throwable throwable) {
            GunWarCommandRegisterEvent event = new GunWarCommandRegisterEvent(command, throwable);
            Bukkit.getPluginManager().callEvent(event);
            if(!event.isCaught()) throw throwable;
        }
    }

    @Override
    @NanamiServer
    public String getRoleNameById(int id) throws SQLException {
        return GwMySQLPlayerData.getRoleNameById(id);
    }

    @Override
    public PermissionInfo testPermission(Player player, int required) {
        if(GunWar.getConfig().isNanamiNetwork()) {
            try {
                String requiredRole = getRoleNameById(required);
                String nowRole = "";
                int now = 0;
                List<NanamiServerRoleData> data = GwMySQLPlayerData.getList();
                for (NanamiServerRoleData d : data) {
                    if (d.getUniqueId() == player.getUniqueId()) {
                        nowRole = d.getName();
                        now = d.getRank();
                    }
                }
                boolean passed = true;
                if (required > now) {
                    passed = false;
                }
                return new PermissionInfo(requiredRole, nowRole, passed);
            } catch (SQLException e) {
                String requiredRole = "一般";
                String nowRole = "一般";
                boolean passed = true;
                if (player.isOp()) {
                    nowRole = "OP持ち";
                }
                if (required >= 1) {
                    requiredRole = "OP持ち";
                    if (!player.isOp()) {
                        passed = false;
                    }
                }
                return new PermissionInfo(requiredRole, nowRole, passed);
            }
        } else {
            String requiredRole = "一般";
            String nowRole = "一般";
            boolean passed = true;
            if (player.isOp()) {
                nowRole = "OP持ち";
            }
            if (required >= 1) {
                requiredRole = "OP持ち";
                if (!player.isOp()) {
                    passed = false;
                }
            }
            return new PermissionInfo(requiredRole, nowRole, passed);
        }
    }
}
