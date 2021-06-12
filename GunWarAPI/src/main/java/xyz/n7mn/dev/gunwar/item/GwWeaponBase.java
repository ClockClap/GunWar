package xyz.n7mn.dev.gunwar.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class GwWeaponBase extends GwItemBase implements GwWeaponItem {

    private float damage;

    protected GwWeaponBase() {
        this(0, Material.STONE, "", "", "", new ArrayList<>(), 0F);
    }

    protected GwWeaponBase(int index, Material type, String name, String displayName, String id, List<String> description, float damage) {
        super(index, type, name, displayName, id, description);
        this.damage = damage;
    }

    public float getAttackDamage() {
        return damage;
    }

    protected void setAttackDamage(float damage) {
        this.damage = damage;
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
