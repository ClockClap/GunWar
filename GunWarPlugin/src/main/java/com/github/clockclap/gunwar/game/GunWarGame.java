/*
 * Copyright (c) 2021, ClockClap. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.github.clockclap.gunwar.game;

import com.github.clockclap.gunwar.GunWar;
import com.github.clockclap.gunwar.GwPlugin;
import com.github.clockclap.gunwar.game.data.CraftPlayerData;
import com.github.clockclap.gunwar.game.data.ItemData;
import com.github.clockclap.gunwar.game.data.PermanentPlayerData;
import com.github.clockclap.gunwar.game.data.PlayerData;
import com.github.clockclap.gunwar.game.gamemode.GameModeNormal;
import com.github.clockclap.gunwar.game.gamemode.GwGameMode;
import com.github.clockclap.gunwar.game.gamemode.GwGameModes;
import com.github.clockclap.gunwar.util.DataList;
import com.github.clockclap.gunwar.util.TextReference;
import com.github.clockclap.gunwar.util.world.Worlds;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

@GwPlugin
public class GunWarGame implements Game {

    private final Plugin plugin;
    private GameState state;
    private Map<UUID, PlayerData> dataMap;
    private Map<UUID, PermanentPlayerData> permanentlyPlayerDataMap;
    private GwGameMode gameMode;
    private List<ItemData> itemDataList;
    private Collection<Player> playersJoined;
    private BossBar bar;
    private Location loc;
    private int startingAt;

    public GunWarGame(Plugin plugin) {
        this.plugin = plugin;
        this.state = GameState.WAITING;
        this.dataMap = new HashMap<>();
        this.permanentlyPlayerDataMap = new HashMap<>();
        this.gameMode = GwGameModes.NORMAL;
        this.itemDataList = new ArrayList<>();
        World world = Worlds.getWorld(GunWar.getPluginConfigs().getConfig().getString("game.startloc.world", "world"));
        double x = GunWar.getPluginConfigs().getConfig().getDouble("game.startloc.x", 0D);
        double y = GunWar.getPluginConfigs().getConfig().getDouble("game.startloc.y", 0D);
        double z = GunWar.getPluginConfigs().getConfig().getDouble("game.startloc.z", 0D);
        this.loc = new Location(world, x, y, z, 0F, 0F);
        this.startingAt = GunWar.getPluginConfigs().getConfig().getInt("game.prepare", 10);
        this.playersJoined = new ArrayList<>();
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
    public PermanentPlayerData getPermanentPlayerData(UUID uniqueId) {
        return permanentlyPlayerDataMap.get(uniqueId);
    }

    public void addPermanentlyPlayerData(PermanentPlayerData data) {
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
            if(meta.getLore() != null) {
                List<ItemData> dataList = itemDataList;
                for (ItemData data : dataList)
                    if (meta.getLore().contains(ChatColor.DARK_GRAY + data.getGwItem().getId())) return data;
            }
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
    public void start(Object... args) {
        int prepare = GunWar.getPluginConfigs().getConfig().getInt("game.prepare", 10);
        setState(GameState.STARTING);
        getBar().setColor(BarColor.YELLOW);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(startingAt <= 0) {
                    this.cancel();
                    return;
                }
                getBar().setTitle(TextReference.BOSSBAR_STARTING.replaceAll("%SECOND%", Integer.toString(startingAt)));
                if(startingAt <= 5) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
                    }
                }
                startingAt--;
            }
        }.runTaskTimer(GunWar.getPlugin(), 0, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                setState(GameState.PLAYING);
                getGameMode().start(args);
                startingAt = GunWar.getPluginConfigs().getConfig().getInt("game.prepare", 10);
                getJoinedPlayers().clear();
            }
        }.runTaskLater(GunWar.getPlugin(), prepare);
    }

    @Override
    public void start() {
        if(getGameMode() == GwGameModes.NORMAL) {
            if(((GameModeNormal) getGameMode()).getMode() == GameModeNormal.Mode.SOLO) {
                String w = GunWar.getPluginConfigs().getConfig().getString("game.startloc.world", "world");
                double x = GunWar.getPluginConfigs().getConfig().getDouble("game.startloc.x", 0);
                double y = GunWar.getPluginConfigs().getConfig().getDouble("game.startloc.y", 0);
                double z = GunWar.getPluginConfigs().getConfig().getDouble("game.startloc.z", 0);
                float ya = (float) GunWar.getPluginConfigs().getConfig().getDouble("game.startloc.yaw", 0);
                float pi = (float) GunWar.getPluginConfigs().getConfig().getDouble("game.startloc.pitch", 0);
                World ww = Bukkit.getWorld(w);
                Location loc = new Location(ww, x, y, z, ya, pi);
                start(loc);
                return;
            } else {
                String rw = GunWar.getPluginConfigs().getConfig().getString("game.normal.startloc-red.world", "world");
                double rx = GunWar.getPluginConfigs().getConfig().getDouble("game.normal.startloc-red.x", 0);
                double ry = GunWar.getPluginConfigs().getConfig().getDouble("game.normal.startloc-red.y", 0);
                double rz = GunWar.getPluginConfigs().getConfig().getDouble("game.normal.startloc-red.z", 0);
                float rya = (float) GunWar.getPluginConfigs().getConfig().getDouble("game.normal.startloc-red.yaw", 0);
                float rpi = (float) GunWar.getPluginConfigs().getConfig().getDouble("game.normal.startloc-red.pitch", 0);
                World wrw = Bukkit.getWorld(rw);
                String bw = GunWar.getPluginConfigs().getConfig().getString("game.normal.startloc-blue.world", "world");
                double bx = GunWar.getPluginConfigs().getConfig().getDouble("game.normal.startloc-blue.x", 0);
                double by = GunWar.getPluginConfigs().getConfig().getDouble("game.normal.startloc-blue.y", 0);
                double bz = GunWar.getPluginConfigs().getConfig().getDouble("game.normal.startloc-blue.z", 0);
                float bya = (float) GunWar.getPluginConfigs().getConfig().getDouble("game.normal.startloc-blue.yaw", 0);
                float bpi = (float) GunWar.getPluginConfigs().getConfig().getDouble("game.normal.startloc-blue.pitch", 0);
                World wbw = Bukkit.getWorld(bw);
                Location r = new Location(wrw, rx, ry, rz, rya, rpi);
                Location b = new Location(wbw, bx, by, bz, bya, bpi);
                start(r, b);
                return;
            }
        }
        start(loc);
    }

    @Override
    public void stop() {
        setState(GameState.WAITING);
        String world = GunWar.getPluginConfigs().getConfig().getString("game.default-spawn.world", "world");
        double x = GunWar.getPluginConfigs().getConfig().getDouble("game.default-spawn.x", 0);
        double y = GunWar.getPluginConfigs().getConfig().getDouble("game.default-spawn.y", 0);
        double z = GunWar.getPluginConfigs().getConfig().getDouble("game.default-spawn.z", 0);
        World w = Bukkit.getWorld(world);
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p != null) {
                CraftPlayerData data = (CraftPlayerData) GunWar.getPlayerData(p);
                p.teleport(new Location(w, x, y, z));
                data.setTeam(-1);
                data.setDead(false);
                data.setHealth(100);
                data.setMaxHealth(100);
                data.setSpectator(true);
                data.setMoveable(true);
            }
        }
        for(Location l : DataList.brokenGlasses) {
            l.getBlock().setType(Material.GLASS);
        }
        getGameMode().stop();
    }

    @Override
    public Location getLocation() {
        return loc;
    }

    @Override
    public void setLocation(Location location) {
        loc = location;
    }

    public int getStartingAt() {
        return startingAt;
    }

    @Override
    public void randomizeTeams(Collection<Player> players, int... teams) {
        if(teams.length <= 0) throw new IllegalArgumentException("teams length must be more than 0");
        List<Player> playerList = new ArrayList<>(players);
        Collections.shuffle(playerList);
        Random rand = new Random();
        int i = 0;
        int[] n = new int[teams.length];
        for(Player p : playerList) {
            if(p == null) continue;
            PlayerData d = getPlayerData(p);
            int smallest = 0;
            for(int j = 1; j < n.length; j++) {
                int nj = n[j];
                if(nj < n[smallest]) {
                    smallest = j;
                    continue;
                }
                if(nj == n[smallest]) {
                    Random r = new Random();
                    if(r.nextBoolean()) smallest = j;
                }
            }
            int t = teams[smallest];
            d.setTeam(t);
            i++;
        }
    }

    @Override
    public Collection<PlayerData> toPlayerData(Collection<Player> players) {
        List<PlayerData> playerData = new ArrayList<>();
        for(Player p : players) {
            if(p == null) continue;
            PlayerData d = getPlayerData(p);
            playerData.add(d);
        }
        return playerData;
    }

    @Override
    public Collection<Player> getJoinedPlayers() {
        return playersJoined;
    }

    @Override
    public Collection<PlayerData> calculateGamingPlayers() {
        Collection<PlayerData> gamingPlayers = new ArrayList<>();
        Collection<? extends PlayerData> players = getOnlinePlayerData();
        for(PlayerData d : players) {
            if(d != null && !d.isSpectator()) gamingPlayers.add(d);
        }
        return gamingPlayers;
    }
}
