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

import com.github.clockclap.gunwar.GwAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@GwAPI
public final class GwGameModes {

    private static List<GwGameMode> registeredGameMode = new ArrayList<>();

    public static GwGameMode NORMAL = new GameModeNormal();
    public static GwGameMode GENERAL_SIEGE = new GameModeGeneralSiege();
    public static GwGameMode ZOMBIE_ESCAPE = new GameModeZombieEscape();

    public static Collection<GwGameMode> getRegisteredGameModes() {
        return registeredGameMode;
    }

    public static void register(GwGameMode gamemode) {
        if(registeredGameMode != null && registeredGameMode.size() > 0 && !registeredGameMode.contains(gamemode)) {
            List<GwGameMode> modeList = new ArrayList<>(registeredGameMode);
            for(GwGameMode mode : modeList) if(mode.getIndex() == gamemode.getIndex() || mode.getName().equalsIgnoreCase(gamemode.getName())) return;
            registeredGameMode.add(gamemode);
        }
    }

    public static void unregister(GwGameMode gamemode) {
        registeredGameMode.remove(gamemode);
    }

    public static void unregister(int index) {
        if(registeredGameMode != null && registeredGameMode.size() > 0) {
            List<GwGameMode> modeList = new ArrayList<>(registeredGameMode);
            for(GwGameMode mode : modeList) {
                if (mode.getIndex() == index) {
                    registeredGameMode.remove(mode);
                }
            }
        }
    }

    public static void unregister(String name) {
        if(registeredGameMode != null && registeredGameMode.size() > 0) {
            List<GwGameMode> modeList = new ArrayList<>(registeredGameMode);
            for(GwGameMode mode : modeList) {
                if (mode.getName().equalsIgnoreCase(name)) {
                    registeredGameMode.remove(mode);
                }
            }
        }
    }

    public static void unregisterAll(Collection<? extends GwGameMode> gamemodes) {
        registeredGameMode.removeAll(gamemodes);
    }

    public static void registerDefault() {
        register(NORMAL);
        register(ZOMBIE_ESCAPE);
        register(GENERAL_SIEGE);
    }

    public static void clear() {
        unregisterAll(new ArrayList<>(registeredGameMode));
    }

}
