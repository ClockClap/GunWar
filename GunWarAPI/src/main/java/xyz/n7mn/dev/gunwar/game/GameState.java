package xyz.n7mn.dev.gunwar.game;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public enum GameState {
    WAITING(0, "WAITING"),
    STARTING(1, "STARTING"),
    PLAYING(2, "PLAYING"),
    ENDING(3, "ENDING");

    private final int index;
    private final String name;

    private GameState(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
