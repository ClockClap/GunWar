/*
 * Copyright (c) 2021, ClockClap. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.clockclap.gunwar.game.data;

import com.github.clockclap.gunwar.GwPlugin;
import com.mojang.authlib.GameProfile;
import com.github.clockclap.gunwar.GunWar;
import com.github.clockclap.gunwar.entity.HitEntity;
import com.github.clockclap.gunwar.game.GunWarGame;
import com.github.clockclap.gunwar.game.gamemode.GameModeNormal;
import com.github.clockclap.gunwar.game.gamemode.GwGameModes;
import com.github.clockclap.gunwar.item.GwGunItem;
import com.github.clockclap.gunwar.item.GwItem;
import com.github.clockclap.gunwar.item.GwKnifeItem;
import com.github.clockclap.gunwar.util.Angle;
import com.github.clockclap.gunwar.util.BlockShape;
import com.github.clockclap.gunwar.util.PlayerWatcher;
import com.github.clockclap.gunwar.util.TextUtilities;
import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldEvent;
import org.bukkit.*;
import org.bukkit.block.Block;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.*;

@GwPlugin
@SuppressWarnings({"all"})
public class GunWarPlayerData extends GunWarEntityData implements PlayerData {

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
    private float zoomLevel;
    private final PlayerData.Detail detail;

    public GunWarPlayerData(Player player) {
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
        this.zoom = false;
        this.zoomLevel = 0F;
        this.detail = new PlayerData.Detail() {
            private final String oldName = player.getName();
            private Map<Player, String> nameMap = new HashMap<>();

            public String getOldName() {
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
                setName(from, getOldName());
            }

            public void resetName() {
                setName(getOldName());
            }

            public boolean canSee(LivingEntity from) {
                if (from.getWorld() != player.getWorld()) {
                    return false;
                }
                World world = from.getWorld();
                Location fromLoc = from.getEyeLocation();
                Location to = player.getEyeLocation();
                int x = to.getBlockX();
                int y = to.getBlockY();
                int z = to.getBlockZ();
                int x_ = fromLoc.getBlockX();
                int y_ = fromLoc.getBlockY();
                int z_ = fromLoc.getBlockZ();
                for (int traceDistance = 100; traceDistance >= 0; traceDistance--) {
                    byte b0;
                    if (x_ == x && y_ == y && z_ == z) {
                        return true;
                    }
                    double x0 = 999.0D;
                    double y0 = 999.0D;
                    double z0 = 999.0D;
                    double x1 = 999.0D;
                    double y1 = 999.0D;
                    double z1 = 999.0D;
                    double dx = to.getX() - fromLoc.getX();
                    double dy = to.getY() - fromLoc.getY();
                    double dz = to.getZ() - fromLoc.getZ();
                    if (x > x_) {
                        x0 = x_ + 1.0D;
                        x1 = (x0 - fromLoc.getX()) / dx;
                    } else if (x < x_) {
                        x0 = x_ + 0.0D;
                        x1 = (x0 - fromLoc.getX()) / dx;
                    }
                    if (y > y_) {
                        y0 = y_ + 1.0D;
                        y1 = (y0 - fromLoc.getY()) / dy;
                    } else if (y < y_) {
                        y0 = y_ + 0.0D;
                        y1 = (y0 - fromLoc.getY()) / dy;
                    }
                    if (z > z_) {
                        z0 = z_ + 1.0D;
                        z1 = (z0 - fromLoc.getZ()) / dz;
                    } else if (z < z_) {
                        z0 = z_ + 0.0D;
                        z1 = (z0 - fromLoc.getZ()) / dz;
                    }
                    if (x1 < y1 && x1 < z1) {
                        if (x > x_) {
                            b0 = 4;
                        } else {
                            b0 = 5;
                        }
                        fromLoc.setX(x0);
                        fromLoc.add(0.0D, dy * x1, dz * x1);
                    } else if (y1 < z1) {
                        if (y > y_) {
                            b0 = 0;
                        } else {
                            b0 = 1;
                        }
                        fromLoc.add(dx * y1, 0.0D, dz * y1);
                        fromLoc.setY(y0);
                    } else {
                        if (z > z_) {
                            b0 = 2;
                        } else {
                            b0 = 3;
                        }
                        fromLoc.add(dx * z1, dy * z1, 0.0D);
                        fromLoc.setZ(z0);
                    }
                    x_ = fromLoc.getBlockX();
                    y_ = fromLoc.getBlockY();
                    z_ = fromLoc.getBlockZ();
                    if (b0 == 5) {
                        x_--;
                    }
                    if (b0 == 1) {
                        y_--;
                    }
                    if (b0 == 3) {
                        z_--;
                    }
                    if (world.getBlockAt(x_, y_, z_).getType().isOccluding()) {
                        return false;
                    }
                }
                return true;
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
            getPlayer().sendTitle(TextUtilities.TITLE_MAIN_INFECT, TextUtilities.TITLE_SUB_INFECT, 0, 100, 20);
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
            getPlayer().sendTitle(TextUtilities.TITLE_MAIN_DIED_ZOMBIE, TextUtilities.TITLE_SUB_INFECT, 0, 100, 20);
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
                    @SuppressWarnings("deprecation")
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
        double yaw = Math.toRadians(angle.getYaw());
        double pitch = Math.toRadians(angle.getPitch());
        double r = separate * Math.cos(pitch);
        double separateX = r * Math.sin(yaw);
        double separateY = separate * Math.sin(pitch);
        double separateZ = r * Math.cos(yaw);
        double d = Math.sqrt(Math.pow(separateX, 2) + Math.pow(separateY, 2) + Math.pow(separateZ, 2)) * distance;
        if(startZ < d) {
            double times = d / separate;
            double x = startX;
            double y = startY;
            double z = startZ;
            double ad = aim ? gun.getAttackDamage() + gun.getDamageAimed() : gun.getAttackDamage();
            double damageMin = ad / 1.1;
            double hsdamageMin = (ad + gun.getHeadShotDamage()) / 1.1;
            double currentDamage = ad;
            double currentHSDamage = ad + gun.getHeadShotDamage();
            double separateDamage = currentDamage - damageMin / times;
            double separateHSDamage = currentHSDamage - hsdamageMin / times;
            double px = 0;
            double py = 0;
            double pz = 0;
            while (z < times) {
                double twopi = 2 * Math.PI;
                double t = 1 * twopi;
                double division = twopi / 100;
                double radius = 2;

                Location c = player.getEyeLocation();
                Vector nv = c.getDirection().normalize();

                double nx = radius * nv.getX() + c.getX();
                double ny = radius * nv.getY() + c.getY();
                double nz = radius * nv.getZ() + c.getZ();

                Vector ya = a(nv, new Vector(0, 1, 0)).normalize();
                Vector xa = ya.getCrossProduct(nv).normalize();

                for (double theta = 0; theta < t; theta += division) {
                    double xi = xa.getX() * x + ya.getX() * y + nv.getX() * z;
                    double yi = xa.getY() * x + ya.getY() * y + nv.getY() * z;
                    double zi = xa.getZ() * x + ya.getZ() * y + nv.getZ() * z;

                    px = xi + nx;
                    py = yi + ny;
                    pz = zi + nz;

                    Random rand = new Random();
                    double dx_ = rand.nextDouble() * 0.2 - 0.1;
                    double dy_ = rand.nextDouble() * 0.2 - 0.1;
                    double dz_ = rand.nextDouble() * 0.2 - 0.1;

                    int count = 1;
                    if(particle == Particle.SUSPENDED_DEPTH) count = 8;

                    player.spawnParticle(particle, new Location(c.getWorld(), px + dx_, py + dy_, pz + dz_), count, 0, 0, 0, 0);

                    for(Entity entity : getPlayer().getNearbyEntities(distance + 1, distance + 1, distance + 1)) {
                        if(entity instanceof LivingEntity) {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            if(livingEntity != getPlayer()) {
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
                                    if(livingEntity instanceof Player) {
                                        if ((GunWar.getGame().getGameMode() == GwGameModes.NORMAL && ((GameModeNormal) GwGameModes.NORMAL).getMode() == GameModeNormal.Mode.TEAM) ||
                                                GunWar.getGame().getGameMode() == GwGameModes.GENERAL_SIEGE || GunWar.getGame().getGameMode() == GwGameModes.ZOMBIE_ESCAPE) {
                                            PlayerData data = GunWar.getGame().getPlayerData((Player) livingEntity);
                                            if(data != null) passed = data.getTeam() == getTeam();
                                        }
                                    }
                                    if(passed) return new HitEntity(livingEntity, headShot, headShot ? currentHSDamage : currentDamage,
                                            getPlayer().getEyeLocation(), new Location(livingEntity.getWorld(), px, py, pz));
                                }
                            }
                        }
                    }
                    Location loc = new Location(getPlayer().getWorld(), px, py, pz);

                    Block block = loc.getBlock();
                    if(block != null) {
                        if (a(block, px, py, pz)) {
                            PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(
                                    2001, GunWarBlockData.newBlockPosition(block), block.getType().getId(), false);
                            List<Player> players = getPlayer().getWorld().getPlayers();
                            for(final Player p : players) {
                                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                            }
                            return new HitEntity(null, false, 0,
                                    getPlayer().getEyeLocation(), new Location(getPlayer().getWorld(), px, py, pz));
                        } else {
                            if(block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER) {
                                currentDamage /= 1;
                                currentHSDamage /= 2;
                            }
                            if(block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA) {
                                currentDamage /= 3;
                                currentHSDamage /= 3;
                            }
                            if(block.getType() == Material.WEB) {
                                block.setType(Material.AIR);
                            }
                        }
                    }
                }
                x += separateX;
                y += separateY;
                z += separateZ;
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
        double yaw = 0;
        double pitch = 0;
        double d = Math.sqrt(Math.pow(0, 2) + Math.pow(0, 2) + Math.pow(separate, 2)) * distance;
        if(startZ < d) {
            double times = d / separate;
            double x = startX;
            double y = startY;
            double z = startZ;
            double currentDamage = knife.getAttackDamage();
            double currentHSDamage = knife.getAttackDamage();
            double separateDamage = 0;
            double separateHSDamage = 0;
            double px = 0;
            double py = 0;
            double pz = 0;
            while (z < times) {
                double twopi = 2 * Math.PI;
                double t = 1 * twopi;
                double division = twopi / 100;
                double radius = 2;

                Location c = player.getEyeLocation();
                Vector nv = c.getDirection().normalize();

                double nx = radius * nv.getX() + c.getX();
                double ny = radius * nv.getY() + c.getY();
                double nz = radius * nv.getZ() + c.getZ();

                Vector ya = a(nv, new Vector(0, 1, 0)).normalize();
                Vector xa = ya.getCrossProduct(nv).normalize();

                for (double theta = 0; theta < t; theta += division) {
                    double xi = xa.getX() * x + ya.getX() * y + nv.getX() * z;
                    double yi = xa.getY() * x + ya.getY() * y + nv.getY() * z;
                    double zi = xa.getZ() * x + ya.getZ() * y + nv.getZ() * z;

                    px = xi + nx;
                    py = yi + ny;
                    pz = zi + nz;

                    Random rand = new Random();
                    double dx_ = rand.nextDouble() * 0.2 - 0.1;
                    double dy_ = rand.nextDouble() * 0.2 - 0.1;
                    double dz_ = rand.nextDouble() * 0.2 - 0.1;

                    int count = 1;
                    if(particle == Particle.SUSPENDED_DEPTH) count = 8;

                    player.spawnParticle(particle, new Location(c.getWorld(), px + dx_, py + dy_, pz + dz_), count, 0, 0, 0, 0);

                    for(Entity entity : getPlayer().getNearbyEntities(distance + 1, distance + 1, distance + 1)) {
                        if(entity instanceof LivingEntity) {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            if(livingEntity != getPlayer()) {
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
                                    if(livingEntity instanceof Player) {
                                        if ((GunWar.getGame().getGameMode() == GwGameModes.NORMAL && ((GameModeNormal) GwGameModes.NORMAL).getMode() == GameModeNormal.Mode.TEAM) ||
                                                GunWar.getGame().getGameMode() == GwGameModes.GENERAL_SIEGE || GunWar.getGame().getGameMode() == GwGameModes.ZOMBIE_ESCAPE) {
                                            PlayerData data = GunWar.getGame().getPlayerData((Player) livingEntity);
                                            if(data != null) passed = data.getTeam() == getTeam();
                                        }
                                    }
                                    if(passed) return new HitEntity(livingEntity, headShot, headShot ? currentHSDamage : currentDamage,
                                            getPlayer().getEyeLocation(), new Location(livingEntity.getWorld(), px, py, pz));
                                }
                            }
                        }
                    }
                    Location loc = new Location(getPlayer().getWorld(), px, py, pz);

                    Block block = loc.getBlock();
                    if(block != null) {
                        if(a(block, px, py, pz)) {
                            PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(
                                    2001, GunWarBlockData.newBlockPosition(block), block.getType().getId(), false);
                            List<Player> players = getPlayer().getWorld().getPlayers();
                            for (final Player p : players) {
                                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                            }
                            return new HitEntity(null, false, 0,
                                    getPlayer().getEyeLocation(), new Location(getPlayer().getWorld(), px, py, pz));
                        }
                    }
                }
                z += separate;
                currentDamage -= separateDamage;
                currentHSDamage -= separateHSDamage;
            }
            return new HitEntity(null, false, 0,
                    getPlayer().getEyeLocation(), new Location(getPlayer().getWorld(), px, py, pz));
        }
        return null;
    }

    private boolean a(Block block, double x, double y, double z) {
        boolean result = true;
        if(block != null && block.getType() != null) {
            Material type = block.getType();
            int xa = block.getLocation().getBlockX();
            int ya = block.getLocation().getBlockY();
            int za = block.getLocation().getBlockZ();

            double xb = x - xa;
            double yb = y - ya;
            double zb = z - za;

            if(!BlockShape.isTransparent(type)) {
                if (BlockShape.isSlab(type)) {
                    if (block.getData() >= 8 && yb >= 0.5) {
                        result = false;
                    } else if (block.getData() < 8 && yb <= 0.5) {
                        result = false;
                    }
                } else if (BlockShape.isStairs(type)) {
                    if (block.getData() == 0 || block.getData() == 8) {
                        if(yb <= 0.5) {
                            result = false;
                        } else if(xb >= 0.5) {
                            result = false;
                        }
                    } else if (block.getData() == 1 || block.getData() == 9) {
                        if(yb <= 0.5) {
                            result = false;
                        } else if(xb <= 0.5) {
                            result = false;
                        }
                    } else if (block.getData() == 2 || block.getData() == 10) {
                        if(yb <= 0.5) {
                            result = false;
                        } else if(zb >= 0.5) {
                            result = false;
                        }
                    } else if (block.getData() == 3 || block.getData() == 11) {
                        if(yb <= 0.5) {
                            result = false;
                        } else if(zb <= 0.5) {
                            result = false;
                        }
                    } else if (block.getData() == 4 || block.getData() == 12) {
                        if(yb >= 0.5) {
                            result = false;
                        } else if(xb >= 0.5) {
                            result = false;
                        }
                    } else if ((block.getData() == 5 || block.getData() == 13)) {
                        if(yb >= 0.5) {
                            result = false;
                        } else if(xb <= 0.5) {
                            result = false;
                        }
                    } else if ((block.getData() == 6 || block.getData() == 14)) {
                        if(yb >= 0.5) {
                            result = false;
                        } else if(zb >= 0.5) {
                            result = false;
                        }
                    } else if ((block.getData() == 7 || block.getData() == 15)) {
                        if(yb >= 0.5) {
                            result = false;
                        } else if(zb <= 0.5) {
                            result = false;
                        }
                    }
                } else if(BlockShape.isDoor(type)) {
                    World w = block.getLocation().getWorld();
                    Block bottom = w.getBlockAt(xa, ya - 1, za);
                    Block b = block;
                    if(bottom != null && bottom.getType() != null && BlockShape.isDoor(bottom.getType())) b = bottom;
                    if((b.getData() == 0 || b.getData() == 7) && xb <= 0.1875) {
                        result = false;
                    } else if((b.getData() == 1 || b.getData() == 4) && zb <= 0.1875) {
                        result = false;
                    } else if((b.getData() == 2 || b.getData() == 5) && xb >= 0.8125) {
                        result = false;
                    } else if((b.getData() == 3 || b.getData() == 6) && zb >= 0.8125) {
                        result = false;
                    }
                } else if(BlockShape.isFence(type)) {
                    World w = block.getLocation().getWorld();
                    Block x0 = w.getBlockAt(xa + 1, ya, za);
                    Block x1 = w.getBlockAt(xa - 1, ya, za);
                    Block z0 = w.getBlockAt(xa, ya, za + 1);
                    Block z1 = w.getBlockAt(xa, ya, za - 1);
                    boolean b = true;
                    if(x0 != null && x0.getType() == block.getType()) {
                        if(xb >= 0.375 && zb >= 0.375 && zb <= 0.625) {
                            b = false;
                        }
                    }
                    if(x1 != null && x1.getType() == block.getType()) {
                        if(xb <= 0.625 && zb >= 0.375 && zb <= 0.625) {
                            b = false;
                        }
                    }
                    if(z0 != null && z0.getType() == block.getType()) {
                        if(zb >= 0.375 && xb >= 0.375 && xb <= 0.625) {
                            b = false;
                        }
                    }
                    if(z1 != null && z1.getType() == block.getType()) {
                        if(zb <= 0.625 && xb >= 0.375 && xb <= 0.625) {
                            b = false;
                        }
                    }
                    result = b;
                } else if(type == Material.COBBLE_WALL) {
                    World w = block.getLocation().getWorld();
                    Block x0 = w.getBlockAt(xa + 1, ya, za);
                    Block x1 = w.getBlockAt(xa - 1, ya, za);
                    Block z0 = w.getBlockAt(xa, ya, za + 1);
                    Block z1 = w.getBlockAt(xa, ya, za - 1);
                    boolean b = true;
                    if(x0 != null && x0.getType() == block.getType()) {
                        if(xb >= 0.3125 && zb >= 0.3125 && zb <= 0.6875) {
                            b = false;
                        }
                    }
                    if(x1 != null && x1.getType() == block.getType()) {
                        if(xb <= 0.6875 && zb >= 0.3125 && zb <= 0.6875) {
                            b = false;
                        }
                    }
                    if(z0 != null && z0.getType() == block.getType()) {
                        if(zb >= 0.3125 && xb >= 0.3125 && xb <= 0.6875) {
                            b = false;
                        }
                    }
                    if(z1 != null && z1.getType() == block.getType()) {
                        if(zb <= 0.6875 && xb >= 0.3125 && xb <= 0.6875) {
                            b = false;
                        }
                    }
                    result = b;
                } else if(BlockShape.isFenceGate(type)) {
                    if((block.getData() == 0 || block.getData() == 2 || block.getData() == 8 || block.getData() == 10) && zb >= 0.375 && zb <= 0.625) {
                        result = false;
                    } else if((block.getData() == 1 || block.getData() == 3 || block.getData() == 9 || block.getData() == 11) && xb >= 0.375 && xb <= 0.625) {
                        result = false;
                    }
                } else if(BlockShape.isTrapdoor(type)) {
                    if((block.getData() >= 0 && block.getData() <= 3) && yb <= 0.1875) {
                        result = false;
                    } else if((block.getData() == 4 || block.getData() == 12) && zb >= 0.8125) {
                        result = false;
                    } else if((block.getData() == 5 || block.getData() == 13) && zb <= 0.1875) {
                        result = false;
                    } else if((block.getData() == 6 || block.getData() == 14) && xb >= 0.8125) {
                        result = false;
                    } else if((block.getData() == 7 || block.getData() == 15) && xb <= 0.1825) {
                        result = false;
                    } else if((block.getData() >= 8 && block.getData() <= 11) && yb >= 0.8125) {
                        result = false;
                    }
                } else if(type == Material.ENCHANTMENT_TABLE || type == Material.ENDER_PORTAL_FRAME) {
                    if(yb <= 0.8125) result = false;
                }
            } else if(!BlockShape.isTransparent(type)) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public void giveItem(GwItem item) {
        ItemStack i = item.getItem().clone();
        if(item instanceof GwGunItem) {
            ((GunWarGame) GunWar.getGame()).addItemData(new GunWarGunData((GwGunItem) item, i, getPlayer()));
        } else if(item instanceof GwKnifeItem) {
            ((GunWarGame) GunWar.getGame()).addItemData(new GunWarKnifeData((GwKnifeItem) item, i, getPlayer()));
        } else {
            ((GunWarGame) GunWar.getGame()).addItemData(new GunWarItemData(item, i, getPlayer()));
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
