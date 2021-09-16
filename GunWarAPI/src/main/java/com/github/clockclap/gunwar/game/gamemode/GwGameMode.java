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

import java.util.HashMap;
import java.util.Map;

public abstract class GwGameMode implements IGwGameMode {

    private final int index;
    private String name;
    private String displayName;
    private int gameTime;
    private int elapsedTime;
    private Map<Integer, String> teamNames;
    private Map<Integer, ChatColor> teamColors;

    protected GwGameMode(int index) {
        this.index = index;
        this.gameTime = 0;
        this.elapsedTime = 0;
        this.teamNames = new HashMap<>();
        this.teamColors = new HashMap<>();
    }

    public GwGameMode(int index, String name, String displayName, int gameTime, Map<Integer, String> teamNames, Map<Integer, ChatColor> teamColors) {
        this.index = index;
        this.name = name;
        this.displayName = displayName;
        this.gameTime = gameTime;
        this.elapsedTime = 0;
        this.teamNames = teamNames;
        this.teamColors = teamColors;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public int getGameTime() {
        return gameTime;
    }

    @Override
    public int getElapsedTime() {
        return elapsedTime;
    }

    @Override
    public int getRemainingTime() {
        return Math.max(0, gameTime - elapsedTime);
    }

    @Override
    public Map<Integer, String> getTeamNames() {
        return teamNames;
    }

    @Override
    public Map<Integer, ChatColor> getTeamColors() {
        return teamColors;
    }

    protected void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    protected void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    protected void setTeamNames(Map<Integer, String> teamNames) {
        this.teamNames = teamNames;
    }

    protected void setTeamColors(Map<Integer, ChatColor> teamColors) {
        this.teamColors = teamColors;
    }

}
