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

import java.sql.*;
import java.util.Enumeration;

@GwPlugin
public final class MySQLSettingBuilder {

    private GwMySqlSetting setting;

    private static GwMySqlSetting settingStatic;
    private static GwMySQLPlayerDataUpdater updaterStatic;
    private static boolean enabled = false;

    public static MySQLSettingBuilder builder() {
        return new MySQLSettingBuilder();
    }

    public MySQLSettingBuilder withSetting(String host, int port, String database, String option, String username, String password) {
        setting = new GwMySqlSetting(host, port, database, option, username, password);
        return this;
    }

    public void build() {
        settingStatic = this.setting;
        updaterStatic = new GwMySQLPlayerDataUpdater(this.setting);

        try {
            boolean found = false;
            Enumeration<Driver> drivers = DriverManager.getDrivers();

            while (drivers.hasMoreElements()){
                Driver driver = drivers.nextElement();
                if (driver.equals(new com.mysql.cj.jdbc.Driver())){
                    found = true;
                    break;
                }
            }

            if (!found){
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Connection con = DriverManager.getConnection("" +
                            "jdbc:mysql://" +
                            MySQLSettingBuilder.getSetting().getHost()+ ":" +
                            MySQLSettingBuilder.getSetting().getPort() + "/" +
                            MySQLSettingBuilder.getSetting().getDatabase() +
                            MySQLSettingBuilder.getSetting().getOption(),
                    MySQLSettingBuilder.getSetting().getUsername(),
                    MySQLSettingBuilder.getSetting().getPassword());
            con.close();
            enabled = true;
        } catch(SQLException ex) {
            enabled = false;
            ex.printStackTrace();
        }
    }

    public static GwMySQLPlayerDataUpdater getUpdater() {
        return updaterStatic;
    }

    static GwMySqlSetting getSetting() {
        return settingStatic;
    }

    public static boolean isEnabled() {
        return enabled;
    }

}
