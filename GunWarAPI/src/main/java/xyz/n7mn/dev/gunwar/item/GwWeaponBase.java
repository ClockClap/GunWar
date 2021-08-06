package xyz.n7mn.dev.gunwar.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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
