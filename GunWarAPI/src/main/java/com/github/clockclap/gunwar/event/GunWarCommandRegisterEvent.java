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

package com.github.clockclap.gunwar.event;

import com.github.clockclap.gunwar.GwAPI;
import org.bukkit.command.Command;
import org.bukkit.event.HandlerList;
import org.bukkit.event.server.ServerEvent;

@GwAPI
public class GunWarCommandRegisterEvent extends ServerEvent {

    private static final HandlerList handlers = new HandlerList();
    private final Command command;
    private final Throwable throwable;
    private boolean isCaught = false;

    public GunWarCommandRegisterEvent(Command command, Throwable throwable) {
        super();
        this.command = command;
        this.throwable = throwable;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Command getCommand() {
        return command;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void catchThrowable() {
        isCaught = true;
    }

    public boolean isCaught() {
        return isCaught;
    }
}
