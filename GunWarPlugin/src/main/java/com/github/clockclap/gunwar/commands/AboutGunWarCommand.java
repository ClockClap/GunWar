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

import com.github.clockclap.gunwar.GunWarCommand;
import com.github.clockclap.gunwar.GwPlugin;
import com.github.clockclap.gunwar.LoggableDefault;
import com.github.clockclap.gunwar.util.GwReference;
import com.github.clockclap.gunwar.util.PermissionInfo;
import com.github.clockclap.gunwar.util.TextReference;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

@GwPlugin
public class AboutGunWarCommand extends GunWarCommand implements LoggableDefault {
    public AboutGunWarCommand() {
        super("aboutgunwar", GwReference.COMMAND_AGW_DESCRIPTION, "Usage: /aboutgunwar", Arrays.asList("aboutgw", "agw"));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            int required = getRequiredPermission("commands.aboutgunwar", 0);
            PermissionInfo info = testPermission(p, required);
            if(!info.isPassed()) {
                p.sendMessage(TextReference.getChatCommandPermissionError(info.getRequired(), info.getCurrent()));
                return true;
            }
        }
        sender.sendMessage(ChatColor.DARK_GREEN + "=-=-=- ?????????????????? v" + getPluginVersion() + " -=-=-=\n" +
                ChatColor.GRAY + "??????: " + ChatColor.RESET + "...\n" +
                ChatColor.GRAY + "??????: " + ChatColor.RESET + "ClockClap");
        return true;
    }
}
