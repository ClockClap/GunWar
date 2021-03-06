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

package com.github.clockclap.gunwar.mysql;

import com.github.clockclap.gunwar.GwPlugin;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@GwPlugin
public class GwMySQLDataPath implements Serializable {

    private static final long serialVersionUID = 2076752219409265522L;

    private transient static Set<GwMySQLDataPath> paths = new HashSet<>();

    public static Set<GwMySQLDataPath> pathSet() {
        return paths;
    }

    public static GwMySQLDataPath get(UUID id) {
        for(GwMySQLDataPath p : paths) {
            if(p.getUniqueId() == id) {
                return p;
            }
        }
        return null;
    }

    public static String getPath(UUID id) {
        if(get(id) != null) {
            return get(id).getPath();
        }
        return null;
    }

    public static GwMySQLDataPath insert(UUID id, String path) {
        GwMySQLDataPath p = new GwMySQLDataPath(id, path);
        paths.add(p);
        return p;
    }

    public static void delete(UUID id) {
        Set<GwMySQLDataPath> paths_ = new HashSet<>(paths);
        for(GwMySQLDataPath p : paths_) {
            if(p.getUniqueId() == id) {
                paths.remove(p);
                break;
            }
        }
    }

    private final UUID id;
    private String path;

    private GwMySQLDataPath(UUID id, String path) {
        this.id = id;
        this.path = path;
    }

    public UUID getUniqueId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
