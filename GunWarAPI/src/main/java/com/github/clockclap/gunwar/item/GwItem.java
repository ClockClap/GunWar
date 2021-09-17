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

import com.github.clockclap.gunwar.GwAPI;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@GwAPI
public interface GwItem {

    public ItemStack getItem();

    public int getIndex();

    public String getName();

    public String getDisplayName();

    public List<String> getDescription();

    public String getId();

    public Material getType();

    public default void onClick(Player player, ClickAction action) { }

    public default void onClickAtEntity(Player player, ClickAction action, Entity entity) { }

    public default void onClickAtBlock(Player player, ClickAction action, Block block) { }

    public default void onPlace(Player player, Block block) { }

    public default void onBreak(Player player, Block block) { }

    public default void onAttack(Player player, Entity target) { }

}
