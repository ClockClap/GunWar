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

package com.github.clockclap.gunwar.mysql;

import java.io.Serializable;
import java.util.*;

public class GwMySQLDataPath implements Serializable {

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
