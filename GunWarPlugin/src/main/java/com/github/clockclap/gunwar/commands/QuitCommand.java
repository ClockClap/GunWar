/*
 * Copyright (c) 2021, ClockClap. All rights reserved.
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

package com.github.clockclap.gunwar.commands;

import com.github.clockclap.gunwar.GunWarCommand;
import com.github.clockclap.gunwar.LoggableDefault;
import com.github.clockclap.gunwar.game.data.PlayerData;
import com.github.clockclap.gunwar.util.TextReference;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class QuitCommand extends GunWarCommand implements LoggableDefault {

    public QuitCommand() {
        super("quit", "", "Usage: /quit", Arrays.asList("q"));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            PlayerData d = getPlayerData(p);
            if(!d.isJoined()) {
                sender.sendMessage(TextReference.CHAT_PREFIX + " " + TextReference.CHAT_GAME_ERROR_NOT_JOINED_YET);
                return true;
            }
            d.quit();
            Bukkit.broadcastMessage(TextReference.CHAT_PREFIX + " " + TextReference.CHAT_GAME_PLAYER_QUIT);
            return true;
        }
        sender.sendMessage(TextReference.CHAT_PREFIX + " " + TextReference.CHAT_COMMAND_ERROR_ONLY_PLAYER);
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return null;
    }

}
