package xyz.n7mn.dev.gunwar.item;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public abstract class GwKnifeBase extends GwWeaponBase implements GwKnifeItem {

    protected GwKnifeBase() {
        this(0, Material.STONE, "", "", "", new ArrayList<>(), 0F);
    }

    protected GwKnifeBase(int index, Material type, String name, String displayName, String id, List<String> description, float damage) {
        super(index, type, name, displayName, id, description, damage);
    }

}
