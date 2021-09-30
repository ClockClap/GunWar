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
import com.github.clockclap.gunwar.game.data.PermanentlyPlayerData;
import com.github.clockclap.gunwar.game.data.PlayerData;
import com.github.clockclap.gunwar.game.gamemode.GwGameModes;
import com.github.clockclap.gunwar.item.GwGunItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

@GwPlugin
public class DamageListener implements Listener {

    @EventHandler
    public void onExplodeEntity(EntityExplodeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            e.setDamage(0);
            PlayerData vdata = GunWar.getGame().getPlayerData((Player) e.getEntity());
            PermanentlyPlayerData vdata_ = GunWar.getGame().getPermanentlyPlayerData(e.getEntity().getUniqueId());
            PlayerData ddata = GunWar.getGame().getPlayerData((Player) e.getDamager());
            PermanentlyPlayerData ddata_ = GunWar.getGame().getPermanentlyPlayerData(e.getDamager().getUniqueId());
            if(!vdata.isSpectator() && !ddata.isSpectator()) {
                if(GunWar.getGame().getGameMode() == GwGameModes.ZOMBIE_ESCAPE) {
                    if (ddata.getTeam() == 1 && vdata.getTeam() == 0) {
                        e.setCancelled(true);
                        vdata.infect();
                        ddata_.setInfectedCount(ddata_.getInfectedCount() + 1);
                    }
                }
                if(vdata.getHealth() <= 0) {
                    vdata_.setDeathCount(vdata_.getDeathCount() + 1);
                    ItemData i = GunWar.getGame().getItemData(((Player) e.getDamager()).getInventory().getItemInMainHand());
                    if (i instanceof GunData) {
                        GwGunItem gi = (GwGunItem) i.getGwItem();
                        ddata_.getKillCount().put(gi, ddata_.getKillCount().get(gi));
                    }
                    if(GunWar.getGame().getGameMode() == GwGameModes.ZOMBIE_ESCAPE) {
                        vdata.kill();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(!(e instanceof EntityDamageByEntityEvent)) e.setCancelled(true);
    }

    @EventHandler
    public void onDeathByEntity(EntityDeathEvent e) {
        if(e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e.getEntity().getLastDamageCause();
            if(event.getDamager() instanceof Player) {
                PlayerData ddata = GunWar.getGame().getPlayerData((Player) event.getDamager());
                if(!ddata.isSpectator()) {
                    PermanentlyPlayerData ddata_ = GunWar.getGame().getPermanentlyPlayerData(event.getDamager().getUniqueId());
                    if (ddata_ != null) {
                        ItemData i = GunWar.getGame().getItemData(((Player) event.getDamager()).getInventory().getItemInMainHand());
                        if (i instanceof GunData) {
                            GwGunItem gi = (GwGunItem) i.getGwItem();
                            ddata_.getKillCount().put(gi, ddata_.getKillCount().get(gi));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if(!e.getPlayer().isOp()) e.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(!e.getPlayer().isOp()) e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            PlayerData data = GunWar.getGame().getPlayerData(e.getPlayer());
            if(!data.isSpectator() && !e.getPlayer().isOp()) e.setCancelled(true);
        }
    }

}
