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

package com.github.clockclap.gunwar.item.items;

import com.github.clockclap.gunwar.item.GwGunType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import com.github.clockclap.gunwar.item.GwGunBase;

import java.util.Arrays;

public class GwItemAK47 extends GwGunBase {

    public GwItemAK47() {
        super(0, Material.IRON_HOE, "AK47", "AK-47", "0f00a46e", Arrays.asList("" +
                        ChatColor.WHITE + "初期武器のAK-47。ソ連が開発したアサルトライフル。攻撃力や精度、ノックバックなどから考えて割と使いやすい。"),
                10F, 5F, 3F, 30, 30, 0.6F, 0F, 100L,
                4L, 30F, 50F, 300F, 0.8F, GwGunType.ASSAULT_RIFLE);
    }

}
