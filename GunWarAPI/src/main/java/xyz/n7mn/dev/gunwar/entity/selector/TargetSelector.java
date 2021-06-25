package xyz.n7mn.dev.gunwar.entity.selector;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface TargetSelector {

    public enum Type {

        ALL_ENTITY(0, "e"),
        ALL_PLAYER(1, "a"),
        NEAREST_PLAYER(2, "p"),
        ACTIVE_ENTITY(3, "s");

        private int value;
        private CharSequence character;

        private Type(int value, CharSequence character) {
            this.value = value;
            this.character = character;
        }

        public int getValue() {
            return value;
        }

        public CharSequence getCharacter() {
            return character;
        }
    }

    public interface Builder {
        public TargetSelector.Builder withType(EntityType type);

        public TargetSelector.Builder withName(String name);

        public TargetSelector.Builder withMode(GameMode mode);

        public TargetSelector.Builder with(String key, Object value);

        public TargetSelector.Builder fromString(String selector);

        public TargetSelector build();
    }

    public static TargetSelector.Builder builder(Type selectorType, Player sender, Location source) {
        return A1.a1.builder(selectorType, sender, source);
    }

    public Type getSelectorType();

    public EntityType getType();

    public String getName();

    public GameMode getMode();

    public String toString();

    public Collection<? extends Entity> getTargets();

}
