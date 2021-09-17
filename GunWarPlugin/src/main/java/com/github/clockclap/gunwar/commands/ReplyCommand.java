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

import com.github.clockclap.gunwar.GwPlugin;
import com.github.clockclap.gunwar.util.TextUtilities;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import com.github.clockclap.gunwar.util.MessageLog;

import java.util.Arrays;
import java.util.List;

@GwPlugin
public class ReplyCommand extends Command {

    public ReplyCommand() {
        super("reply", "", "Usage: /reply <message>", Arrays.asList("rep", "r"));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length >= 1 && args[0] != null) {
            CommandSender to = MessageLog.finalMessageTarget.get(sender);
            if(to instanceof Player && ((Player) to).isOnline()) {
                StringBuilder message = new StringBuilder();
                for(int i = 1; i < args.length; i++) {
                    message.append(args[i]);
                    message.append(" ");
                }
                String name = sender instanceof ConsoleCommandSender ? "Server" : sender.getName();
                String s = TextUtilities.privateChat(sender.getName(), to.getName(), message.toString().trim());
                sender.sendMessage(s);
                to.sendMessage(s);
                MessageLog.finalMessageTarget.put(sender, to);
                MessageLog.finalMessageTarget.put(to, sender);
                return true;
            }
            sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + TextUtilities.CHAT_COMMAND_ERROR_NO_FINAL_MESSAGE_TARGET);
            return true;
        }
        sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + ChatColor.GRAY + getUsage());
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return null;
    }

}
