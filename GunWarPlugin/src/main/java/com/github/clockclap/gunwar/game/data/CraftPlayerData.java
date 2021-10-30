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
import com.github.clockclap.gunwar.entity.HitEntity;
import com.github.clockclap.gunwar.game.GunWarGame;
import com.github.clockclap.gunwar.game.gamemode.GameModeNormal;
import com.github.clockclap.gunwar.game.gamemode.GwGameModes;
import com.github.clockclap.gunwar.item.GwGunItem;
import com.github.clockclap.gunwar.item.GwItem;
import com.github.clockclap.gunwar.item.GwKnifeItem;
import com.github.clockclap.gunwar.util.*;
import com.mojang.authlib.GameProfile;
import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldEvent;
import org.apache.commons.lang3.Validate;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.*;

@GwPlugin
@SuppressWarnings({ "all" })
public class CraftPlayerData extends CraftEntityData implements PlayerData {

    private final Player player;
    private PlayerWatcher watcher;
    private double health;
    private double maxHealth;
    private int team;
    private boolean spectator;
    private boolean clickable;
    private boolean moveable;
    private boolean dead;
    private Location loc;
    private boolean zoom;
    private boolean general;
    private float zoomLevel;
    private final PlayerData.Detail detail;

    public CraftPlayerData(Player player) {
        super(player);
        this.player = player;
        this.health = 100;
        this.maxHealth = 100;
        this.team = -1;
        this.spectator = true;
        this.clickable = true;
        this.moveable = true;
        this.loc = player.getLocation();
        this.dead = false;
        this.general = false;
        this.zoom = false;
        this.zoomLevel = 0F;
        this.detail = new PlayerData.Detail() {
            private final String oldName = player.getName();
            private Map<Player, String> nameMap = new HashMap<>();

            public String getOriginalName() {
                return oldName;
            }

            public void setName(String name) {
                nameMap.clear();
                Collection<? extends Player> players = Bukkit.getOnlinePlayers();
                for(Player p : players) {
                    nameMap.put(p, name);
                }
            }

            public void setName(Player from, String name) {
                nameMap.put(from, name);
            }

            public void setNameByMap(Map<Player, String> map) {
                nameMap = map;
            }

            public String getName(Player player) {
                return nameMap.get(player);
            }

            public void resetName(Player from) {
                setName(from, getOriginalName());
            }

            public void resetName() {
                setName(getOriginalName());
            }

            public boolean canSee(LivingEntity from) {
                return canSee(from, 100D);
            }

            public boolean canSee(LivingEntity from, double distance) {
                Validate.notNull(from);
                GunWarValidate.distance(distance);
                Player player = getPlayer();
                if(from instanceof Player && !((Player) from).canSee(player)) return false;
                if(from.getWorld().getUID().equals(player.getWorld().getUID())) {
                    Collection<PotionEffect> c = player.getActivePotionEffects();
                    for(PotionEffect e : c) if (e.getType() == PotionEffectType.INVISIBILITY) return false;

                    Location f = from.getEyeLocation();
                    Location t = player.getEyeLocation();
                    World w = player.getWorld();
                    double xf = f.getX();
                    double yf = f.getY();
                    double zf = f.getZ();
                    double xt = t.getX();
                    double yt = t.getY();
                    double zt = t.getZ();

                    double dx = xt - xf;
                    double dy = yt - yf;
                    double dz = zt - zf;

                    Vector v = new Vector(dx, dy, dz);
                    double d = v.length();
                    if(d > distance) return false;
                    double nx = dx / d;
                    double ny = dy / d;
                    double nz = dz / d;

                    for(double cx = xf, cy = yf, cz = zf;
                        cx <= xt && cy <= yt && cz <= zt;
                        cx += nx,cy += ny,cz += nz) {

                        int xi = (int)Math.round(cx);
                        int yi = (int)Math.round(cy);
                        int zi = (int)Math.round(cz);

                        Block b = w.getBlockAt(xi, yi, zi);
                        if(!BlockShape.isTransparentBlock(b.getType()) && BlockShape.isInBlock(b, cx, cy, cz)) return false;
                    }
                    return true;
                }
                return false;
            }

            private void a(String name) {
                GameProfile gameProfile = ((CraftPlayer) getPlayer()).getHandle().getProfile();
                try {
                    Field field = gameProfile.getClass().getDeclaredField("name");
                    field.setAccessible(true);
                    field.set(gameProfile, name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            /**
             * Shows player of current player data from player
             *
             * @param player player
             */
            public void show(Player player) {
                if(nameMap.containsKey(player)) a(nameMap.get(player));
                player.showPlayer(GunWar.getPlugin(), getPlayer());
                a(oldName);
            }

            /**
             * Hides player of current player data from player
             *
             * @param player player
             */
            public void hide(Player player) {
                player.hidePlayer(GunWar.getPlugin(), getPlayer());
            }

            public void updateName() {
                Collection<? extends Player> players = Bukkit.getOnlinePlayers();
                for(Player p : players) {
                    updateName(p);
                }
            }

            public void updateName(Player player) {
                hide(player);
                show(player);
            }
        };
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public PlayerWatcher getWatcher() {
        return watcher;
    }

    public void setWatcher(PlayerWatcher watcher) {
        this.watcher = watcher;
    }

    public double getHealth() {
        return health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    @Override
    public int getTeam() {
        return team;
    }

    @Override
    public boolean isSpectator() {
        return spectator;
    }

    @Override
    public boolean isGeneral() {
        return general;
    }

    @Override
    public boolean isClickable() {
        return clickable;
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    public boolean isMoveable() {
        return moveable;
    }

    @Override
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    @Override
    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    @Override
    public void setGeneral(boolean general) {
        this.general = general;
    }

    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    @Override
    public void setHealth(double health) {
        this.health = Math.min(health, maxHealth);
    }

    @Override
    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public void setTeam(int team) {
        this.team = team;
        if(team < 0) setSpectator(true);
    }

    @Override
    public void setSpectator(boolean spectator) {
        this.spectator = spectator;
    }

    private boolean inf = false;

    @Override
    public void infect() {
        if(!dead && !inf) {
            setTeam(1);
            inf = true;
            moveable = false;
            loc = getPlayer().getLocation();
            getPlayer().sendTitle(TextReference.TITLE_MAIN_INFECT, TextReference.TITLE_SUB_INFECT, 0, 100, 20);
            new BukkitRunnable() {
                @Override
                public void run() {
                    moveable = true;
                    inf = false;
                }
            }.runTaskLater(GunWar.getPlugin(), 100);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (moveable) {
                        this.cancel();
                        return;
                    }
                    Location location = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(),
                            getPlayer().getLocation().getYaw(), getPlayer().getLocation().getPitch());
                    getPlayer().teleport(location);
                }
            }.runTaskTimer(GunWar.getPlugin(), 0, 1);
        }
    }

    @Override
    public void kill() {
        if(!dead && !inf) {
            moveable = false;
            dead = true;
            loc = getPlayer().getLocation();
            getPlayer().sendTitle(TextReference.TITLE_MAIN_DIED_ZOMBIE, TextReference.TITLE_SUB_INFECT, 0, 100, 20);
            new BukkitRunnable() {
                @Override
                public void run() {
                    moveable = true;
                    dead = false;
                }
            }.runTaskLater(GunWar.getPlugin(), 100);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (moveable) {
                        this.cancel();
                        return;
                    }
                    Location location = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(),
                            getPlayer().getLocation().getYaw(), getPlayer().getLocation().getPitch());
                    getPlayer().teleport(location);
                }
            }.runTaskTimer(GunWar.getPlugin(), 0, 1);
        }
    }

