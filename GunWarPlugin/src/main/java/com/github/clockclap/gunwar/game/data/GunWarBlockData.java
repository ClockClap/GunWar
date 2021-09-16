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

import net.minecraft.server.v1_12_R1.BlockPosition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class GunWarBlockData implements BlockData {

    private final Block block;

    public GunWarBlockData(Block block) {
        this.block = block;
    }

    @Override
    public Location getLocation() {
        return block.getLocation();
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public Material getType() {
        return block.getType();
    }

    public BlockPosition getPosition() {
        return new BlockPosition(block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ());
    }
}
