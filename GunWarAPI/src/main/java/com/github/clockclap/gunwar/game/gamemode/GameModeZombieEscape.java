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

package com.github.clockclap.gunwar.game.gamemode;

import com.github.clockclap.gunwar.GunWar;
import com.github.clockclap.gunwar.GwAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;

@GwAPI
public class GameModeZombieEscape extends GwGameMode {

    public GameModeZombieEscape() {
        super(1);
        setName("ZOMBIE_ESCAPE");
        setDisplayName(ChatColor.DARK_GREEN + "Zombie Escape");
        setGameTime(GunWar.getPluginConfigs().getConfig().getInt("zombie-escape.game-time"));
        getTeamNames().put(0, "SURVIVOR");
        getTeamColors().put(0, ChatColor.DARK_AQUA);
        getTeamNames().put(1, "ZOMBIE");
        getTeamColors().put(1, ChatColor.DARK_GREEN);
    }

    @Override
    public void start(Location loc) {

    }

    @Override
    public void stop() {

    }
}
