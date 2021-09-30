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

import com.github.clockclap.gunwar.GwPlugin;
import com.github.clockclap.gunwar.util.BlockShape;
import com.github.clockclap.gunwar.util.GunWarValidate;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.UUID;

@GwPlugin
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
                return canSee(from, 100D);
            }

            public boolean canSee(LivingEntity from, double distance) {
                Validate.notNull(from);
                GunWarValidate.distance(distance);
                if(from.getWorld().getUID().equals(entity.getWorld().getUID())) {

                    Location f = from.getEyeLocation();
                    Location t = entity.getLocation();
                    World w = entity.getWorld();
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
