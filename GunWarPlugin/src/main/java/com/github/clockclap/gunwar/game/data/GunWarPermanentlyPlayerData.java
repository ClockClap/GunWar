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

package com.github.clockclap.gunwar.game.data;

import com.github.clockclap.gunwar.GunWar;
import com.github.clockclap.gunwar.GwPlugin;
import com.github.clockclap.gunwar.achievement.GwAchievement;
import com.github.clockclap.gunwar.item.GwGunItem;
import com.github.clockclap.gunwar.item.GwItem;
import com.github.clockclap.gunwar.mysql.GwMySQL;
import com.github.clockclap.gunwar.mysql.GwMySQLDataPath;
import com.github.clockclap.gunwar.util.GunWarPluginConfiguration;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

@GwPlugin
public class GunWarPermanentlyPlayerData implements PermanentlyPlayerData, Serializable {

    private final UUID uniqueId;
    private File dataFile;
    private int coins;
    private List<GwItem> items;
    private List<GwItem> gifts;
    private List<GwAchievement> achievements;
    private Map<GwGunItem, Integer> playCount;
    private Map<GwGunItem, Integer> killCount;
    private int deathCount;
    private int infectedCount;

    public GunWarPermanentlyPlayerData(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.coins = 0;
        this.items = new ArrayList<>();
        this.gifts = new ArrayList<>();
        this.achievements = new ArrayList<>();
        this.playCount = new HashMap<>();
        this.killCount = new HashMap<>();
        this.deathCount = 0;
        this.infectedCount = 0;
        this.dataFile = new File(((GunWarPluginConfiguration) GunWar.getConfig()).getDataFolder().getPath() + "/players/" + uniqueId);
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public int getCoins() {
        return coins;
    }

    @Override
    public List<GwItem> getItemInProcessions() {
        return items;
    }

    @Override
    public List<GwItem> getGifts() {
        return gifts;
    }

    @Override
    public List<GwAchievement> getAchievements() {
        return achievements;
    }

    @Override
    public Map<GwGunItem, Integer> getPlayCount() {
        return playCount;
    }

    @Override
    public Map<GwGunItem, Integer> getKillCount() {
        return killCount;
    }

    @Override
    public int getDeathCount() {
        return Math.max(0, deathCount);
    }

    @Override
    public int getInfectedCount() {
        return Math.max(0, infectedCount);
    }

    @Override
    public void setCoins(int coins) {
        this.coins = coins;
    }

    @Override
    public void setDeathCount(int deathCount) {
        this.deathCount = deathCount;
    }

    @Override
    public void setInfectedCount(int infectedCount) {
        this.infectedCount = infectedCount;
    }

    @Override
    public File getDefaultDataFile() {
        return dataFile;
    }

    private Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("uniqueId", uniqueId);
        result.put("coins", coins);
        result.put("items", items);
        result.put("gifts", gifts);
        result.put("achievements", achievements);
        result.put("playCount", playCount);
        result.put("killCount", killCount);
        result.put("deathCount", deathCount);
        result.put("infectCount", infectedCount);
        return result;
    }

    private void initialize() {
        this.coins = 0;
        this.items.clear();
        this.gifts.clear();
        this.achievements.clear();
        this.playCount.clear();
        this.killCount.clear();
        this.deathCount = 0;
        this.infectedCount = 0;
    }

    @SuppressWarnings({"unchecked"})
    private void fromMap(Map<String, Object> map) {
        if(map.get("uniqueId") == uniqueId) {
            initialize();
            int a = (Integer) map.get("coins");
            List<GwItem> b = (List<GwItem>) map.get("items");
            List<GwItem> c = (List<GwItem>) map.get("gifts");
            List<GwAchievement> h = (List<GwAchievement>) map.get("achievements");
            Map<GwGunItem, Integer> d = (Map<GwGunItem, Integer>) map.get("playCount");
            Map<GwGunItem, Integer> e = (Map<GwGunItem, Integer>) map.get("killCount");
            int f = (Integer) map.get("deathCount");
            int g = (Integer) map.get("infectCount");
            this.coins = a;
            if(b != null) this.items = b;
            if(c != null) this.gifts = c;
            if(h != null) this.achievements = h;
            if(d != null) this.playCount = d;
            if(e != null) this.killCount = e;
            this.deathCount = f;
            this.infectedCount = g;
        }
    }

    @Override
    public void save(File file) throws IOException {
        save(this, file);
    }

