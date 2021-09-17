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

package com.github.clockclap.gunwar.nanamiserver.permission;

import com.github.clockclap.gunwar.GwPlugin;

import java.util.UUID;

@GwPlugin
public class NanamiServerRoleData {

    private java.util.UUID uniqueId;
    private String id;
    private String name;
    private int rank;

    public NanamiServerRoleData(UUID uuid, String discordRoleID, String roleName, int roleRank){
        this.uniqueId = uuid;
        this.id = discordRoleID;
        this.name = roleName;
        this.rank = roleRank;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getDiscordRoleId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

}
