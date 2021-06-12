package xyz.n7mn.dev.gunwar.game.data;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.UUID;

public class GunWarEntityData implements EntityData {

    private final UUID uniqueId;
    private final Entity entity;
    private final EntityType type;

    public GunWarEntityData(Entity entity) {
        this.uniqueId = entity.getUniqueId();
        this.entity = entity;
        this.type = entity.getType();
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public EntityType getType() {
        return type;
    }
}
