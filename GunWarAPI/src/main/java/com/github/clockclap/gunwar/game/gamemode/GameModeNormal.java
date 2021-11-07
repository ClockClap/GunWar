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
import com.github.clockclap.gunwar.game.Game;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

@GwAPI
public class GameModeNormal extends GwGameMode implements Shoutable {

    public enum Mode {
        TEAM, SOLO
    }

    private Mode mode;

    public GameModeNormal() {
        super(0);
        setName("NORMAL");
        setDisplayName(ChatColor.GREEN + "Normal");
        this.mode = Mode.SOLO;
        setGameTime(GunWar.getPluginConfigs().getConfig().getInt("normal.game-time-solo"));
        getTeamNames().put(0, "RED");
        getTeamColors().put(0, ChatColor.RED);
        getTeamNames().put(1, "BLUE");
        getTeamColors().put(1, ChatColor.BLUE);
    }

    @Override
    public void start(Object... args) {
        Game game = GunWar.getGame();
        int[] teams = new int[]{0, 1};
        Collection<Player> players = game.getJoinedPlayers();
        game.randomizeTeams(players, teams);
    }

    @Override
    public void stop() {

    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        if(mode == Mode.SOLO) {
            setGameTime(GunWar.getPluginConfigs().getConfig().getInt("normal.game-time-solo"));
        } else if(mode == Mode.TEAM) {
            setGameTime(GunWar.getPluginConfigs().getConfig().getInt("normal.game-time-team"));
        }
    }
}
