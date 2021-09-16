/*
 * Copyright (c) 2021, ClockClap. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.clockclap.gunwar.game.gamemode;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import com.github.clockclap.gunwar.GunWar;

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
        setGameTime(GunWar.getConfig().getConfig().getInt("normal.game-time-solo"));
        getTeamNames().put(0, "RED");
        getTeamColors().put(0, ChatColor.RED);
        getTeamNames().put(1, "BLUE");
        getTeamColors().put(1, ChatColor.BLUE);
    }

    @Override
    public void start(Location loc) {

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
            setGameTime(GunWar.getConfig().getConfig().getInt("normal.game-time-solo"));
        } else if(mode == Mode.TEAM) {
            setGameTime(GunWar.getConfig().getConfig().getInt("normal.game-time-team"));
        }
    }
}
