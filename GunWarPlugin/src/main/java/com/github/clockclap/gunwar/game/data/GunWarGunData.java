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
import com.github.clockclap.gunwar.item.GunReloadingType;
import com.github.clockclap.gunwar.item.GwGunItem;
import com.github.clockclap.gunwar.util.Angle;
import com.github.clockclap.gunwar.util.TextReference;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

@GwPlugin
public class GunWarGunData extends GunWarItemData implements GunData {

    private boolean reloading;
    private boolean canFire;
    private int ammo;
    private BukkitRunnable fire;
    private BukkitRunnable reload;

    public GunWarGunData(GwGunItem gwitem, ItemStack item, Player owner) {
        super(gwitem, item, owner);
        reloading = false;
        canFire = true;
        ammo = gwitem.getAmmo();
        updateName();
    }

    @Override
    public boolean isReloading() {
        return reloading;
    }

    @Override
    public boolean canFire() {
        return canFire;
    }

    @Override
    public int getAmmo() {
        return ammo;
    }

    @Override
    public void setAmmo(int ammo) {
        this.ammo = Math.max(0, ammo);
        if(this.ammo <= 0) canFire = false;
        if(this.ammo > 0) canFire = true;
    }

    @Override
    public void fire(boolean aim) {
        if(canFire && getAmmo() > 0) {
            canFire = false;

            Random random = new Random();
            float accuracy = ((GwGunItem) getGwItem()).getAccuracy();
            if (getOwner().isSneaking()) {
                accuracy = ((GwGunItem) getGwItem()).getAccuracyOnSneak();
            }

            double yaw = random.nextDouble() * (32 / accuracy) - (16 / accuracy);
            double pitch = random.nextDouble() * (32 / accuracy) - (16 / accuracy);

            HitEntity hitEntity = GunWar.getGame().getPlayerData(getOwner()).drawParticleLine(
                    Particle.SUSPENDED_DEPTH, yaw, pitch, 0.1, ((GwGunItem) getGwItem()).getRange(),
                    new Angle(yaw, pitch), 0.25, (GwGunItem) getGwItem(), aim);
            if(hitEntity != null) {
                double subX = hitEntity.getHitLocation().getX() - hitEntity.getFrom().getX();
                double subY = hitEntity.getHitLocation().getY() - hitEntity.getFrom().getY();
                double subZ = hitEntity.getHitLocation().getZ() - hitEntity.getFrom().getZ();
                double far = Math.sqrt(Math.pow(subX, 2) +
                        Math.pow(subY, 2) +
                        Math.pow(subZ, 2));
                if(hitEntity.getEntity() != null) {
                    if (hitEntity.getEntity() instanceof Player) {
                        PlayerData data = GunWar.getGame().getPlayerData((Player) hitEntity.getEntity());
                        hitEntity.getEntity().damage(0, getOwner());
                        data.setHealth(Math.max(0, data.getHealth() - hitEntity.getDamage()));
                    } else if (!(hitEntity.getEntity() instanceof ArmorStand)) {
                        double damage = hitEntity.getDamage();
                        hitEntity.getEntity().damage(damage, getOwner());
                    }
                    double d = ((GwGunItem) getGwItem()).getKnockBack() / far;
                    Vector vector = new Vector(subX * d, subY * d, subZ * d);
                    hitEntity.getEntity().setVelocity(vector);
                }
                double recoil = (getOwner().isSneaking() ? ((GwGunItem) getGwItem()).getRecoilOnSneak() : ((GwGunItem) getGwItem()).getRecoil());
                double d_ = recoil / far;
                Vector vector_ = new Vector(subX * d_ * -1, subY * d_ * -1, subZ * d_ * -1);
                getOwner().setVelocity(vector_);
            }

            ((GwGunItem) getGwItem()).onShoot(getOwner());

            setAmmo(getAmmo() - 1);
            updateName();
            if(getAmmo() <= 0) {
                reload();
                return;
            }
            fire = new BukkitRunnable() {
                @Override
                public void run() {
                    canFire = true;
                }
            };
            fire.runTaskLater(GunWar.getPlugin(), ((GwGunItem) getGwItem()).getFire());
        }
    }

    @Override
    public void cancelFireCooldown() {
        if(fire != null && !fire.isCancelled()) fire.cancel();
    }

    @Override
    public void reload() {
        if(!isReloading()) {
            reloading = true;
            canFire = false;
            updateName();
            if(((GwGunItem) getGwItem()).getReloadingType() == GunReloadingType.SINGLE) {
                reload = new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(getAmmo() >= ((GwGunItem) getGwItem()).getAmmo()) {
                            setAmmo(((GwGunItem) getGwItem()).getAmmo());
                            reloading = false;
                            canFire = true;
                            this.cancel();
                            return;
                        }
                        setAmmo(getAmmo() + 1);
                        updateName();
                    }
                };
                reload.runTaskTimer(GunWar.getPlugin(), 0, ((GwGunItem) getGwItem()).getReload());
            } else {
                reload = new BukkitRunnable() {
                    @Override
                    public void run() {
                        reloading = false;
                        setAmmo(((GwGunItem) getGwItem()).getAmmo());
                        updateName();
                        canFire = true;
                    }
                };
                reload.runTaskLater(GunWar.getPlugin(), ((GwGunItem) getGwItem()).getReload());
            }
        }
    }

    public void updateName() {
        ItemMeta meta = getItem().getItemMeta();
        String s = GunWar.getConfig().getDetailConfig().getString("item.gun_name_format.general", "%{color}7%i ▪ «%a»");
        if(reloading) s = GunWar.getConfig().getDetailConfig().getString("item.gun_name_format.reloading", "%{color}7%i ▫ %{color}8«%a»");
            s = TextReference.translateAlternateColorCodes("%{color}", s);
            s = s.replaceAll("%i", getGwItem().getDisplayName()).replaceAll("%a", ammo + "");
            meta.setDisplayName(s);
        getItem().setItemMeta(meta);
        if(getOwner().getInventory().getItemInMainHand() != null) {
            ItemStack hand = getOwner().getInventory().getItemInMainHand();
            if(hand.getType() != null && hand.getType() == getGwItem().getType() && hand.hasItemMeta() &&
                    hand.getItemMeta().getLore() != null && hand.getItemMeta().getLore().contains(getGwItem().getId())) {
                getOwner().getInventory().setItem(getOwner().getInventory().getHeldItemSlot(), getItem());
                getOwner().updateInventory();
                return;
            }
        }
        List<ItemStack> items = Arrays.asList(getOwner().getInventory().getContents());
        for(ItemStack i : items) {
            if(i == null) {
                continue;
            }
            if(i.hasItemMeta()) {
                if(i.getItemMeta().getLore() != null && i.getItemMeta().getLore().contains(getGwItem().getId())); {
                    getOwner().getInventory().setItem(items.indexOf(i), getItem());
                    break;
                }
            }
        }
        getOwner().updateInventory();
    }

    @Override
    public void cancelReload() {
        if(reload != null && !reload.isCancelled()) {
            reload.cancel();
            reloading = false;
            canFire = true;
            updateName();
        }

    }
}
