package com.github.clockclap.gunwar.game.data;

import com.github.clockclap.gunwar.item.GwItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

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
