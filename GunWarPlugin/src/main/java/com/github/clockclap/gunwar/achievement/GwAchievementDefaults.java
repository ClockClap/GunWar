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

package com.github.clockclap.gunwar.achievement;

import com.github.clockclap.gunwar.GwPlugin;
import com.github.clockclap.gunwar.achievement.achievements.CraftAchievementMasterKiller;

import java.util.ArrayList;
import java.util.List;

@GwPlugin
public final class GwAchievementDefaults {

    public static List<GwAchievement> defaultAchievements = new ArrayList<>();

    public static void init() {
        defaultAchievements.add(new CraftAchievementMasterKiller());
    }

    public static void register() {
        for(GwAchievement a : defaultAchievements) GwAchievementRegisterer.register(a);
    }

    public static List<GwAchievement> getDefaultAchievements() {
        return defaultAchievements;
    }

}
