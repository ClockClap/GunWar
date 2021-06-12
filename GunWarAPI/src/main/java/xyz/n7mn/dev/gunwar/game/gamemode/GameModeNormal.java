package xyz.n7mn.dev.gunwar.game.gamemode;

import org.bukkit.ChatColor;

class GameModeNormal extends GwGameMode {

    public GameModeNormal() {
        super(0);
        setName("NORMAL");
        setDisplayName(ChatColor.GREEN + "Normal");
    }

}
