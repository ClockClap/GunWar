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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class HitEntity {

    private final LivingEntity entity;
    private final boolean headShot;
    private final double damage;
    private final Location from;
    private final Location hitLocation;

    public HitEntity(LivingEntity entity, boolean headShot, double damage, @NotNull Location from, @NotNull Location hitLocation) {
        this.entity = entity;
        this.headShot = headShot;
        this.damage = damage;
        this.from = from;
        this.hitLocation = hitLocation;
    }

    @Nullable
    public UUID getUniqueId() {
        if(entity != null) return entity.getUniqueId();
        return null;
    }

    @Nullable
    public LivingEntity getEntity() {
        return entity;
    }

    public boolean isHeadShot() {
        return headShot;
    }

    public double getDamage() {
        return damage;
    }

    @NotNull
    public Location getFrom() {
        return from;
    }

    @NotNull
    public Location getHitLocation() {
        return hitLocation;
    }
}
