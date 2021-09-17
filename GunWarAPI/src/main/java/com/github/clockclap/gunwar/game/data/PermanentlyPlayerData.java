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
public interface PermanentlyPlayerData {

    public UUID getUniqueId();

    public int getCoins();

    public List<GwItem> getItemInProcessions();

    public List<GwItem> getGifts();

    public List<GwAchievement> getAchievements();

    public Map<GwGunItem, Integer> getPlayCount();

    public Map<GwGunItem, Integer> getKillCount();

    public int getDeathCount();

    public int getInfectedCount();

    public void setCoins(int coins);

    public void setDeathCount(int deathCount);

    public void setInfectedCount(int infectedCount);

    public File getDefaultDataFile();

    public void save(File file) throws IOException;

    public void load(File file) throws IOException,ClassNotFoundException;

}
