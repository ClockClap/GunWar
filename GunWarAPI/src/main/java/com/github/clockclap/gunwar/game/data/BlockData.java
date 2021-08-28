package com.github.clockclap.gunwar.game.data;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public interface BlockData extends CustomData {

    public Location getLocation();

    public Block getBlock();

    public Material getType();

}
