package xyz.n7mn.dev.gunwar.game.gamemode;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import xyz.n7mn.dev.gunwar.GunWar;

public class GameModeCastleSiege extends GwGameMode {

    public GameModeCastleSiege() {
        super(2);
        setName("CASTLE_SIEGE");
        setDisplayName(ChatColor.AQUA + "Castle Siege");
        setGameTime(0);
    }

    @Override
    public void start(Location loc) {

    }

    @Override
    public void stop() {

    }
}
