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

package com.github.clockclap.gunwar.commands;

import com.github.clockclap.gunwar.GunWar;
import com.github.clockclap.gunwar.GunWarCommand;
import com.github.clockclap.gunwar.GwPlugin;
import com.github.clockclap.gunwar.LoggableDefault;
import com.github.clockclap.gunwar.mysql.MySQLSettingBuilder;
import com.github.clockclap.gunwar.util.config.GunWarPluginConfiguration;
import com.github.clockclap.gunwar.util.PermissionInfo;
import com.github.clockclap.gunwar.util.TextReference;
import com.google.common.base.Charsets;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.Arrays;

@GwPlugin
public class GunWarReloadCommand extends GunWarCommand implements LoggableDefault {
    public GunWarReloadCommand() {
        super("gunwarreload", TextReference.MISC_DESCRIPTION_COMMAND_GUNWARRELOAD, "Usage: /gunwarreload", Arrays.asList("gwreload"));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            int required = getRequiredPermission("commands.gunwarreload", 1);
            PermissionInfo info = testPermission(p, required);
            if(!info.isPassed()) {
                p.sendMessage(TextReference.getChatCommandPermissionError(info.getRequired(), info.getCurrent()));
                return true;
            }
        }
        reload();
        sender.sendMessage(TextReference.CHAT_PREFIX + " " + TextReference.CHAT_COMMAND_RELOAD);
        return true;
    }

    private void reload() {
        reloadConfig();
        reloadPerm();
    }

    private void reloadConfig() {
        FileConfiguration newConfig = YamlConfiguration.loadConfiguration(GunWar.getPluginConfigs().getConfigFile());

        final InputStream defConfigStream = GunWar.getPlugin().getResource("config.yml");
        if (defConfigStream == null) {
            return;
        }

        newConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
        try {
            GunWar.getPluginConfigs().getConfig().load(GunWar.getPluginConfigs().getConfigFile());
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void reloadPerm() {
        String path = GunWar.getPluginConfigs().getConfig()
                .getString("permission-setting", ((GunWarPluginConfiguration) GunWar.getPluginConfigs()).getDataFolder() + "/permission/default.yml");
        if(path.startsWith(":nanami-network:>")) {
            if(MySQLSettingBuilder.isEnabled()) GunWar.getPluginConfigs().setNanamiNetwork(true);
            path = path.substring(17);
        } else {
            GunWar.getPluginConfigs().setNanamiNetwork(false);
        }
        ((GunWarPluginConfiguration) GunWar.getPluginConfigs()).setPermissionFile(new File(path));
        File permissionFile = GunWar.getPluginConfigs().getPermissionSettingFile();
        boolean b1 = true;
        if (!permissionFile.exists()) {
            try {
                permissionFile.createNewFile();
            } catch(IOException ex) {
                return;
            }
            b1 = false;
        }
        if (!b1) {
            try {
                InputStream inputStream = GunWar.getPlugin().getResource("permission/default.yml");
                File file = permissionFile;
                OutputStream out = new FileOutputStream(file);
                byte[] buf = new byte['?'];
                int length;
                while ((length = inputStream.read(buf)) > 0) {
                    out.write(buf, 0, length);
                }
                out.close();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        FileConfiguration newConfig = YamlConfiguration.loadConfiguration(GunWar.getPluginConfigs().getPermissionSettingFile());

        final InputStream defConfigStream = GunWar.getPlugin().getResource("permission/default.yml");
        if (defConfigStream == null) {
            return;
        }

        newConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
        try {
            GunWar.getPluginConfigs().getPermissionSetting().load(GunWar.getPluginConfigs().getPermissionSettingFile());
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
