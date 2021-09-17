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

import com.github.clockclap.gunwar.game.Game;
import com.github.clockclap.gunwar.util.GunWarConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import com.github.clockclap.gunwar.game.data.PlayerData;

import java.util.Collection;
import java.util.UUID;

@GwAPI
public final class GunWar {

    static Plugin plugin;
    static GunWarManager manager;
    static GunWarConfiguration config;
    static Game game;
    private static final int apiVer = 2;

    public static Plugin getPlugin() {
        return plugin;
    }

    public static GunWarManager getManager() {
        return manager;
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

    public static Collection<PlayerData> getOnlinePlayerData() {
        return getGame().getOnlinePlayerData();
    }

    public static int getAPIVersion() {
        return apiVer;
    }

}