    private Vector a(Vector onto, Vector u) {
        return u.clone().subtract(b(onto, u));
    }

    private Vector b(Vector onto, Vector u) {
        return onto.clone().multiply(onto.dot(u) / onto.lengthSquared());
    }

    private void c(Particle particle, double xb, double yb, double zb) {
        Player p = getPlayer();

        double twopi = 2 * Math.PI;
        double times = 1 * twopi;
        double division = twopi / 100;
        double radius = 2;

        Location c = p.getEyeLocation();
        Vector nv = c.getDirection().normalize();

        double nx = radius * nv.getX() + c.getX();
        double ny = radius * nv.getY() + c.getY();
        double nz = radius * nv.getZ() + c.getZ();

        Vector ya = a(nv, new Vector(0, 1, 0)).normalize();
        Vector xa = ya.getCrossProduct(nv).normalize();

        for (double theta = 0; theta < times; theta += division) {
            double xi = xa.getX() * xb + ya.getX() * yb + nv.getX() * zb;
            double yi = xa.getY() * xb + ya.getY() * yb + nv.getY() * zb;
            double zi = xa.getZ() * xb + ya.getZ() * yb + nv.getZ() * zb;

            double px = xi + nx;
            double py = yi + ny;
            double pz = zi + nz;

            p.spawnParticle(particle, new Location(c.getWorld(), px, py, pz), 1, 0, 0, 0, 0);
        }
    }

