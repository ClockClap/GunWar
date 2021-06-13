package xyz.n7mn.dev.gunwar.game.gamemode;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import xyz.n7mn.dev.gunwar.GunWar;

public class GameModeZombieEscape extends GwGameMode {

    public GameModeZombieEscape() {
        super(1);
        setName("ZOMBIE_ESCAPE");
        setDisplayName(ChatColor.DARK_GREEN + "Zombie Escape");
        setGameTime(GunWar.getConfig().getConfig().getInt("zombie-escape.game-time"));
    }

    @Override
    public void start(Location loc) {

    }

    @Override
    public void stop() {

    }
}
