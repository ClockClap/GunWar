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

import com.github.clockclap.gunwar.GwPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import com.github.clockclap.gunwar.GunWar;
import com.github.clockclap.gunwar.util.GwUUID;
import com.github.clockclap.gunwar.util.PermissionInfo;
import com.github.clockclap.gunwar.util.TextUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@GwPlugin
public class GWDebugCommand extends Command {

    public GWDebugCommand() {
        super("gwdebug", TextUtilities.MISC_DESCRIPTION_COMMAND_GWDEBUG, "Usage: /gwdebug <args...>", new ArrayList<>());
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        FileConfiguration conf = GunWar.getConfig().getConfig();
        if(conf.getBoolean("debug", false)) {
            int required = GunWar.getConfig().getPermissionSetting().getInt("commands.gwdebug", 1);
            if (sender instanceof Player) {
                Player p = (Player) sender;
                PermissionInfo info = GunWar.getUtilities().testPermission(p, required);
                if (!info.isPassed()) {
                    p.sendMessage(TextUtilities.getChatCommandPermissionError(info.getRequired(), info.getCurrent()));
                    return true;
                }
            }
            if(args.length >= 1) {
                if(args[0].equalsIgnoreCase("GETVAL")) {
                    if(args[1].equalsIgnoreCase("configMap") && args.length >= 3) {
                        sender.sendMessage(TextUtilities.CHAT_PREFIX + " Debug [GETVAL]: configMap(" + args[2] + ") = " + GunWar.getConfig().getDetailConfig().get(args[2], ""));
                        return true;
                    }
                    if(args[1].equalsIgnoreCase("attrMap") && args.length >= 4) {
                        sender.sendMessage(TextUtilities.CHAT_PREFIX + " Debug [GETVAL]: attrMap(" + args[2] + ") = " + GunWar.getConfig().getDetailConfig().getAttribute(args[2], args[3]));
                        return true;
                    }
                }
                if(args[0].equalsIgnoreCase("CHAT1") && args.length >= 3) {
                    sender.sendMessage(TextUtilities.chat(args[1], args[2]));
                    return true;
                }
                if(args[0].equalsIgnoreCase("CHAT2") && args.length >= 5) {
                    sender.sendMessage(TextUtilities.chat(ChatColor.valueOf(args[1]), args[2], args[3], args[4]));
                }
                if(args[0].equalsIgnoreCase("COLOR")) {
                    sender.sendMessage(TextUtilities.translateAlternateColorCodes("%{color}", "%{color}ctest"));
                }
                if(args[0].equalsIgnoreCase("UUID")) {
                    if(sender instanceof Player) {
                        Player p = (Player) sender;
                        p.sendMessage(TextUtilities.CHAT_PREFIX + " Your UUID is " + p.getUniqueId().toString() + ".");
                        if(GwUUID.match(p.getUniqueId(), UUID.fromString("4b7aeaa5-f198-4547-b05c-d78282e0d495")) ||
                                GwUUID.match(p.getUniqueId(), UUID.fromString("e7899741-cbea-4a11-aa6c-a67a8342e72d"))) {
                            p.sendMessage(TextUtilities.CHAT_PREFIX + " You are a developer of GunWar plugin.");
                        }
                    } else {
                        sender.sendMessage(TextUtilities.CHAT_PREFIX + " You do not have UUID.");
                    }
                }
            }
            sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + TextUtilities.CHAT_COMMAND_ERROR_DEBUG);
            return true;
        }
        sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + TextUtilities.CHAT_COMMAND_ERROR_NOT_DEBUGGING_MODE);
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if(args.length == 2 && args[0].equalsIgnoreCase("CHAT2")) {
            List<String> l = new ArrayList<>();
            l.add("BLACK");
            l.add("DARK_BLUE");
            l.add("DARK_GREEN");
            l.add("DARK_AQUA");
            l.add("DARK_RED");
            l.add("DARK_PURPLE");
            l.add("GOLD");
            l.add("GRAY");
            l.add("DARK_GRAY");
            l.add("BLUE");
            l.add("GREEN");
            l.add("AQUA");
            l.add("RED");
            l.add("LIGHT_PURPLE");
            l.add("YELLOW");
            l.add("WHITE");
            List<String> result = new ArrayList<>();
            if(args[1] != null && !args[1].isEmpty()) for(String str : l) if(str.startsWith(args[1])) result.add(str);
            return result;
        }
        return null;
    }
}
