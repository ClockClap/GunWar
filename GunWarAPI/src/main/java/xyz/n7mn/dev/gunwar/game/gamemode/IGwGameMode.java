package xyz.n7mn.dev.gunwar.game.gamemode;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.Map;

public interface IGwGameMode {

    public int getIndex();

    public String getName();

    public String getDisplayName();

    public int getGameTime();

    public int getElapsedTime();

    public int getRemainingTime();

    public Map<Integer, String> getTeamNames();

    public Map<Integer, ChatColor> getTeamColors();

    public void start(Location loc);

    public void stop();

}
