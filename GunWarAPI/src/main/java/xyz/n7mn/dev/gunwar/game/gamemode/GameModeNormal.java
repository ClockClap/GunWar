package xyz.n7mn.dev.gunwar.game.gamemode;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import xyz.n7mn.dev.gunwar.GunWar;

public class GameModeNormal extends GwGameMode {

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
