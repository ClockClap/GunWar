package xyz.n7mn.dev.gunwar.game.gamemode;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import xyz.n7mn.dev.gunwar.GunWar;

public class GameModeGeneralSiege extends GwGameMode {

    public GameModeGeneralSiege() {
        super(2);
        setName("GENERAL_SIEGE");
        setDisplayName(ChatColor.AQUA + "General Siege");
        setGameTime(0);
    }

    @Override
    public void start(Location loc) {

    }

    @Override
    public void stop() {

    }
}