    @Override
    @Deprecated
    public void drawParticleLine(Particle particle, double start, double distance, double separate) {
        drawParticleLine(particle, 0, 0, start, distance, 0, 0, separate);
    }

    @Override
    @Deprecated
    public void drawParticleLine(Particle particle, double startX, double startY, double startZ,
                                 double distance, double separateX, double separateY, double separateZ) {
        double d = Math.sqrt(Math.pow(separateX, 2) + Math.pow(separateY, 2) + Math.pow(separateZ, 2)) * distance;
        if(startZ < d) {
            double times = d / separateZ;
            double x = startX;
            double y = startY;
            for (double z = startZ; z < times; z += separateZ) {
                c(particle, x, y, z);
                x += separateX;
                y += separateY;
            }
        }
    }

    @Override
    @Deprecated
    public HitEntity drawParticleLine(Particle particle, double startX, double startY, double startZ,
                                      double distance, double separateX, double separateY, double separateZ, GwGunItem gun) {
        double d = Math.sqrt(Math.pow(separateX, 2) + Math.pow(separateY, 2) + Math.pow(separateZ, 2)) * distance;
        if(startZ < d) {
            double times = d / separateZ;
            double x = startX;
            double y = startY;
            double z = startZ;
            double damageMin = gun.getAttackDamage() / 1.3;
            double hsdamageMin = gun.getHeadShotDamage() / 1.3;
            double currentDamage = gun.getAttackDamage();
            double currentHSDamage = gun.getHeadShotDamage();
            double separateDamage = currentDamage - damageMin / times;
            double separateHSDamage = currentHSDamage - hsdamageMin / times;
            while (z < times) {
                c(particle, x, y, z);
                for(Entity entity : getPlayer().getNearbyEntities(gun.getRange() + 1, gun.getRange() + 1, gun.getRange() + 1)) {
                    if(entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        if(livingEntity != getPlayer()) {
                            double xmin = entity.getLocation().getX() - (entity.getWidth() / 2);
                            double xmax = entity.getLocation().getX() + (entity.getWidth() / 2);
                            double ymin = entity.getLocation().getY();
                            double ymax = entity.getLocation().getY() + entity.getHeight();
                            double zmin = entity.getLocation().getZ() - (entity.getWidth() / 2);
                            double zmax = entity.getLocation().getZ() + (entity.getWidth() / 2);
                            boolean condition1 = xmin <= x && xmax >= x;
                            boolean condition2 = ymin <= y && ymax >= y;
                            boolean condition3 = zmin <= z && zmax >= z;
                            if (condition1 && condition2 && condition3) {
                                double hxmin = livingEntity.getEyeLocation().getX() - (livingEntity.getEyeHeight() / 2);
                                double hxmax = livingEntity.getEyeLocation().getX() + (livingEntity.getEyeHeight() / 2);
                                double hymin = livingEntity.getEyeLocation().getY() - (livingEntity.getEyeHeight() / 2);
                                double hymax = livingEntity.getEyeLocation().getY() + (livingEntity.getEyeHeight() / 2);
                                double hzmin = livingEntity.getEyeLocation().getZ() - (livingEntity.getEyeHeight() / 2);
                                double hzmax = livingEntity.getEyeLocation().getZ() + (livingEntity.getEyeHeight() / 2);
                                boolean hcondition1 = hxmin <= x && hxmax >= x;
                                boolean hcondition2 = hymin <= y && hymax >= y;
                                boolean hcondition3 = hzmin <= z && hzmax >= z;
                                boolean headShot = hcondition1 && hcondition2 && hcondition3;
                                if(GunWar.getGame().getGameMode() == GwGameModes.NORMAL && ((GameModeNormal) GwGameModes.NORMAL).getMode() == GameModeNormal.Mode.TEAM) {
                                    if (livingEntity instanceof Player) {
                                        PlayerData data = GunWar.getGame().getPlayerData((Player) livingEntity);
                                        if (data != null && data.getTeam() != getTeam()) {
                                            return new HitEntity(livingEntity, headShot, headShot ? currentHSDamage : currentDamage,
                                                    getPlayer().getEyeLocation(), new Location(livingEntity.getWorld(), x, y, z));
                                        }
                                    }
                                }
                                return new HitEntity(livingEntity, headShot, headShot ? currentHSDamage : currentDamage,
                                        getPlayer().getEyeLocation(), new Location(livingEntity.getWorld(), x, y, z));
                            }
                        }
                    }
                }
                Location loc = new Location(getPlayer().getWorld(), x, y, z);
                Block block = loc.getBlock();
                if(block != null && block.getType() != Material.AIR && block.getType() != Material.STRUCTURE_VOID) {
                    PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(2001,
                            new BlockPosition(block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ()),
                            block.getType().getId(), false);
                    List<Player> players = getPlayer().getWorld().getPlayers();
                    for(final Player p : players) {
                        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                    }
                    return null;
                }
                x += separateX;
                y += separateY;
                z += separateZ;
                currentDamage -= separateDamage;
                currentHSDamage -= separateHSDamage;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public HitEntity drawParticleLine(Particle particle, double startX, double startY, double startZ,
                                      double distance, Angle angle, double separate, GwGunItem gun, boolean aim) {
        double start = Math.sqrt(Math.pow(startX, 2) + Math.pow(startY, 2) + Math.pow(startZ, 2));
        if(start < distance) {
            double times = distance / separate;
            double ad = aim ? gun.getAttackDamage() + gun.getDamageAimed() : gun.getAttackDamage();
            double damageMin = ad / 1.075;
            double hsdamageMin = (ad + gun.getHeadShotDamage()) / 1.075;
            double currentDamage = ad;
            double currentHSDamage = ad + gun.getHeadShotDamage();
            double separateDamage = currentDamage - damageMin / times;
            double separateHSDamage = currentHSDamage - hsdamageMin / times;
            double t = 2 * Math.PI;
            double division = Math.PI / 50;
            double radius = 2;

            Location c = player.getEyeLocation();
            Vector nv = new Vector();
            double rx = c.getYaw() + angle.getYaw();
            double ry = c.getPitch() + angle.getPitch();
            double rrx = Math.toRadians(rx);
            double rry = Math.toRadians(ry);

            nv.setX(-Math.cos(rry) * Math.sin(rrx));
            nv.setY(-Math.sin(rry));
            nv.setZ(Math.cos(rry) * Math.cos(rrx));
            nv.normalize();

            double cx = c.getX() + startX;
            double cy = c.getY() + startY;
            double cz = c.getZ() + startZ;
            double nx = radius * nv.getX() + cx;
            double ny = radius * nv.getY() + cy;
            double nz = radius * nv.getZ() + cz;
            double px = nx;
            double py = ny;
            double pz = nz;

            for(double o = start; o <= distance; o += separate) {
                for (double theta = 0; theta < t; theta += division) {
                    double xi = nv.getX() * o;
                    double yi = nv.getY() * o;
                    double zi = nv.getZ() * o;

                    px = xi + nx;
                    py = yi + ny;
                    pz = zi + nz;

                    Random rand = new Random();
                    double dx_ = rand.nextDouble() * 0.2 - 0.1;
                    double dy_ = rand.nextDouble() * 0.2 - 0.1;
                    double dz_ = rand.nextDouble() * 0.2 - 0.1;

                    int count = 1;
                    if (particle == Particle.SUSPENDED_DEPTH) count = 2;
                    player.spawnParticle(particle, new Location(c.getWorld(), px + dx_, py + dy_, pz + dz_), count, 0, 0, 0, 0);

                    for (Entity entity : getPlayer().getNearbyEntities(distance + 1, distance + 1, distance + 1)) {
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            if (livingEntity != getPlayer()) {
                                double xmin = livingEntity.getLocation().getX() - (livingEntity.getWidth() / 2);
                                double xmax = livingEntity.getLocation().getX() + (livingEntity.getWidth() / 2);
                                double ymin = livingEntity.getLocation().getY();
                                double ymax = livingEntity.getLocation().getY() + livingEntity.getHeight();
                                double zmin = livingEntity.getLocation().getZ() - (livingEntity.getWidth() / 2);
                                double zmax = livingEntity.getLocation().getZ() + (livingEntity.getWidth() / 2);
                                boolean condition1 = xmin <= px && xmax >= px;
                                boolean condition2 = ymin <= py && ymax >= py;
                                boolean condition3 = zmin <= pz && zmax >= pz;
                                if (condition1 && condition2 && condition3) {
                                    double hxmin = livingEntity.getEyeLocation().getX() - (livingEntity.getEyeHeight() / 2);
                                    double hxmax = livingEntity.getEyeLocation().getX() + (livingEntity.getEyeHeight() / 2);
                                    double hymin = livingEntity.getEyeLocation().getY() - (livingEntity.getEyeHeight() / 2);
                                    double hymax = livingEntity.getEyeLocation().getY() + (livingEntity.getEyeHeight() / 2);
                                    double hzmin = livingEntity.getEyeLocation().getZ() - (livingEntity.getEyeHeight() / 2);
                                    double hzmax = livingEntity.getEyeLocation().getZ() + (livingEntity.getEyeHeight() / 2);
                                    boolean hcondition1 = hxmin <= px && hxmax >= px;
                                    boolean hcondition2 = hymin <= py && hymax >= py;
                                    boolean hcondition3 = hzmin <= pz && hzmax >= pz;
                                    boolean headShot = hcondition1 && hcondition2 && hcondition3;
                                    boolean passed = true;
                                    if (livingEntity instanceof Player) {
                                        if ((GunWar.getGame().getGameMode() == GwGameModes.NORMAL && ((GameModeNormal) GwGameModes.NORMAL).getMode() == GameModeNormal.Mode.TEAM) ||
                                                GunWar.getGame().getGameMode() == GwGameModes.GENERAL_SIEGE || GunWar.getGame().getGameMode() == GwGameModes.ZOMBIE_ESCAPE) {
                                            PlayerData data = GunWar.getGame().getPlayerData((Player) livingEntity);
                                            if (data != null) passed = data.getTeam() == getTeam();
                                        }
                                    }
                                    if (passed)
                                        return new HitEntity(livingEntity, headShot, headShot ? currentHSDamage : currentDamage,
                                                getPlayer().getEyeLocation(), new Location(livingEntity.getWorld(), px, py, pz));
                                }
                            }
                        }
                    }
                    Location loc = new Location(getPlayer().getWorld(), px, py, pz);

                    Block block = loc.getBlock();
                    if (block != null) {
                        if (BlockShape.isInBlock(block, px, py, pz)) {
                            PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(
                                    2001, CraftBlockData.newBlockPosition(block), block.getType().getId(), false);
                            a(packet);
                            return new HitEntity(null, false, 0,
                                    getPlayer().getEyeLocation(), new Location(getPlayer().getWorld(), px, py, pz));
                        } else {
                            if (block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER) {
                                currentDamage /= 1;
                                currentHSDamage /= 2;
                            }
                            if (block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA) {
                                currentDamage /= 3;
                                currentHSDamage /= 3;
                            }
                            if (block.getType() == Material.WEB) {
                                block.setType(Material.AIR);
                            }
                        }
                    }
                }
                currentDamage -= separateDamage;
                currentHSDamage -= separateHSDamage;
            }
            return new HitEntity(null, false, 0,
                    getPlayer().getEyeLocation(), new Location(getPlayer().getWorld(), px, py, pz));
        }
        return null;
    }

    @Nullable
    @Override
    public HitEntity drawParticleLine(Particle particle, double startX, double startY, double startZ,
                                      double distance, double separate, GwKnifeItem knife) {
        double start = Math.sqrt(Math.pow(startX, 2) + Math.pow(startY, 2) + Math.pow(startZ, 2));
        if(start < distance) {
            double times = distance / separate;
            double ad = knife.getAttackDamage();
            double damageMin = ad / 1.075;
            double currentDamage = ad;
            double separateDamage = currentDamage - damageMin / times;
            double t = 2 * Math.PI;
            double division = Math.PI / 50;
            double radius = 2;

            Location c = player.getEyeLocation();
            Vector nv = c.getDirection().normalize();

            double cx = c.getX() + startX;
            double cy = c.getY() + startY;
            double cz = c.getZ() + startZ;
            double nx = radius * nv.getX() + cx;
            double ny = radius * nv.getY() + cy;
            double nz = radius * nv.getZ() + cz;
            double px = nx;
            double py = ny;
            double pz = nz;

            for(double o = start; o <= distance; o += separate) {
                for (double theta = 0; theta < t; theta += division) {
                    double xi = nv.getX() * o;
                    double yi = nv.getY() * o;
                    double zi = nv.getZ() * o;

                    px = xi + nx;
                    py = yi + ny;
                    pz = zi + nz;

                    int count = 1;
                    if (particle == Particle.SUSPENDED_DEPTH) count = 2;
                    player.spawnParticle(particle, new Location(c.getWorld(), px, py, pz), count, 0, 0, 0, 0);

                    for (Entity entity : getPlayer().getNearbyEntities(distance + 1, distance + 1, distance + 1)) {
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            if (livingEntity != getPlayer()) {
                                double xmin = livingEntity.getLocation().getX() - (livingEntity.getWidth() / 2);
                                double xmax = livingEntity.getLocation().getX() + (livingEntity.getWidth() / 2);
                                double ymin = livingEntity.getLocation().getY();
                                double ymax = livingEntity.getLocation().getY() + livingEntity.getHeight();
                                double zmin = livingEntity.getLocation().getZ() - (livingEntity.getWidth() / 2);
                                double zmax = livingEntity.getLocation().getZ() + (livingEntity.getWidth() / 2);
                                boolean condition1 = xmin <= px && xmax >= px;
                                boolean condition2 = ymin <= py && ymax >= py;
                                boolean condition3 = zmin <= pz && zmax >= pz;
                                if (condition1 && condition2 && condition3) {
                                    boolean passed = true;
                                    if (livingEntity instanceof Player) {
                                        if ((GunWar.getGame().getGameMode() == GwGameModes.NORMAL && ((GameModeNormal) GwGameModes.NORMAL).getMode() == GameModeNormal.Mode.TEAM) ||
                                                GunWar.getGame().getGameMode() == GwGameModes.GENERAL_SIEGE || GunWar.getGame().getGameMode() == GwGameModes.ZOMBIE_ESCAPE) {
                                            PlayerData data = GunWar.getGame().getPlayerData((Player) livingEntity);
                                            if (data != null) passed = data.getTeam() == getTeam();
                                        }
                                    }
                                    if (passed)
                                        return new HitEntity(livingEntity, false, currentDamage,
                                                getPlayer().getEyeLocation(), new Location(livingEntity.getWorld(), px, py, pz));
                                }
                            }
                        }
                    }
                    Location loc = new Location(getPlayer().getWorld(), px, py, pz);

                    Block block = loc.getBlock();
                    if (block != null) {
                        if (BlockShape.isInBlock(block, px, py, pz)) {
                            PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(
                                    2001, CraftBlockData.newBlockPosition(block), block.getType().getId(), false);
                            a(packet);
                            return new HitEntity(null, false, 0,
                                    getPlayer().getEyeLocation(), new Location(getPlayer().getWorld(), px, py, pz));
                        }
                    }
                }
                currentDamage -= separateDamage;
            }
            return new HitEntity(null, false, 0,
                    getPlayer().getEyeLocation(), new Location(getPlayer().getWorld(), px, py, pz));
        }
        return null;
    }

    private void a(Packet<?> packet) {
        a(getPlayer().getWorld(), packet);
    }

    private void a(World world, Packet<?> packet) {
        List<Player> players = world.getPlayers();
        for (final Player p : players) {
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
    }

    @Override
    public void giveItem(GwItem item) {
        ItemStack i = item.getItem().clone();
        if(item instanceof GwGunItem) {
            ((GunWarGame) GunWar.getGame()).addItemData(new CraftGunData((GwGunItem) item, i, getPlayer()));
        } else if(item instanceof GwKnifeItem) {
            ((GunWarGame) GunWar.getGame()).addItemData(new CraftKnifeData((GwKnifeItem) item, i, getPlayer()));
        } else {
            ((GunWarGame) GunWar.getGame()).addItemData(new CraftItemData(item, i, getPlayer()));
        }
        if (GunWar.getGame().getItemData(i) != null) i = GunWar.getGame().getItemData(i).getItem();
        getPlayer().getInventory().addItem(i);
        getPlayer().updateInventory();
    }

    @Override
    public void sendMessage(String message) {
        getPlayer().sendMessage(message);
    }

    @Override
    public void sendMessage(BaseComponent component) {
        getPlayer().sendMessage(component);
    }

    @Override
    public void sendMessage(BaseComponent... components) {
        getPlayer().sendMessage(components);
    }

    @Override
    public void sendMessage(String[] messages) {
        getPlayer().sendMessage(messages);
    }

    @Override
    public void sendStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        String str = sw.toString();
        getPlayer().sendMessage(str);
    }

    @Override
    public boolean isZoom() {
        return zoom;
    }

    @Override
    public void setZoom(boolean zoom, float zoomLevel) {
        this.zoom = zoom;
        this.zoomLevel = zoomLevel;
        if(zoom && zoomLevel > 0) {
            double d = 0.2 - (0.2 * zoomLevel / 100);
            player.setWalkSpeed((float) d);
        } else {
            player.setWalkSpeed(0.2F);
        }
    }

    @NotNull
    @Override
    public PlayerData.Detail detail() {
        return detail;
    }

}
