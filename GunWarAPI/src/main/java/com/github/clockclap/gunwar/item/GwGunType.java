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

package com.github.clockclap.gunwar.item;

import com.github.clockclap.gunwar.GwAPI;

@GwAPI
public enum GwGunType {

    ASSAULT_RIFLE(0, "ASSAULT_RIFLE"),
    SNIPER_RIFLE(1, "SNIPER_RIFLE"),
    SUBMACHINE_GUN(2, "SUBMACHINE_GUN"),
    SHOTGUN(3, "SHOTGUN"),
    HAND_GUN(4, "HAND_GUN"),
    ORIGIN(5, "ORIGIN");

    private int index;
    private String name;

    private GwGunType(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

}
