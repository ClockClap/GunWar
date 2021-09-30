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

package com.github.clockclap.gunwar.item;

import com.github.clockclap.gunwar.GwAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@GwAPI
public interface GwGunItem extends GwWeaponItem {

    /**
     * Gets amount of ammo the gun has.
     *
     * @return amoung of ammo with int
     */
    int getAmmo();

    /**
     * Gets range can be shot.
     *
     * @return block
     */
    double getRange();

    /**
     * Gets recoil size.
     *
     * @return block
     */
    float getRecoil();

    /**
     * Gets recoil size when the gun owner is currently sneaking.
     *
     * @return block
     */
    float getRecoilOnSneak();

    /**
     * Gets reloading time with tick.
     *
     * @return tick
     */
    long getReload();

    /**
     * Gets rate of fire with tick.
     *
     * @return tick
     */
    long getFire();

    /**
     * Gets zoom level with percent.
     *
     * @return percent as float
     */
    float getZoomLevel();

    /**
     * Gets health value of damage when the gun gave head shot damage.
     *
     * @return health value of damage
     */
    float getHeadShotDamage();

    /**
     * Gets health value of damage when the gun holder sneaking.
     *
     * @return health value of damage
     */
    float getDamageAimed();

    /**
     * Gets accuracy of the gun ammo. <br>
     * 500F is max value.
     *
     * @return accuracy of the gun ammo as float less than 500F
     */
    float getAccuracy();

    /**
     * Gets accuracy of the gun ammo when the gun owner is currently sneaking. <br>
     * 500F is max value.
     *
     * @return accuracy of the gun ammo as float less than 500F
     */
    float getAccuracyOnSneak();

    /**
     * Gets value of knockback.
     *
     * @return value
     */
    float getKnockBack();

    /**
     * Gets kind of the gun.
     *
     * @return gun type
     */
    GwGunType getGunType();

    /**
     * Gets type of the gun reloading.
     *
     * @return gun reloading type
     */
    GunReloadingType getReloadingType();

    default void onShoot(Player player) { }

    default void onHit(Player player, Entity entity, float damage) { }

    default void onHitHeadShot(Player player, Entity entity, float damage) { }

}
