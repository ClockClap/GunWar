package com.github.clockclap.gunwar.game.data;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.UUID;

public interface EntityData extends CustomData {

    public UUID getUniqueId();

    public EntityType getType();

    public Entity getEntity();

    public class Detail {
        public boolean canSee(LivingEntity from) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }
    }

    public Detail detail();

}
