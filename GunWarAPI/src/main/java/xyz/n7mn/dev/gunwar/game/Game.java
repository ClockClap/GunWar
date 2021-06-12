package xyz.n7mn.dev.gunwar.game;

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

    public GwGameMode getGameMode();

    public ItemData getItemData(ItemStack item);

    public void setGameMode(GwGameMode gamemode);

}
