package xyz.n7mn.dev.gunwar.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.game.data.ItemData;
import xyz.n7mn.dev.gunwar.game.data.PermanentlyPlayerData;
import xyz.n7mn.dev.gunwar.game.data.PlayerData;
import xyz.n7mn.dev.gunwar.game.gamemode.GwGameMode;
import xyz.n7mn.dev.gunwar.game.gamemode.GwGameModes;
import xyz.n7mn.dev.gunwar.util.Reference;

import java.util.*;

public class GunWarGame implements Game {

    private final Plugin plugin;
    private GameState state;
    private Map<UUID, PlayerData> dataMap;
    private Map<UUID, PermanentlyPlayerData> permanentlyPlayerDataMap;
    private GwGameMode gameMode;
    private List<ItemData> itemDataList;
    private BossBar bar;

    public GunWarGame(Plugin plugin) {
        this.plugin = plugin;
        this.state = GameState.WAITING;
        this.dataMap = new HashMap<>();
        this.permanentlyPlayerDataMap = new HashMap<>();
        this.gameMode = GwGameModes.NORMAL;
        this.itemDataList = new ArrayList<>();
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

    public void removePermanentlyPlayerData(UUID uniqueId) {
        permanentlyPlayerDataMap.remove(uniqueId);
    }

    @Override
    public Collection<PlayerData> getOnlinePlayerData() {
        return dataMap.values();
    }

    @Override
    public BossBar getBar() {
        return bar;
    }

    @Override
    public void setBar(BossBar bar) {
        this.bar = bar;
    }

    @Override
    public GwGameMode getGameMode() {
        return gameMode;
    }

    @Override
    public ItemData getItemData(ItemStack item) {
        if(item != null && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            List<ItemData> dataList = itemDataList;
            for (ItemData data : dataList) if(meta.getLore().contains(ChatColor.DARK_GRAY + data.getGwItem().getId())) return data;
        }
        return null;
    }

    public void addItemData(ItemData data) {
        itemDataList.add(data);
    }

    @Override
    public void setGameMode(GwGameMode gameMode) {
        if(getState() == GameState.WAITING) this.gameMode = gameMode;
    }

    @Override
    public void start(Location loc) {
        int prepare = GunWar.getConfig().getConfig().getInt("", 10);
        setState(GameState.STARTING);
        final int[] t = { prepare + 1 };
        getBar().setColor(BarColor.YELLOW);
        new BukkitRunnable() {
            @Override
            public void run() {
                t[0]--;
                if(t[0] <= 0) {
                    this.cancel();
                    return;
                }
                getBar().setTitle(Reference.BOSSBAR_STARTING.replaceAll("%SECOND%", Integer.toString(t[0])));
                if(t[0] <= 5) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
                    }
                }
            }
        }.runTaskTimer(GunWar.getPlugin(), 0, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                setState(GameState.PLAYING);
                getGameMode().start(loc);
            }
        }.runTaskLater(GunWar.getPlugin(), prepare);
    }

    @Override
    public void stop() {
        setState(GameState.WAITING);
        getGameMode().stop();
    }
}
