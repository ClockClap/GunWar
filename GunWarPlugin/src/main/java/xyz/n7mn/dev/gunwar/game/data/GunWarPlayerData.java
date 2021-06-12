package xyz.n7mn.dev.gunwar.game.data;

import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.entity.HitEntity;
import xyz.n7mn.dev.gunwar.game.GunWarGame;
import xyz.n7mn.dev.gunwar.item.GwGunItem;
import xyz.n7mn.dev.gunwar.item.GwItem;
import xyz.n7mn.dev.gunwar.util.PlayerWatcher;

import java.util.List;

public class GunWarPlayerData extends GunWarEntityData implements PlayerData {

    private final Player player;
    private PlayerWatcher watcher;
    private float health;
    private float maxHealth;
    private int team;
    private boolean spectator;
    private boolean clickable;

    public GunWarPlayerData(Player player) {
        super(player);
        this.player = player;
        this.health = 100;
        this.maxHealth = 100;
        this.team = -1;
        this.spectator = true;
        this.clickable = true;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public PlayerWatcher getWatcher() {
        return watcher;
    }

    public void setWatcher(PlayerWatcher watcher) {
        this.watcher = watcher;
    }

    public float getHealth() {
        return health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    @Override
    public int getTeam() {
        return team;
    }

    @Override
    public boolean isSpectator() {
        return spectator;
    }

    @Override
    public boolean isClickable() {
        return clickable;
    }

    @Override
    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    @Override
    public void setHealth(float health) {
        this.health = Math.min(health, maxHealth);
    }

    @Override
    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public void setTeam(int team) {
        this.team = team;
        if(team < 0) setSpectator(true);
    }

    @Override
    public void setSpectator(boolean spectator) {
        this.spectator = spectator;
    }

    private Vector a(Vector onto, Vector u) {
        return u.clone().subtract(b(onto, u));
    }

    private Vector b(Vector onto, Vector u) {
        return onto.clone().multiply(onto.dot(u) / onto.lengthSquared());
    }

    private void c(Particle particle, double xb, double yb, double zb) {
        Player p = getPlayer();

        double twopi = 2 * Math.PI;
        double times = 1 * twopi;
        double division = twopi / 100;
        double radius = 2;

        Location c = p.getEyeLocation();
        Vector nv = c.getDirection().normalize();

        double nx = radius * nv.getX() + c.getX();
        double ny = radius * nv.getY() + c.getY();
        double nz = radius * nv.getZ() + c.getZ();

        Vector ya = a(nv, new Vector(0, 1, 0)).normalize();
        Vector xa = ya.getCrossProduct(nv).normalize();

        for (double theta = 0; theta < times; theta += division) {
            double xi = xa.getX() * xb + ya.getX() * yb + nv.getX() * zb;
            double yi = xa.getY() * xb + ya.getY() * yb + nv.getY() * zb;
            double zi = xa.getZ() * xb + ya.getZ() * yb + nv.getZ() * zb;

            double x = xi + nx;
            double y = yi + ny;
            double z = zi + nz;

            p.spawnParticle(particle, new Location(c.getWorld(), x, y, z), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public void drawParticleLine(Particle particle, double start, double far, double separate) {
        drawParticleLine(particle, 0, 0, start, far, 0, 0, separate);
    }

    @Override
    public void drawParticleLine(Particle particle, double startX, double startY, double startZ,
                                 double far, double separateX, double separateY, double separateZ) {
        double d = Math.sqrt(Math.pow(separateX, 2) + Math.pow(separateY, 2) + Math.pow(separateZ, 2)) * far;
        if(startZ < d) {
            double times = d / separateZ;
            double x = startX;
            double y = startY;
            for (double z = startZ; z < times; z += separateZ) {
                c(particle, x, y, z);
                x += separateX;
                y += separateY;
            }
        }
    }

    @Override
    public HitEntity drawParticleLine(Particle particle, double startX, double startY, double startZ,
                                      double far, double separateX, double separateY, double separateZ, GwGunItem gun) {
        double d = Math.sqrt(Math.pow(separateX, 2) + Math.pow(separateY, 2) + Math.pow(separateZ, 2)) * far;
        if(startZ < d) {
            double times = d / separateZ;
            double x = startX;
            double y = startY;
            double damageMin = gun.getAttackDamage() / 1.3;
            double hsdamageMin = gun.getHeadShotDamage() / 1.3;
            double currentDamage = gun.getAttackDamage();
            double currentHSDamage = gun.getHeadShotDamage();
            double separateDamage = currentDamage - damageMin / times;
            double separateHSDamage = currentHSDamage - hsdamageMin / times;
            for (double z = startZ; z < times; z += separateZ) {
                c(particle, x, y, z);
                for(Entity entity : getPlayer().getNearbyEntities(gun.getRange() + 1, gun.getRange() + 1, gun.getRange() + 1)) {
                    if(entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        double xmin = entity.getLocation().getX() - (entity.getWidth() / 2);
                        double xmax = entity.getLocation().getX() + (entity.getWidth() / 2);
                        double ymin = entity.getLocation().getY();
                        double ymax = entity.getLocation().getY() + entity.getHeight();
                        double zmin = entity.getLocation().getZ() - (entity.getWidth() / 2);
                        double zmax = entity.getLocation().getZ() + (entity.getWidth() / 2);
                        boolean condition1 = xmin <= x && xmax >= x;
                        boolean condition2 = ymin <= y && ymax >= y;
                        boolean condition3 = zmin <= z && zmax >= z;
                        if (condition1 && condition2 && condition3) {
                            double hxmin = livingEntity.getEyeLocation().getX() - (livingEntity.getEyeHeight() / 2);
                            double hxmax = livingEntity.getEyeLocation().getX() + (livingEntity.getEyeHeight() / 2);
                            double hymin = livingEntity.getEyeLocation().getY() - (livingEntity.getEyeHeight() / 2);
                            double hymax = livingEntity.getEyeLocation().getY() + (livingEntity.getEyeHeight() / 2);
                            double hzmin = livingEntity.getEyeLocation().getZ() - (livingEntity.getEyeHeight() / 2);
                            double hzmax = livingEntity.getEyeLocation().getZ() + (livingEntity.getEyeHeight() / 2);
                            boolean hcondition1 = hxmin <= x && hxmax >= x;
                            boolean hcondition2 = hymin <= y && hymax >= y;
                            boolean hcondition3 = hzmin <= z && hzmax >= z;
                            boolean headShot = hcondition1 && hcondition2 && hcondition3;
                            return new HitEntity(livingEntity, headShot, headShot ? currentHSDamage : currentDamage,
                                    getPlayer().getEyeLocation(), new Location(livingEntity.getWorld(), x, y, z));
                        }
                    }
                }
                Location loc = new Location(getPlayer().getWorld(), x, y, z);
                Block block = loc.getBlock();
                if(block != null && block.getType() != Material.AIR && block.getType() != Material.STRUCTURE_VOID) {
                    @SuppressWarnings("deprecation")
                    PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(2001,
                            new BlockPosition(block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ()),
                            block.getType().getId(), false);
                    List<Player> players = getPlayer().getWorld().getPlayers();
                    for(final Player p : players) {
                        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                    }
                    return null;
                }
                x += separateX;
                y += separateY;
                currentDamage -= separateDamage;
                currentHSDamage -= separateHSDamage;
            }
        }
        return null;
    }

    @Override
    public void giveItem(GwItem item) {
        ItemStack i = item.getItem().clone();
        ((GunWarGame) GunWar.getGame()).addItemData(new GunWarItemData(item, i, getPlayer()));
        getPlayer().getInventory().addItem(i);
        getPlayer().updateInventory();
    }
}
