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

package com.github.clockclap.gunwar.game.data;

import com.github.clockclap.gunwar.GwAPI;
import com.github.clockclap.gunwar.achievement.GwAchievement;
import com.github.clockclap.gunwar.item.GwGunItem;
import com.github.clockclap.gunwar.item.GwItem;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@GwAPI
public interface PermanentPlayerData {

    UUID getUniqueId();

    int getCoins();

    List<GwItem> getItemInProcessions();

    List<GwItem> getGifts();

    List<GwAchievement> getAchievements();

    Map<GwGunItem, Integer> getPlayCount();

    Map<GwGunItem, Integer> getKillCount();

    int getDeathCount();

    int getInfectedCount();

    void setCoins(int coins);

    void setDeathCount(int deathCount);

    void setInfectedCount(int infectedCount);

    File getDefaultDataFile();

    void save(File file) throws IOException;

    void load(File file) throws IOException,ClassNotFoundException;

}
