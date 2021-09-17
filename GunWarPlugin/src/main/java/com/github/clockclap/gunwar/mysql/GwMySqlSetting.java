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

import com.github.clockclap.gunwar.GwPlugin;

@GwPlugin
final class GwMySqlSetting {

    private final String host;
    private final int port;
    private final String database;
    private final String option;
    private final String username;
    private final String password;
    private final String url;

    GwMySqlSetting(String host, int port, String database, String option, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.option = option;
        this.username = username;
        this.password = password;
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + database + option;
    }

    String getHost() {
        return host;
    }

    int getPort() {
        return port;
    }

    String getDatabase() {
        return database;
    }

    String getOption() {
        return option;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    String getUrl() {
        return url;
    }
}
