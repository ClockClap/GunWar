package com.github.clockclap.gunwar.game;

import com.github.clockclap.gunwar.game.data.ItemData;
import com.github.clockclap.gunwar.game.gamemode.GwGameMode;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import com.github.clockclap.gunwar.game.data.PermanentlyPlayerData;
import com.github.clockclap.gunwar.game.data.PlayerData;

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

    public void start();

    public void stop();

    public Location getLocation();

    public void setLocation(Location location);

    public int getStartingAt();

}
