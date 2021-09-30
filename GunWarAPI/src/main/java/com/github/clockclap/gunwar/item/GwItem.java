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
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@GwAPI
public interface GwItem {

    ItemStack getItem();

    int getIndex();

    String getName();

    String getDisplayName();

    List<String> getDescription();

    String getId();

    Material getType();

    default void onClick(Player player, ClickAction action) { }

    default void onClickAtEntity(Player player, ClickAction action, Entity entity) { }

    default void onClickAtBlock(Player player, ClickAction action, Block block) { }

    default void onPlace(Player player, Block block) { }

    default void onBreak(Player player, Block block) { }

    default void onAttack(Player player, Entity target) { }

}
