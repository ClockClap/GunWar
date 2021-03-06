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

package com.github.clockclap.gunwar.listeners;

import com.github.clockclap.gunwar.GunWar;
import com.github.clockclap.gunwar.GwPlugin;
import com.github.clockclap.gunwar.game.data.GunData;
import com.github.clockclap.gunwar.game.data.ItemData;
import com.github.clockclap.gunwar.game.data.PlayerData;
import com.github.clockclap.gunwar.item.GwGunItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

@GwPlugin
public class ItemListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        PlayerData data = GunWar.getGame().getPlayerData(e.getPlayer());
        if(data != null && e.getHand() == EquipmentSlot.HAND) {
            ItemData itemData = GunWar.getGame().getItemData(e.getItem());
            if (itemData != null) {
                if (itemData instanceof GunData) {
                    e.setCancelled(true);
                    GunData gunData = (GunData) itemData;
                    if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        if(e.getPlayer().isSneaking()) {
                            if (gunData.getAmmo() <= 0) gunData.reload();
                            gunData.fire(true);
                        } else {
                            BukkitRunnable loop = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (gunData.getAmmo() <= 0) gunData.reload();
                                    gunData.fire(false);
                                }
                            };
                            loop.runTaskTimer(GunWar.getPlugin(), 0, 1);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    loop.cancel();;
                                }
                            }.runTaskLater(GunWar.getPlugin(), 5);
                        }
                    } else if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                        if(data.isZoom()) {
                            data.setZoom(false, 0);
                        } else {
                            data.setZoom(true, ((GwGunItem) gunData.getGwItem()).getZoomLevel());
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent e) {
        PlayerData data = GunWar.getGame().getPlayerData(e.getPlayer());
        if(data != null) {
            ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
            if(item != null) {
                ItemData itemData = GunWar.getGame().getItemData(e.getPlayer().getInventory().getItemInMainHand());
                if (itemData != null) {
                    if (itemData instanceof GunData) {
                        GunData gunData = (GunData) itemData;
                        if (gunData.getAmmo() <= 0) gunData.reload();
                        gunData.fire(e.getPlayer().isSneaking());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        ItemData data = GunWar.getGame().getItemData(e.getItemDrop().getItemStack());
        if(data instanceof GunData) {
            e.setCancelled(true);
            ((GunData) data).reload();
        }
    }

    @EventHandler
    public void onSlotChange(PlayerItemHeldEvent e) {
        for(ItemStack i : e.getPlayer().getInventory().getContents()) {
            ItemData data = GunWar.getGame().getItemData(i);
            if(data instanceof GunData) {
                GunData gunData = (GunData) data;
                gunData.cancelReload();
                PlayerData pdata = GunWar.getGame().getPlayerData(e.getPlayer());
                if(pdata != null) pdata.setZoom(false, 0);
            }
        }
    }

}
