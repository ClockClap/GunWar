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

package com.github.clockclap.gunwar.achievement.achievements;

import com.github.clockclap.gunwar.GwPlugin;
import com.github.clockclap.gunwar.achievement.GwAchievementBase;
import org.bukkit.Material;
import com.github.clockclap.gunwar.util.TextUtilities;

import java.util.Arrays;

@GwPlugin
public class CraftAchievementMasterKiller extends GwAchievementBase implements AchievementMasterKiller {

    public CraftAchievementMasterKiller() {
        super("master_killer", TextUtilities.format("achievement.master_killer.name", "マスターキラー"),
                Arrays.asList(TextUtilities.format("achievement.master_killer.description", "1試合で50キル")), Material.DIAMOND_SWORD);
    }

}
