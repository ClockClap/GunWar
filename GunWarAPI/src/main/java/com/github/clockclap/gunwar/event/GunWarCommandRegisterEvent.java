package com.github.clockclap.gunwar.event;

import org.bukkit.command.Command;
import org.bukkit.event.HandlerList;
import org.bukkit.event.server.ServerEvent;

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
