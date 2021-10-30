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

package com.github.clockclap.gunwar;

import com.github.clockclap.gunwar.game.Game;
import com.github.clockclap.gunwar.util.PermissionInfo;
import com.github.clockclap.gunwar.util.config.GunWarConfiguration;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.List;

@GwAPI(since = 2)
public abstract class GunWarCommand extends Command {

    protected GunWarCommand(String name) {
        super(name);
    }

    protected GunWarCommand(String name, String description, String usageMessage, List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    protected GunWarManager getManager() {
        return GunWar.getManager();
    }

    protected PermissionInfo testPermission(Player player, int required) {
        return GunWar.getManager().testPermission(player, required);
    }

    protected GunWarConfiguration getPluginConfigs() {
        return GunWar.getPluginConfigs();
    }

    protected String getPluginVersion() {
        return GunWar.getPluginVersion();
    }

    protected int getRequiredPermission(String permission, int def) {
        return GunWar.getRequiredPermission(permission, def);
    }

    protected Game getGame() {
        return GunWar.getGame();
    }

}
