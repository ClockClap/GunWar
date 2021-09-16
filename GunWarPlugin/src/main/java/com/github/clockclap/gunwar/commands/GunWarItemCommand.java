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
import com.github.clockclap.gunwar.item.GwItem;
import com.github.clockclap.gunwar.item.GwItems;
import com.github.clockclap.gunwar.util.PermissionInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.github.clockclap.gunwar.game.data.PlayerData;
import com.github.clockclap.gunwar.util.TextUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GunWarItemCommand extends Command {

    public GunWarItemCommand() {
        super("gunwaritem", TextUtilities.MISC_DESCRIPTION_COMMAND_GUNWARITEM, "Usage: /gunwaritem <player> <item>", Arrays.asList("gwitem", "gwi"));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        int required = GunWar.getConfig().getPermissionSetting().getInt("commands.gunwaritem", 1);
        if(sender instanceof Player) {
            Player p = (Player) sender;
            PermissionInfo info = GunWar.getUtilities().testPermission(p, required);
            if(!info.isPassed()) {
                p.sendMessage(TextUtilities.getChatCommandPermissionError(info.getRequired(), info.getCurrent()));
                return true;
            }
        }
        if(args.length >= 2) {
            Player p = Bukkit.getPlayer(args[0]);
            if(p == null) {
                sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + TextUtilities.CHAT_COMMAND_ERROR_UNKNOWN_PLAYER);
                return true;
            }
            PlayerData data = GunWar.getGame().getPlayerData(p);
            for (GwItem i : GwItems.getRegisteredItems()) {
                if (i.getName().equalsIgnoreCase(args[1])) {
                    int amount = 1;
                    if(args[2] != null) {
                        try {
                            int am = Integer.parseInt(args[2]);
                            if(am < 1) {
                                sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + ChatColor.GRAY + getUsage());
                                return true;
                            }
                            amount = am;
                        } catch(NumberFormatException e) {
                            sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + ChatColor.GRAY + getUsage());
                            e.printStackTrace();
                            return true;
                        }
                    }
                    for(int j = 0; j < amount; j++) data.giveItem(i);
                    sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + TextUtilities.CHAT_COMMAND_GIVE_ITEM
                            .replaceAll("%PLAYER%", p.getName()).replaceAll("%ITEM%", i.getName()));
                    return true;
                }
            }
        }
        sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + ChatColor.GRAY + getUsage());
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if(args.length == 1) {
            List<String> result = new ArrayList<>();
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(p.getName().startsWith(args[0]))
                result.add(p.getName());
            }
            return result;
        }
        if(args.length == 2) {
            List<String> result = new ArrayList<>();
            for(GwItem i : GwItems.getRegisteredItems()) {
                if(i.getName().startsWith(args[1]))
                result.add(i.getName());
            }
            return result;
        }
        return null;
    }
}
