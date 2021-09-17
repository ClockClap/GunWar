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
import com.github.clockclap.gunwar.item.GwGunItem;
import com.github.clockclap.gunwar.item.GwKnifeItem;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@GwPlugin
public class GunWarKnifeData extends GunWarItemData implements KnifeData {

    private boolean reloading;
    private boolean canFire;
    private int ammo;
    private BukkitRunnable fire;
    private BukkitRunnable reload;

    public GunWarKnifeData(GwKnifeItem gwitem, ItemStack item, Player owner) {
        super(gwitem, item, owner);
        reloading = false;
        canFire = true;
        ammo = 1;
        updateName();
    }

    @Override
    public boolean canThrow() {
        return canFire;
    }

    @Override
    public void throwKnife() {
        if(canFire && ammo > 0) {

            Random random = new Random();
            float accuracy = ((GwGunItem) getGwItem()).getAccuracy();
            if (getOwner().isSneaking()) {
                accuracy = ((GwGunItem) getGwItem()).getAccuracyOnSneak();
            }

            HitEntity hitEntity = GunWar.getGame().getPlayerData(getOwner()).drawParticleLine(
                    Particle.CRIT, 0, 0, 0.1, 5.2, 0.3, (GwKnifeItem) getGwItem());
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
                    double d = 1 / far;
                    Vector vector = new Vector(subX * d, subY * d, subZ * d);
                    hitEntity.getEntity().setVelocity(vector);
                }
                double recoil = (getOwner().isSneaking() ? ((GwGunItem) getGwItem()).getRecoilOnSneak() : ((GwGunItem) getGwItem()).getRecoil());
                double d_ = recoil / far;
                Vector vector_ = new Vector(subX * d_ * -1, subY * d_ * -1, subZ * d_ * -1);
                getOwner().setVelocity(vector_);
            }

            ((GwGunItem) getGwItem()).onShoot(getOwner());

            ammo--;
            updateName();
            if(ammo <= 0) {
                canFire = false;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        canFire = true;
                        ammo++;
                        updateName();
                    }
                }.runTaskLater(GunWar.getPlugin(), 10);
                return;
            }
        }
    }

    @Override
    public void cancelThrowingCooldown() {
        if(fire != null && !fire.isCancelled()) fire.cancel();
    }

    public void updateName() {
        ItemMeta meta = getItem().getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + getGwItem().getDisplayName() + " " + (!canFire ? "▫" : "▪") + " " + (!canFire ? ChatColor.DARK_GRAY : ChatColor.GRAY) + "«" + ammo + "»");
        getItem().setItemMeta(meta);
        List<ItemStack> items = Arrays.asList(getOwner().getInventory().getContents());
        for(ItemStack i : items) {
            if(i == null) {
                continue;
            }
            if(i.hasItemMeta()) {
                if(i.getItemMeta().getLore() != null && i.getItemMeta().getLore().contains(getGwItem().getId())); {
                    getOwner().getInventory().setItem(items.indexOf(i), getItem());
                }
            }
        }
        getOwner().updateInventory();
    }

}
