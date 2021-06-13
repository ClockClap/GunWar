package xyz.n7mn.dev.gunwar.game;

import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.gunwar.game.data.ItemData;
import xyz.n7mn.dev.gunwar.game.data.PermanentlyPlayerData;
import xyz.n7mn.dev.gunwar.game.data.PlayerData;
import xyz.n7mn.dev.gunwar.game.gamemode.GwGameMode;

import java.util.Collection;
import java.util.UUID;

public interface Game {

    public Plugin getPlugin();

    public GameState getState();

    public void setState(GameState state);

    public PlayerData getPlayerData(Player player);

    public PermanentlyPlayerData getPermanentlyPlayerData(UUID uniqueId);

    public Collection<PlayerData> getOnlinePlayerData();

    public BossBar getBar();

    public void setBar(BossBar bar);

    public GwGameMode getGameMode();

    public ItemData getItemData(ItemStack item);

    public void setGameMode(GwGameMode gamemode);

    public void start(Location loc);

    public void stop();

}
