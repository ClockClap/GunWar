package xyz.n7mn.dev.gunwar.game.gamemode;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.game.Game;

public class GameModeNormal extends GwGameMode implements Shoutable {

    public enum Mode {
        TEAM, SOLO
    }

    private Mode mode;

    public GameModeNormal() {
        super(0);
        setName("NORMAL");
        setDisplayName(ChatColor.GREEN + "Normal");
        this.mode = Mode.SOLO;
        setGameTime(GunWar.getConfig().getConfig().getInt("normal.game-time-solo"));
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

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        if(mode == Mode.SOLO) {
            setGameTime(GunWar.getConfig().getConfig().getInt("normal.game-time-solo"));
        } else if(mode == Mode.TEAM) {
            setGameTime(GunWar.getConfig().getConfig().getInt("normal.game-time-team"));
        }
    }
}
