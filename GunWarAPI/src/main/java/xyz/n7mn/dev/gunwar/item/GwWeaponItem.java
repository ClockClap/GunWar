package xyz.n7mn.dev.gunwar.item;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface GwWeaponItem extends GwItem {

    public float getAttackDamage();

    public WeaponType getWeaponType();

}
