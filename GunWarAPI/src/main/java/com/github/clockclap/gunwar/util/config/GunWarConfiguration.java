/*
 * Copyright (c) 2021-2021. ClockClap. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.github.clockclap.gunwar.util.config;

import com.github.clockclap.gunwar.GwAPI;
import com.github.clockclap.gunwar.util.map.StringMap;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

@GwAPI
public interface GunWarConfiguration {

    FileConfiguration getConfig();

    FileConfiguration getPermissionSetting();

    XmlConfiguration getDetailConfig();

    StringMap getLang();

    File getConfigFile();

    File getPermissionSettingFile();

    File getDetailConfigFile();

    File getLangFile();

    boolean isNanamiNetwork();

    void setNanamiNetwork(boolean nanamiNetwork);

}
