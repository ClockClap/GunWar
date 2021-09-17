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

package com.github.clockclap.gunwar.game;

import com.github.clockclap.gunwar.GwAPI;
import com.github.clockclap.gunwar.game.data.ItemData;
import com.github.clockclap.gunwar.game.gamemode.GwGameMode;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import com.github.clockclap.gunwar.game.data.PermanentlyPlayerData;
import com.github.clockclap.gunwar.game.data.PlayerData;

import java.util.Collection;
import java.util.UUID;

@GwAPI
public interface Game {

    public Plugin getPlugin();

    public GameState getState();

    public void setState(GameState state);

    public PlayerData getPlayerData(Player player);

    public PermanentlyPlayerData getPermanentlyPlayerData(UUID uniqueId);

    public Collection<PlayerData> getOnlinePlayerData();

    public BossBar getBar();

    public void setBar(BossBar bar);

    public GwGameMode getGameMode();

    public ItemData getItemData(ItemStack item);

    public void setGameMode(GwGameMode gamemode);

    public void start(Location loc);

    public void start();

    public void stop();

    public Location getLocation();

    public void setLocation(Location location);

    public int getStartingAt();

}
