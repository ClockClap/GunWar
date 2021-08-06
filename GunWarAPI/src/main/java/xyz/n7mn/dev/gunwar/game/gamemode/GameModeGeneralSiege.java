package xyz.n7mn.dev.gunwar.game.gamemode;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import xyz.n7mn.dev.gunwar.GunWar;

import java.util.HashMap;

public class GameModeGeneralSiege extends GwGameMode implements Shoutable {

    public GameModeGeneralSiege() {
        super(2);
        setName("GENERAL_SIEGE");
        setDisplayName(ChatColor.AQUA + "General Siege");
        setGameTime(0);
        getTeamNames().put(0, "RED");
        getTeamColors().put(0, ChatColor.RED);
        getTeamNames().put(1, "BLUE");
        getTeamColors().put(1, ChatColor.BLUE);
    }

    @Override
    public void start(Location loc) {

    }

    @Override
    public void stop() {

    }
}
