package xyz.n7mn.dev.gunwar.game;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.gunwar.game.data.ItemData;
import xyz.n7mn.dev.gunwar.game.data.PermanentlyPlayerData;
import xyz.n7mn.dev.gunwar.game.data.PlayerData;
import xyz.n7mn.dev.gunwar.game.gamemode.GwGameMode;
import xyz.n7mn.dev.gunwar.game.gamemode.GwGameModes;

import java.util.*;

public class GunWarGame implements Game {

    private final Plugin plugin;
    private GameState state;
    private Map<UUID, PlayerData> dataMap;
    private Map<UUID, PermanentlyPlayerData> permanentlyPlayerDataMap;
    private GwGameMode gameMode;
    private Map<ItemStack, ItemData> itemDataMap;

    public GunWarGame(Plugin plugin) {
        this.plugin = plugin;
        this.state = GameState.WAITING;
        this.dataMap = new HashMap<>();
        this.permanentlyPlayerDataMap = new HashMap<>();
        this.gameMode = GwGameModes.NORMAL;
        this.itemDataMap = new HashMap<>();
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public void setState(GameState state) {
        this.state = state;
    }

    public void addPlayerData(UUID uniqueId, PlayerData data) {
        dataMap.put(uniqueId, data);
    }

    public void removePlayerData(UUID uniqueId) {
        dataMap.remove(uniqueId);
    }

    @Override
    public PlayerData getPlayerData(Player player) {
        return dataMap.get(player.getUniqueId());
    }

    @Override
    public PermanentlyPlayerData getPermanentlyPlayerData(UUID uniqueId) {
        return permanentlyPlayerDataMap.get(uniqueId);
    }

    public void addPermanentlyPlayerData(PermanentlyPlayerData data) {
        permanentlyPlayerDataMap.put(data.getUniqueId(), data);
    }

    @Override
    public Collection<PlayerData> getOnlinePlayerData() {
        return dataMap.values();
    }

    @Override
    public GwGameMode getGameMode() {
        return gameMode;
    }

    @Override
    public ItemData getItemData(ItemStack item) {
        return itemDataMap.get(item);
    }

    public void addItemData(ItemData data) {
        itemDataMap.put(data.getItem(), data);
    }

    @Override
    public void setGameMode(GwGameMode gameMode) {
        this.gameMode = gameMode;
    }
}
