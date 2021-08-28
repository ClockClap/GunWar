package com.github.clockclap.gunwar.game.gamemode;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import com.github.clockclap.gunwar.GunWar;

public class GameModeZombieEscape extends GwGameMode {

    public GameModeZombieEscape() {
        super(1);
        setName("ZOMBIE_ESCAPE");
        setDisplayName(ChatColor.DARK_GREEN + "Zombie Escape");
        setGameTime(GunWar.getConfig().getConfig().getInt("zombie-escape.game-time"));
        getTeamNames().put(0, "SURVIVOR");
        getTeamColors().put(0, ChatColor.DARK_AQUA);
        getTeamNames().put(1, "ZOMBIE");
        getTeamColors().put(1, ChatColor.DARK_GREEN);
    }

    @Override
    public void start(Location loc) {

    }

    @Override
    public void stop() {

    }
}
