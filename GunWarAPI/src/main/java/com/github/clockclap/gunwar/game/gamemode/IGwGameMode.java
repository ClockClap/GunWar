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

import java.util.Map;

public interface IGwGameMode {

    public int getIndex();

    public String getName();

    public String getDisplayName();

    public int getGameTime();

    public int getElapsedTime();

    public int getRemainingTime();

    public Map<Integer, String> getTeamNames();

    public Map<Integer, ChatColor> getTeamColors();

    public void start(Location loc);

    public void stop();

}
