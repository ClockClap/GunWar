package xyz.n7mn.dev.gunwar.entity.selector;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.n7mn.dev.gunwar.exception.InvalidTargetSelectorException;
import xyz.n7mn.dev.gunwar.exception.TargetSelectorException;

import java.util.*;

public final class CraftTargetSelector implements TargetSelector {

    public static final class Builder implements TargetSelector.Builder {

        private Location source;
        private Type selectorType;
        private EntityType type;
        private String name;
        private GameMode mode;
        private CraftPlayer sender;
        private int c;
        private TargetSelectorException ex;

        Builder(@NotNull Type selectorType, CraftPlayer playerSender, Location source) {
            this.source = source;
            this.selectorType = selectorType;
            this.sender = playerSender;
            type = null;
            name = null;
            mode = null;
            c = 1024;
        }

        @Override
        public TargetSelector.Builder withType(EntityType type) {
            this.type = type;
            return this;
        }

        @Override
        public TargetSelector.Builder withName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public TargetSelector.Builder withMode(GameMode mode) {
            this.mode = mode;
            return this;
        }

        @Override
        public TargetSelector.Builder withCount(int count) {
            this.c = count;
            return this;
        }

        @Override
        public TargetSelector.Builder with(@NotNull String key, Object value) {
            if(value != null) {
                if (key.equalsIgnoreCase("type") && value instanceof EntityType) {
                    this.type = (EntityType) value;
                } else if (key.equalsIgnoreCase("name") && value instanceof String) {
                    this.name = (String) value;
                } else if (key.equalsIgnoreCase("m") && value instanceof GameMode) {
                    this.mode = (GameMode) value;
                } else if (key.equalsIgnoreCase("c") && value instanceof Integer) {
                    this.c = (Integer) value;
                }
            }
            return this;
        }

        @Override
        public TargetSelector.Builder fromString(@NotNull String selector) {
            if(selector.length() >= 2 && selector.startsWith("@")) {
                this.type = null;
                this.name = null;
                this.mode = null;
                Type t = Type.ALL_ENTITY;
                switch (selector.charAt(1)) {
                    case 'e':
                        t = Type.ALL_ENTITY;
                        break;
                    case 'a':
                        t = Type.ALL_PLAYER;
                        break;
                    case 'p':
                        t = Type.NEAREST_PLAYER;
                        break;
                    case 's':
                        t = Type.ACTIVE_ENTITY;
                        break;
                }
                this.selectorType = t;
                if(selector.length() >= 4 && selector.charAt(2) == '[') {
                    String option = selector.substring(3, selector.length() - 1);
                    String[] l = option.split(",");
                    for(String o : l) {
                        String[] s = o.split("=");
                        String key = s[0];
                        String value = s[1];
                        Object object = null;
                        if(key.equalsIgnoreCase("type")) {
                            String str = value;
                            if(value.startsWith("minecraft:")) {
                                str = value.substring(10);
                            }
                            object = EntityType.fromName(str);
                        }
                        if(key.equalsIgnoreCase("name")) {
                            object = value;
                        }
                        if(key.equalsIgnoreCase("m")) {
                            try {
                                object = GameMode.getByValue(Integer.parseInt(value));
                            } catch (NumberFormatException ignored) { }
                        }
                        if(key.equalsIgnoreCase("c")) {
                            try {
                                object = Integer.parseInt(value);
                            } catch(NumberFormatException ignored) { }
                        }
                        with(key, object);
                    }
                }
                return this;
            }
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(selector.equalsIgnoreCase(p.getName())) {
                    selectorType = Type.ALL_PLAYER;
                    withCount(1);
                    withName(p.getName());
                    withMode(p.getGameMode());
                    withType(EntityType.PLAYER);
                    return this;
                }
            }
            ex = new InvalidTargetSelectorException("invalid format");
            return this;
        }

