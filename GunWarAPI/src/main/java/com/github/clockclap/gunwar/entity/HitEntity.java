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

package com.github.clockclap.gunwar.entity;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.UUID;

public class HitEntity {

    private LivingEntity entity;
    private boolean headShot;
    private double damage;
    private Location from;
    private Location hitLocation;

    public HitEntity(LivingEntity entity, boolean headShot, double damage, Location from, Location hitLocation) {
        this.entity = entity;
        this.headShot = headShot;
        this.damage = damage;
        this.from = from;
        this.hitLocation = hitLocation;
    }

    public UUID getUniqueId() {
        return entity.getUniqueId();
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public boolean isHeadShot() {
        return headShot;
    }

    public double getDamage() {
        return damage;
    }

    public Location getFrom() {
        return from;
    }

    public Location getHitLocation() {
        return hitLocation;
    }
}
