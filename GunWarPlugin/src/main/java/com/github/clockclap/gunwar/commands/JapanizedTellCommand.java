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
import com.github.clockclap.gunwar.util.MessageLog;
import com.github.clockclap.gunwar.util.TextReference;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@GwPlugin
public class JapanizedTellCommand extends GunWarCommand implements LoggableDefault {

    public JapanizedTellCommand() {
        super("tell", "", "Usage: /tell <player> <message>", Arrays.asList("msg", "message", "m", "t", "w"));
        setPermission("minecraft.command.msg");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length >= 2 && args[0] != null && args[1] != null) {
            Player to = Bukkit.getPlayer(args[0]);
            if(to != null && to.isOnline()) {
                StringBuilder message = new StringBuilder();
                for(int i = 1; i < args.length; i++) {
                    message.append(args[i]);
                    message.append(" ");
                }
                String name = sender instanceof ConsoleCommandSender ? "Server" : sender.getName();
                String s = TextReference.privateChat(sender.getName(), args[0], message.toString().trim());
                sender.sendMessage(s);
                to.sendMessage(s);
                MessageLog.finalMessageTarget.put(sender, to);
                MessageLog.finalMessageTarget.put(to, sender);
                return true;
            }
            sender.sendMessage(TextReference.CHAT_PREFIX + " " + TextReference.CHAT_COMMAND_ERROR_UNKNOWN_PLAYER);
            return true;
        }
        sender.sendMessage(TextReference.CHAT_PREFIX + " " + ChatColor.GRAY + getUsage());
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if(args.length == 1) {
            List<String> result = new ArrayList<>();
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(p != null && p.getName().startsWith(args[0])) {
                    if(p == sender) continue;
                    result.add(p.getName());
                }
            }
            return result;
        }
        return null;
    }
}
