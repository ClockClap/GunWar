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

package com.github.clockclap.gunwar.item;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface GwGunItem extends GwWeaponItem {

    /**
     * Gets amount of ammo the gun has.
     *
     * @return amoung of ammo with int
     */
    public int getAmmo();

    /**
     * Gets range can be shot.
     *
     * @return block
     */
    public double getRange();

    /**
     * Gets recoil size.
     *
     * @return block
     */
    public float getRecoil();

    /**
     * Gets recoil size when the gun owner is currently sneaking.
     *
     * @return block
     */
    public float getRecoilOnSneak();

    /**
     * Gets reloading time with tick.
     *
     * @return tick
     */
    public long getReload();

    /**
     * Gets rate of fire with tick.
     *
     * @return tick
     */
    public long getFire();

    /**
     * Gets zoom level with percent.
     *
     * @return percent as float
     */
    public float getZoomLevel();

    /**
     * Gets health value of damage when the gun gave head shot damage.
     *
     * @return health value of damage
     */
    public float getHeadShotDamage();

    /**
     * Gets health value of damage when the gun holder sneaking.
     *
     * @return health value of damage
     */
    public float getDamageAimed();

    /**
     * Gets accuracy of the gun ammo. <br>
     * 500F is max value.
     *
     * @return accuracy of the gun ammo as float less than 500F
     */
    public float getAccuracy();

    /**
     * Gets accuracy of the gun ammo when the gun owner is currently sneaking. <br>
     * 500F is max value.
     *
     * @return accuracy of the gun ammo as float less than 500F
     */
    public float getAccuracyOnSneak();

    /**
     * Gets value of knockback.
     *
     * @return value
     */
    public float getKnockBack();

    /**
     * Gets kind of the gun.
     *
     * @return gun type
     */
    public GwGunType getGunType();

    /**
     * Gets type of the gun reloading.
     *
     * @return gun reloading type
     */
    public GunReloadingType getReloadingType();

    public default void onShoot(Player player) { }

    public default void onHit(Player player, Entity entity, float damage) { }

    public default void onHitHeadShot(Player player, Entity entity, float damage) { }

}
