package com.github.clockclap.gunwar.util;

import org.bukkit.Material;

public final class BlockShape {

    public static boolean isSlab(Material material) {
        if(material == null || !material.isBlock()) return false;
        switch(material) {
            case STEP:
            case WOOD_STEP:
            case STONE_SLAB2:
            case PURPUR_SLAB:
                return true;
            default:
                return false;
        }
    }

    public static boolean isStairs(Material material) {
        if(material == null || !material.isBlock()) return false;
        switch(material) {
            case SANDSTONE_STAIRS:
            case SMOOTH_STAIRS:
            case ACACIA_STAIRS:
            case SPRUCE_WOOD_STAIRS:
            case BRICK_STAIRS:
            case COBBLESTONE_STAIRS:
            case BIRCH_WOOD_STAIRS:
            case DARK_OAK_STAIRS:
            case PURPUR_STAIRS:
            case QUARTZ_STAIRS:
            case WOOD_STAIRS:
            case JUNGLE_WOOD_STAIRS:
            case NETHER_BRICK_STAIRS:
            case RED_SANDSTONE_STAIRS:
                return true;
            default:
                return false;
        }
    }

    public static boolean isTransparent(Material material) {
        if(material == null || !material.isBlock()) return false;
        return material != Material.SKULL && (material.isTransparent() ||
                material == Material.SIGN_POST || material == Material.WALL_SIGN ||
                material == Material.WALL_BANNER || material == Material.STANDING_BANNER);
    }

    public static boolean isFence(Material material) {
        if(material == null || !material.isBlock()) return false;
        switch (material) {
            case FENCE:
            case ACACIA_FENCE:
            case BIRCH_FENCE:
            case IRON_FENCE:
            case DARK_OAK_FENCE:
            case JUNGLE_FENCE:
            case NETHER_FENCE:
            case STAINED_GLASS_PANE:
            case END_ROD:
            case THIN_GLASS:
            case SPRUCE_FENCE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isFenceGate(Material material) {
         if(material == null || !material.isBlock()) return false;
         switch(material) {
             case FENCE_GATE:
             case ACACIA_FENCE_GATE:
             case BIRCH_FENCE_GATE:
             case DARK_OAK_FENCE_GATE:
             case JUNGLE_FENCE_GATE:
             case SPRUCE_FENCE_GATE:
                 return true;
             default:
                 return false;
         }
    }

    public static boolean isDoor(Material material) {
        if(material == null || !material.isBlock()) return false;
        switch (material) {
            case DARK_OAK_DOOR:
            case ACACIA_DOOR:
            case BIRCH_DOOR:
            case IRON_DOOR_BLOCK:
            case JUNGLE_DOOR:
            case SPRUCE_DOOR:
            case WOOD_DOOR:
                return true;
            default:
                return false;
        }
    }

    public static boolean isTrapdoor(Material material) {
        if(material == null || !material.isBlock()) return false;
        switch (material) {
            case IRON_TRAPDOOR:
            case TRAP_DOOR:
                return true;
            default:
                return false;
        }
    }
}
