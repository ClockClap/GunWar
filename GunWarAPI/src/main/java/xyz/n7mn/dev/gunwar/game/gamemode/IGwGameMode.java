package xyz.n7mn.dev.gunwar.game.gamemode;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public interface IGwGameMode {

    public int getIndex();

    public String getName();

    public String getDisplayName();

    public int getGameTime();

    public int getElapsedTime();

    public int getRemainingTime();

    public void start(Location loc);

    public void stop();

}
