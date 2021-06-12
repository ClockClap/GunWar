package xyz.n7mn.dev.gunwar.game.data;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.UUID;

public interface EntityData extends CustomData {

    public UUID getUniqueId();

    public EntityType getType();

    public Entity getEntity();

}
