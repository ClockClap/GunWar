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
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@GwAPI
public abstract class GwWeaponBase extends GwItemBase implements GwWeaponItem {

    private float damage;
    private WeaponType type;

    protected GwWeaponBase() {
        this(0, Material.STONE, "", "", "", new ArrayList<>(), 0F, WeaponType.KNIFE);
    }

    protected GwWeaponBase(int index, Material type, String name, String displayName, String id, List<String> description, float damage, WeaponType weaponType) {
        super(index, type, name, displayName, id, description);
        this.damage = damage;
        this.type = weaponType;
    }

    public float getAttackDamage() {
        return damage;
    }

    public WeaponType getWeaponType() {
        return type;
    }

    protected void setAttackDamage(float damage) {
        this.damage = damage;
    }

    protected void setWeaponType(WeaponType type) {
        this.type = type;
    }

    protected void setDescription(List<String> description) {
        this.description = description;
        ItemMeta meta = getItem().getItemMeta();
        List<String> lore = new ArrayList<>(getDescription());
        lore.add("");
        lore.add(ChatColor.GRAY + "攻撃力: " + ChatColor.GOLD + getAttackDamage());
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + getId());
        meta.setLore(lore);
        getItem().setItemMeta(meta);
    }

    protected void setId(String id) {
        this.id = id;
        ItemMeta meta = getItem().getItemMeta();
        List<String> lore = new ArrayList<>(getDescription());
        lore.add("");
        lore.add(ChatColor.GRAY + "攻撃力: " + ChatColor.GOLD + getAttackDamage());
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + getId());
        meta.setLore(lore);
        getItem().setItemMeta(meta);
    }


}
