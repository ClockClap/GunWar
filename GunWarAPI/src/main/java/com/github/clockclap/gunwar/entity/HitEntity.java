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
