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

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class GwItemBase implements GwItem {

    private ItemStack item;
    private int index;
    private String name;
    private String displayName;
    protected String id;
    protected List<String> description;
    private Material type;

    protected GwItemBase() {
        this(0, Material.STONE, "", "", "", new ArrayList<>());
    }

    protected GwItemBase(int index, Material type, String name, String displayName, String id, List<String> description) {
        this.index = index;
        this.type = type;
        this.name = name;
        this.displayName = displayName;
        this.id = id;
        this.description = description;
        ItemStack i = new ItemStack(type);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(displayName);
        List<String> lore = new ArrayList<>(description);
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + id);
        meta.setLore(lore);
        i.setItemMeta(meta);
        i.setAmount(1);
        this.item = i;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public List<String> getDescription() {
        return description;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Material getType() {
        return type;
    }

    protected void setIndex(int index) {
        this.index = index;
    }

    protected void setItem(ItemStack item) {
        this.item = item;
        this.type = item.getType();
        ItemMeta meta = getItem().getItemMeta();
        meta.setDisplayName(getDisplayName());
        List<String> lore = new ArrayList<>(getDescription());
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + getId());
        meta.setLore(lore);
        getItem().setItemMeta(meta);
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setDisplayName(String displayName) {
        this.displayName = displayName;
        ItemMeta meta = getItem().getItemMeta();
        meta.setDisplayName(getDisplayName());
        getItem().setItemMeta(meta);
    }

    protected void setDescription(List<String> description) {
        this.description = description;
        ItemMeta meta = getItem().getItemMeta();
        List<String> lore = new ArrayList<>(getDescription());
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
        lore.add(ChatColor.DARK_GRAY + getId());
        meta.setLore(lore);
        getItem().setItemMeta(meta);
    }

}
