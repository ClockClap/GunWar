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
import com.github.clockclap.gunwar.game.GameState;
import com.github.clockclap.gunwar.game.gamemode.Shoutable;
import com.github.clockclap.gunwar.util.TextUtilities;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@GwPlugin
public class ShoutCommand extends Command {

    public ShoutCommand() {
        super("shout", "", "Usage: /shout <message>", new ArrayList<>());
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (GunWar.getGame().getState() == GameState.PLAYING) {
                if (GunWar.getGame().getGameMode() instanceof Shoutable) {
                    if(GunWar.getPlayerData(p).isSpectator()) {
                        sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + TextUtilities.CHAT_COMMAND_ERROR_CANT_USE_SPECTATOR);
                        return true;
                    }
                    if(args.length >= 1 && args[0] != null) {
                        String message = String.join(" ", args);
                        Bukkit.broadcastMessage(TextUtilities.chat(p.getName(), message));
                        return true;
                    }
                    sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + getUsage());
                    return true;
                }
                sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + TextUtilities.CHAT_COMMAND_ERROR_CANT_USE_THIS_MODE);
                return true;
            }
            sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + TextUtilities.CHAT_COMMAND_ERROR_GAME_NOT_STARTED);
            return true;
        }
        sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + TextUtilities.CHAT_COMMAND_ERROR_ONLY_PLAYER);
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return null;
    }
}
