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

package com.github.clockclap.gunwar.game;

import com.github.clockclap.gunwar.GwAPI;
import com.github.clockclap.gunwar.game.data.ItemData;
import com.github.clockclap.gunwar.game.data.PermanentPlayerData;
import com.github.clockclap.gunwar.game.data.PlayerData;
import com.github.clockclap.gunwar.game.gamemode.GwGameMode;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.UUID;

@GwAPI
public interface Game {

    Plugin getPlugin();

    GameState getState();

    void setState(GameState state);

    PlayerData getPlayerData(Player player);

    PermanentPlayerData getPermanentPlayerData(UUID uniqueId);

    Collection<PlayerData> getOnlinePlayerData();

    BossBar getBar();

    void setBar(BossBar bar);

    GwGameMode getGameMode();

    ItemData getItemData(ItemStack item);

    Collection<Player> getJoinedPlayers();

    public void setGameMode(GwGameMode gamemode);

    public void start(Object... args);

    public void start();

    public void stop();

    public Location getLocation();

    public void setLocation(Location location);

    public int getStartingAt();

    void randomizeTeams(Collection<Player> players, int... teams);

    Collection<PlayerData> toPlayerData(Collection<Player> players);

    Collection<PlayerData> calculateGamingPlayers();

}
