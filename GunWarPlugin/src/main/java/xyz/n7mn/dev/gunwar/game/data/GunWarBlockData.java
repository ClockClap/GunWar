package xyz.n7mn.dev.gunwar.game.data;

import net.minecraft.server.v1_12_R1.BlockPosition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class GunWarBlockData implements BlockData {

    private final Block block;

    public GunWarBlockData(Block block) {
        this.block = block;
    }

    @Override
    public Location getLocation() {
        return block.getLocation();
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public Material getType() {
        return block.getType();
    }

    public BlockPosition getPosition() {
        return new BlockPosition(block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ());
    }
}