    private void save(Object obj, File file) throws IOException {
        File f = file;
        try {
            GwMySQL.insertPlayerData(getUniqueId(), f.getPath());
            f = new File(GwMySQL.getPath(getUniqueId()));
            if(!f.exists()) {
                f = file;
                GwMySQL.updatePlayerData(getUniqueId(), f.getPath());
            }
            GwMySQLDataPath.insert(getUniqueId(), f.getPath());
            File f_ = new File(((GunWarPluginConfiguration) GunWar.getConfig()).getDataFolder().getPath() + "/cache/" + getUniqueId().toString());
            if(!f_.exists()) {
                f_.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(f_);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(GwMySQLDataPath.get(getUniqueId()));
            oos.flush();
            oos.close();
            fos.close();
        } catch (SQLException ex) {
            File f_ = new File(((GunWarPluginConfiguration) GunWar.getConfig()).getDataFolder().getPath() + "/cache/" + getUniqueId().toString());
            if(!f_.exists()) {
                f_.createNewFile();
                GwMySQLDataPath.insert(getUniqueId(), ((GunWarPluginConfiguration) GunWar.getConfig()).getDataFolder().getPath()
                        + "/players/" + getUniqueId().toString());
                FileOutputStream fos = new FileOutputStream(f_);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(GwMySQLDataPath.get(getUniqueId()));
                oos.flush();
                oos.close();
                fos.close();
            }
            try {
                FileInputStream fis = new FileInputStream(f_);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object obj_ = ois.readObject();
                ois.close();
                fis.close();
                String str = "";
                if(obj_ instanceof GwMySQLDataPath) {
                    GwMySQLDataPath p = (GwMySQLDataPath) obj_;
                    str = p.getPath();
                }
                if(!str.isEmpty() && !str.equalsIgnoreCase(f.getPath())) {
                    f = new File(str);
                }
            } catch (ClassNotFoundException ignored) { }
        }
        if(!f.exists()) {
            if(!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            f.createNewFile();
        }
        Map<String, Object> map = toMap();
        FileOutputStream output = new FileOutputStream(f);
        ObjectOutputStream objectOutput = new ObjectOutputStream(output);
        objectOutput.writeObject(map);
        objectOutput.flush();
        objectOutput.close();
        output.close();
        if(!file.getPath().equalsIgnoreCase(f.getPath())) {
            FileOutputStream output_ = new FileOutputStream(file);
            ObjectOutputStream objectOutput_ = new ObjectOutputStream(output_);
            objectOutput_.writeObject(map);
            objectOutput_.flush();
            objectOutput_.close();
            output_.close();
        }
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void load(File file) throws IOException, ClassNotFoundException {
        File f = file;
        try {
            GwMySQL.insertPlayerData(getUniqueId(), f.getPath());
            f = new File(GwMySQL.getPath(getUniqueId()));
            if(!f.exists()) {
                f = file;
                GwMySQL.updatePlayerData(getUniqueId(), f.getPath());
            }
            GwMySQLDataPath.insert(getUniqueId(), f.getPath());
            File f_ = new File(((GunWarPluginConfiguration) GunWar.getConfig()).getDataFolder().getPath() + "/cache/" + getUniqueId().toString());
            if(!f_.exists()) {
                f_.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(f_);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(GwMySQLDataPath.get(getUniqueId()));
            oos.flush();
            oos.close();
            fos.close();
        } catch (SQLException ex) {
            File f_ = new File(((GunWarPluginConfiguration) GunWar.getConfig()).getDataFolder().getPath() + "/cache/" + getUniqueId().toString());
            if(!f_.exists()) {
                f_.createNewFile();
                GwMySQLDataPath.insert(getUniqueId(), ((GunWarPluginConfiguration) GunWar.getConfig()).getDataFolder().getPath()
                        + "/players/" + getUniqueId().toString());
                FileOutputStream fos = new FileOutputStream(f_);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(GwMySQLDataPath.get(getUniqueId()));
                oos.flush();
                oos.close();
                fos.close();
            }
            try {
                FileInputStream fis = new FileInputStream(f_);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object obj_ = ois.readObject();
                ois.close();
                fis.close();
                String str = "";
                if(obj_ instanceof GwMySQLDataPath) {
                    GwMySQLDataPath p = (GwMySQLDataPath) obj_;
                    str = p.getPath();
                }
                if(!str.isEmpty() && !str.equalsIgnoreCase(f.getPath())) {
                    f = new File(str);
                }
            } catch (ClassNotFoundException ignored) { }
        }
        if(f.exists()) {
            FileInputStream input = new FileInputStream(f);
            ObjectInputStream objectInput = new ObjectInputStream(input);
            Object object = objectInput.readObject();
            Class<?> clazz = object.getClass();
            if(object instanceof Map) fromMap((Map<String, Object>) object);
            objectInput.close();
            input.close();
        }
    }
}
