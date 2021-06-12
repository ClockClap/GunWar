package xyz.n7mn.dev.gunwar.game.data;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import xyz.n7mn.dev.gunwar.item.GwItem;

import java.util.UUID;

public interface ItemData {

    public UUID getUniqueId();

    public ItemStack getItem();

    public Player getOwner();

    public String getName();

    public GwItem getGwItem();

    public Material getType();

    public void setItem(ItemStack item);

    public void setOwner(Player player);
}
