package xyz.n7mn.dev.gunwar.item;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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