        @Override
        public TargetSelector build() throws TargetSelectorException {
            if(ex != null) {
                throw ex;
            }
            return new CraftTargetSelector(selectorType, sender, source, type, name, mode, c);
        }

    }

    static CraftTargetSelector.Builder builder(@NotNull Type selectorType, CraftPlayer playerSender, Location source) {
        return new CraftTargetSelector.Builder(selectorType, playerSender, source);
    }

    private final Type selectorType;
    private final EntityType type;
    private final String name;
    private final GameMode mode;
    private final int count;
    private final List<CraftEntity> targets;

    private CraftTargetSelector(@NotNull Type selectorType, @Nullable CraftPlayer playerSender, @Nullable Location source,
                                @Nullable EntityType type, @Nullable String name, @Nullable GameMode mode, int count) {
        this.selectorType = selectorType;
        this.type = type;
        this.name = name;
        this.mode = mode;
        this.count = count;
        this.targets = new ArrayList<>();
        int c = Math.abs(count);
        List<World> worlds = Bukkit.getWorlds();
        List<Entity> entities = new ArrayList<>();
        if(source != null) {
            worlds.clear();
            worlds.add(source.getWorld());
            for(World w : Bukkit.getWorlds()) if(w != source.getWorld()) worlds.add(w);
        }
        double distance = -1;
        CraftEntity nearestEntity = null;
        int i = 0;
        for(World world : worlds) {
            if (source != null && world == source.getWorld()) {
                entities.addAll(source.getNearbyEntities(2500, 2500, 2500));
                continue;
            }
            entities.addAll(world.getEntities());
        }
        if(count < 0) Collections.reverse(entities);
        for(Entity entity : entities) {
            if(++i > c) break;
            CraftEntity craftEntity = (CraftEntity) entity;
            if(!(
                    (type != null && craftEntity.getType() != type) ||
                            (name != null && craftEntity.getCustomName().equalsIgnoreCase(name)) ||
                            (mode != null && craftEntity instanceof CraftPlayer && ((CraftPlayer)craftEntity).getGameMode() != mode)
            )) {
                if(this.selectorType == Type.ALL_ENTITY) {
                    this.targets.add(craftEntity);
                } else if(this.selectorType == Type.ALL_PLAYER) {
                    if(craftEntity instanceof CraftPlayer) {
                        this.targets.add(craftEntity);
                    }
                } else if(this.selectorType == Type.NEAREST_PLAYER) {
                    if(craftEntity instanceof CraftPlayer && source != null) {
                        double d = Math.abs(Math.sqrt(Math.pow(source.getX() - craftEntity.getLocation().getX(), 2) +
                                Math.pow(source.getY() - craftEntity.getLocation().getY(), 2) +
                                Math.pow(source.getZ() - craftEntity.getLocation().getZ(), 2)));
                        if(distance == -1 || distance > d) {
                            distance = d;
                            nearestEntity = craftEntity;
                        }
                    }
                } else if(this.selectorType == Type.ACTIVE_ENTITY) {
                    if(playerSender != null) this.targets.add(playerSender);
                }
            }
        }
        if(nearestEntity != null) {
            this.targets.add(nearestEntity);
        }
    }


    @Override
    @NotNull
    public Type getSelectorType() {
        return selectorType;
    }

    @Override
    @Nullable
    public EntityType getType() {
        return type;
    }

    @Override
    @Nullable
    public String getName() {
        return name;
    }

    @Override
    @Nullable
    public GameMode getMode() {
        return mode;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public String toString() {

        String type = (getType() == null ? "" : nonnull(getType().getName()));
        String name = nonnull(getName());
        int m_ = -1;
        if(getMode() != null) m_ = getMode().getValue();
        String m = "";
        if(m_ != -1) m = String.valueOf(m_);
        Map<String, String> options = new HashMap<>();
        List<String> a_ = new ArrayList<>();
        options.put("type", type);
        options.put("name", name);
        options.put("m", m);
        for(Map.Entry<String, String> entry : options.entrySet()) {
            if(!entry.getValue().isEmpty()) {
                String s_ = entry.getKey() + "=" + entry.getValue();
                a_.add(s_);
            }
        }
        String option = String.join(",", a_);
        String result = "@" + selectorType.getCharacter().toString();
        if(option.length() > 0) {
            result += "[" + option + "]";
        }
        return result;
    }


    private String nonnull(String nullableString) {
        return nullableString == null ? "" : "minecraft:" + nullableString;
    }

    @Override
    public Collection<CraftEntity> getTargets() {
        return targets;
    }

}
