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

package com.github.clockclap.gunwar.commands;

import com.github.clockclap.gunwar.GunWar;
import com.github.clockclap.gunwar.GwPlugin;
import com.github.clockclap.gunwar.util.PermissionInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.github.clockclap.gunwar.util.GwReference;
import com.github.clockclap.gunwar.util.TextUtilities;

import java.util.Arrays;

@GwPlugin
public class AboutGunWarCommand extends Command {
    public AboutGunWarCommand() {
        super("aboutgunwar", GwReference.COMMAND_AGW_DESCRIPTION, "Usage: /aboutgunwar", Arrays.asList("aboutgw", "agw"));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        int required = GunWar.getConfig().getPermissionSetting().getInt("commands.aboutgunwar", 0);
        if(sender instanceof Player) {
            Player p = (Player) sender;
            PermissionInfo info = GunWar.getUtilities().testPermission(p, required);
            if(!info.isPassed()) {
                p.sendMessage(TextUtilities.getChatCommandPermissionError(info.getRequired(), info.getCurrent()));
                return true;
            }
        }
        sender.sendMessage(ChatColor.DARK_GREEN + "=-=-=- ななみ銃撃戦 v" + GunWar.getPlugin().getDescription().getVersion() + " -=-=-=\n" +
                ChatColor.GRAY + "説明: " + ChatColor.RESET + "...\n" +
                ChatColor.GRAY + "作者: " + ChatColor.RESET + "ClockClap");
        return true;
    }
}
