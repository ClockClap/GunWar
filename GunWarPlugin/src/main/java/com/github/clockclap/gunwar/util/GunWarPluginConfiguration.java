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

package com.github.clockclap.gunwar.util;

import com.github.clockclap.gunwar.GwPlugin;
import com.github.clockclap.gunwar.mysql.MySQLSettingBuilder;
import com.github.clockclap.gunwar.util.map.StringMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;

@GwPlugin
public class GunWarPluginConfiguration implements GunWarConfiguration {

    private final Plugin plugin;
    private File dataFolder;
    private FileConfiguration config;
    private FileConfiguration permissionSetting;
    private StringMap lang;
    private XmlConfiguration detailConfig;
    private File configFile;
    private File permissionFile;
    private File detailConfigFile;
    private File langFile;
    private boolean nanamiNetwork;

    public GunWarPluginConfiguration(Plugin plugin) {
        this.plugin = plugin;
        this.nanamiNetwork = false;
    }

    public void init() {
        try {
            String dataFolder = "plugins/" + plugin.getName();

            File pluginFile = new File(dataFolder);
            if (!pluginFile.exists()) {
                pluginFile.mkdir();
            }
            this.dataFolder = pluginFile;
            File playerDataFile = new File(dataFolder + "/players");
            if(!playerDataFile.exists()) {
                playerDataFile.mkdir();
            }
            File permissionsFolder = new File(dataFolder + "/permission");
            if(!permissionsFolder.exists()) {
                permissionsFolder.mkdir();
            }
            File cacheFolder = new File(dataFolder + "/cache");
            if(!cacheFolder.exists()) {
                cacheFolder.mkdir();
            }
            File langFolder = new File(dataFolder + "/lang");
            if(!langFolder.exists()) {
                langFolder.mkdir();
            }

            detailConfigFile = new File(dataFolder + "/detail_config.xml");
            if(!detailConfigFile.exists()) {
                detailConfigFile.createNewFile();
                saveDefault(detailConfigFile, "detail_config.xml");
            }
            detailConfig = XmlConfiguration.loadXml(detailConfigFile);
            if(detailConfig != null) {
                String langPath = detailConfig.getString("config.chat.lang", "plugins/GunWar/lang/ja_jp.lang");
                langFile = new File(langPath);
            }
            if(langFile != null) {
                if(!langFile.exists()) {
                    langFile.createNewFile();
                    if(langFile.getName().equalsIgnoreCase("ja_jp.lang")) {
                        saveDefault(langFile, "lang/ja_jp.lang");
                    } else {
                        saveDefault(langFile, "lang/en_us.lang");
                    }
                }
            } else {
                langFile = new File(dataFolder + "/lang/ja_jp.lang");
                if(!langFile.exists()) {
                    langFile.createNewFile();
                    saveDefault(langFile, "lang/ja_jp.lang");
                }
            }
            lang = StringMap.load(langFile);

            configFile = new File(dataFolder + "/config.yml");
            if (!configFile.exists()) {
                configFile.createNewFile();
                saveDefault(configFile, "config.yml");
            }
            config = YamlConfiguration.loadConfiguration(configFile);

            MySQLSettingBuilder.builder().withSetting(config.getString("mysql.host", "localhost"),
                    config.getInt("mysql.port", 3306),
                    config.getString("mysql.database", ""),
                    config.getString("mysql.option", "?allowPublicKeyRetrieval=true&useSSL=false"),
                    config.getString("mysql.username", ""),
                    config.getString("mysql.password", "")).build();

            String ppath = config.getString("permission-setting", dataFolder + "/permission/default.yml");
            if(ppath.startsWith(":nanami-network:>")) {
                if(MySQLSettingBuilder.isEnabled()) nanamiNetwork = true;
                ppath = ppath.substring(17);
            } else {
                nanamiNetwork = false;
            }

            permissionFile = new File(ppath);
            if (!permissionFile.exists()) {
                permissionFile.createNewFile();
                saveDefault(permissionFile, "permission/default.yml");
            }
            permissionSetting = YamlConfiguration.loadConfiguration(permissionFile);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveDefault(File file, String resource) {
        try (InputStream inputStream = plugin.getResource(resource);
             OutputStream out = new FileOutputStream(file)) {
            byte[] buf = new byte['?'];
            int length;
            while ((length = inputStream.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

    @Override
    public FileConfiguration getPermissionSetting() {
        return permissionSetting;
    }

    @Override
    public XmlConfiguration getDetailConfig() {
        return detailConfig;
    }

    @Override
    public StringMap getLang() {
        return lang;
    }

    @Override
    public File getConfigFile() {
        return configFile;
    }

    @Override
    public File getPermissionSettingFile() {
        return permissionFile;
    }

    @Override
    public File getDetailConfigFile() {
        return detailConfigFile;
    }

    @Override
    public File getLangFile() {
        return langFile;
    }

    @GwPlugin
    public void setPermissionFile(File file) {
        permissionFile = file;
    }

    @Override
    public boolean isNanamiNetwork() {
        return nanamiNetwork;
    }

    @Override
    public void setNanamiNetwork(boolean nanamiNetwork) {
        this.nanamiNetwork = nanamiNetwork;
    }

    public File getDataFolder() {
        return dataFolder;
    }
}
