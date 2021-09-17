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

package com.github.clockclap.gunwar.game.data;

import com.github.clockclap.gunwar.GwPlugin;
import com.github.clockclap.gunwar.item.GwItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@GwPlugin
public class GunWarItemData implements ItemData {

    private UUID uniqueId;
    private ItemStack item;
    private GwItem gwitem;
    private Player owner;

    public GunWarItemData(GwItem gwitem, ItemStack item, Player owner) {
        this.uniqueId = UUID.randomUUID();
        this.gwitem = gwitem;
        this.item = item;
        this.owner = owner;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public String getName() {
        return gwitem.getName();
    }

    @Override
    public GwItem getGwItem() {
        return gwitem;
    }

    @Override
    public Material getType() {
        return item.getType();
    }

    @Override
    public void setItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public void setOwner(Player player) {
        this.owner = player;
    }
}
