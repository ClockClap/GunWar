package com.github.clockclap.gunwar.util.world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public final class Worlds {

    public static final double DEFAULT_MIN_COORDINATE_X = 0xFE363C80;
    public static final double DEFAULT_MIN_COORDINATE_Y = 0;
    public static final double DEFAULT_MIN_COORDINATE_Z = 0xFE363C80;

    public static final double DEFAULT_MAX_COORDINATE_X = 0x1C9C380;
    public static final double DEFAULT_MAX_COORDINATE_Y = 0x100;
    public static final double DEFAULT_MAX_COORDINATE_Z = 0x1C9C380;

    public static final double BLOCK_16x16_1PIXEL = 0.0625;

    public static double toCoordinate(int pixel) {
        return BLOCK_16x16_1PIXEL * pixel;
    }

    public static World getWorld(String name) {
        return Bukkit.getWorld(name);
    }

    public static World getWorld(UUID id) {
        return Bukkit.getWorld(id);
    }

    public static List<World> getWorlds() {
        return Bukkit.getWorlds();
    }

    public static List<World> getWorldsByStrings(Collection<String> strings) {
        List<World> worlds = new ArrayList<>();
        for(String str : strings) {
            World w = Bukkit.getWorld(str);
            if(w != null) worlds.add(w);
        }
        return worlds;
    }

    public static List<World> getWorldsByIdentifiers(Collection<UUID> ids) {
        List<World> worlds = new ArrayList<>();
        for(UUID id : ids) {
            World w = Bukkit.getWorld(id);
            if(w != null) worlds.add(w);
        }
        return worlds;
    }

    public static long getSeed(World world) {
        if(world != null) {
            return world.getSeed();
        }
        return 0;
    }

    public static long getSeed(UUID world) {
        World w = Bukkit.getWorld(world);
        if(w != null) {
            return w.getSeed();
        }
        return 0;
    }

    public static long getSeed(String world) {
        World w = Bukkit.getWorld(world);
        if(w != null) {
            return w.getSeed();
        }
        return 0;
    }

    public static World create(WorldCreator worldCreator) {
        return Bukkit.createWorld(worldCreator);
    }

}
