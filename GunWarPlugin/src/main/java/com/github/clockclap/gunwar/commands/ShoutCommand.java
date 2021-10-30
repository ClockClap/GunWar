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
import com.github.clockclap.gunwar.game.GameState;
import com.github.clockclap.gunwar.game.gamemode.Shoutable;
import com.github.clockclap.gunwar.util.TextReference;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@GwPlugin
public class ShoutCommand extends GunWarCommand implements LoggableDefault {

    public ShoutCommand() {
        super("shout", "", "Usage: /shout <message>", new ArrayList<>());
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (getGame().getState() == GameState.PLAYING) {
                if (getGame().getGameMode() instanceof Shoutable) {
                    if(GunWar.getPlayerData(p).isSpectator()) {
                        sender.sendMessage(TextReference.CHAT_PREFIX + " " + TextReference.CHAT_COMMAND_ERROR_CANT_USE_SPECTATOR);
                        return true;
                    }
                    if(args.length >= 1 && args[0] != null) {
                        String message = String.join(" ", args);
                        Bukkit.broadcastMessage(TextReference.chat(p.getName(), message));
                        return true;
                    }
                    sender.sendMessage(TextReference.CHAT_PREFIX + " " + getUsage());
                    return true;
                }
                sender.sendMessage(TextReference.CHAT_PREFIX + " " + TextReference.CHAT_COMMAND_ERROR_CANT_USE_THIS_MODE);
                return true;
            }
            sender.sendMessage(TextReference.CHAT_PREFIX + " " + TextReference.CHAT_COMMAND_ERROR_GAME_NOT_STARTED);
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
