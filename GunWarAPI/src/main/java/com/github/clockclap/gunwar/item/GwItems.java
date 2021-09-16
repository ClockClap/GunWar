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

import com.github.clockclap.gunwar.item.items.GwItemAK47;

import java.util.ArrayList;
import java.util.List;

public final class GwItems {

    private static List<GwItem> registeredItems = new ArrayList<>();
    private static List<GwWeaponItem> registeredWeapons = new ArrayList<>();
    private static List<GwGunItem> registeredGuns = new ArrayList<>();

    public static GwGunItem AK47 = new GwItemAK47();

    public static List<GwItem> getRegisteredItems() {
        return registeredItems;
    }

    public static List<GwWeaponItem> getRegisteredWeapons() {
        return registeredWeapons;
    }

    public static List<GwGunItem> getRegisteredGuns() {
        return registeredGuns;
    }

    public static void register(GwItem item) {
        if(!registeredItems.contains(item)) {
            registeredItems.add(item);
            if(item instanceof GwWeaponItem) registeredWeapons.add((GwWeaponItem) item);
            if(item instanceof GwGunItem) registeredWeapons.add((GwGunItem) item);
        }
    }

    public static void unregister(GwItem item) {
        registeredItems.remove(item);
        if(item instanceof GwWeaponItem) registeredWeapons.remove(item);
        if(item instanceof GwGunItem) registeredGuns.remove(item);
    }

    public static void unregister(String name) {
        List<GwItem> lst = new ArrayList<>(getRegisteredItems());
        for(GwItem item : lst) {
            if(item.getName().equalsIgnoreCase(name)) {
                registeredItems.remove(item);
                if(item instanceof GwWeaponItem) registeredWeapons.remove(item);
                if(item instanceof GwGunItem) registeredGuns.remove(item);
            }
        }
    }

    public static void clear() {
        registeredItems.clear();
        registeredWeapons.clear();
        registeredGuns.clear();
    }

    public static void a() {
        register(AK47);
    }
}
