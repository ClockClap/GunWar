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

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.UUID;

public class GunWarEntityData implements EntityData {

    private final UUID uniqueId;
    private final Entity entity;
    private final EntityType type;
    private final Detail detail;

    public GunWarEntityData(Entity entity) {
        this.uniqueId = entity.getUniqueId();
        this.entity = entity;
        this.type = entity.getType();
        this.detail = new Detail() {
            public boolean canSee(LivingEntity from) {
                if (from.getWorld() != entity.getWorld()) {
                    return false;
                }
                World world = from.getWorld();
                Location fromLoc = from.getEyeLocation();
                Location to = entity.getLocation();
                if(entity instanceof LivingEntity) {
                    to = ((LivingEntity) entity).getEyeLocation();
                }
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
        };
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public EntityType getType() {
        return type;
    }

    @Override
    public Detail detail() {
        return detail;
    }
}
