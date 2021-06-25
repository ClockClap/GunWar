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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.entity.HitEntity;
import xyz.n7mn.dev.gunwar.game.GunWarGame;
import xyz.n7mn.dev.gunwar.game.gamemode.GameModeNormal;
import xyz.n7mn.dev.gunwar.game.gamemode.GwGameModes;
import xyz.n7mn.dev.gunwar.item.GwGunItem;
import xyz.n7mn.dev.gunwar.item.GwItem;
import xyz.n7mn.dev.gunwar.util.Angle;
import xyz.n7mn.dev.gunwar.util.PlayerWatcher;
import xyz.n7mn.dev.gunwar.util.Reference;

import java.sql.Ref;
import java.util.List;

public class GunWarPlayerData extends GunWarEntityData implements PlayerData {

    private final Player player;
    private PlayerWatcher watcher;
    private double health;
    private double maxHealth;
    private int team;
    private boolean spectator;
    private boolean clickable;
    private boolean moveable;
    private boolean dead;
    private Location loc;
    private boolean zoom;
    private float zoomLevel;

    public GunWarPlayerData(Player player) {
        super(player);
        this.player = player;
        this.health = 100;
        this.maxHealth = 100;
        this.team = -1;
        this.spectator = true;
        this.clickable = true;
        this.moveable = true;
        this.loc = player.getLocation();
        this.dead = false;
        this.zoom = false;
        this.zoomLevel = 0F;
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

    public double getHealth() {
        return health;
    }

    public double getMaxHealth() {
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
    public boolean isDead() {
        return dead;
    }

    @Override
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    @Override
    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    @Override
    public void setHealth(double health) {
        this.health = Math.min(health, maxHealth);
    }

    @Override
    public void setMaxHealth(double maxHealth) {
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

    private boolean inf = false;

    @Override
    public void infect() {
        if(!dead && !inf) {
            setTeam(1);
            inf = true;
            moveable = false;
            loc = getPlayer().getLocation();
            getPlayer().sendTitle(Reference.TITLE_MAIN_INFECT, Reference.TITLE_SUB_INFECT, 0, 100, 20);
            new BukkitRunnable() {
                @Override
                public void run() {
                    moveable = true;
                    inf = false;
                }
            }.runTaskLater(GunWar.getPlugin(), 100);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!moveable) {
                        this.cancel();
                        return;
                    }
                    Location location = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(),
                            getPlayer().getLocation().getYaw(), getPlayer().getLocation().getPitch());
                    getPlayer().teleport(location);
                }
            }.runTaskTimer(GunWar.getPlugin(), 0, 1);
        }
    }

    @Override
    public void kill() {
        if(!dead && !inf) {
            moveable = false;
            dead = true;
            loc = getPlayer().getLocation();
            getPlayer().sendTitle(Reference.TITLE_MAIN_DIED_ZOMBIE, Reference.TITLE_SUB_INFECT, 0, 100, 20);
            new BukkitRunnable() {
                @Override
                public void run() {
                    moveable = true;
                    dead = false;
                }
            }.runTaskLater(GunWar.getPlugin(), 100);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!moveable) {
                        this.cancel();
                        return;
                    }
                    Location location = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(),
                            getPlayer().getLocation().getYaw(), getPlayer().getLocation().getPitch());
                    getPlayer().teleport(location);
                }
            }.runTaskTimer(GunWar.getPlugin(), 0, 1);
        }
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

            double px = xi + nx;
            double py = yi + ny;
            double pz = zi + nz;

            p.spawnParticle(particle, new Location(c.getWorld(), px, py, pz), 1, 0, 0, 0, 0);
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
            double z = startZ;
            double damageMin = gun.getAttackDamage() / 1.3;
            double hsdamageMin = gun.getHeadShotDamage() / 1.3;
            double currentDamage = gun.getAttackDamage();
            double currentHSDamage = gun.getHeadShotDamage();
            double separateDamage = currentDamage - damageMin / times;
            double separateHSDamage = currentHSDamage - hsdamageMin / times;
            while (z < times) {
                c(particle, x, y, z);
                for(Entity entity : getPlayer().getNearbyEntities(gun.getRange() + 1, gun.getRange() + 1, gun.getRange() + 1)) {
                    if(entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        if(livingEntity != getPlayer()) {
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
                                if(GunWar.getGame().getGameMode() == GwGameModes.NORMAL && ((GameModeNormal) GwGameModes.NORMAL).getMode() == GameModeNormal.Mode.TEAM) {
                                    if (livingEntity instanceof Player) {
                                        PlayerData data = GunWar.getGame().getPlayerData((Player) livingEntity);
                                        if (data != null && data.getTeam() != getTeam()) {
                                            return new HitEntity(livingEntity, headShot, headShot ? currentHSDamage : currentDamage,
                                                    getPlayer().getEyeLocation(), new Location(livingEntity.getWorld(), x, y, z));
                                        }
                                    }
                                }
                                return new HitEntity(livingEntity, headShot, headShot ? currentHSDamage : currentDamage,
                                        getPlayer().getEyeLocation(), new Location(livingEntity.getWorld(), x, y, z));
                            }
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
                z += separateZ;
                currentDamage -= separateDamage;
                currentHSDamage -= separateHSDamage;
            }
        }
        return null;
    }

    @Override
    public HitEntity drawParticleLine(Particle particle, double startX, double startY, double startZ,
                                      double far, Angle angle, double separate, GwGunItem gun) {
        double yaw = angle.getYaw();
        double pitch = angle.getPitch();
        double r = separate * Math.cos(pitch);
        double separateX = r * Math.sin(yaw);
        double separateY = separate * Math.sin(pitch);
        double separateZ = r * Math.cos(yaw);;
        double d = Math.sqrt(Math.pow(separateX, 2) + Math.pow(separateY, 2) + Math.pow(separateZ, 2)) * far;
        if(startZ < d) {
            double times = d / separate;
            double x = startX;
            double y = startY;
            double z = startZ;
            double damageMin = gun.getAttackDamage() / 1.1;
            double hsdamageMin = gun.getHeadShotDamage() / 1.1;
            double currentDamage = gun.getAttackDamage();
            double currentHSDamage = gun.getHeadShotDamage();
            double separateDamage = currentDamage - damageMin / times;
            double separateHSDamage = currentHSDamage - hsdamageMin / times;
            double px = 0;
            double py = 0;
            double pz = 0;
            while (z < times) {
                double twopi = 2 * Math.PI;
                double t = 1 * twopi;
                double division = twopi / 100;
                double radius = 2;

                Location c = player.getEyeLocation();
                Vector nv = c.getDirection().normalize();

                double nx = radius * nv.getX() + c.getX();
                double ny = radius * nv.getY() + c.getY();
                double nz = radius * nv.getZ() + c.getZ();

                Vector ya = a(nv, new Vector(0, 1, 0)).normalize();
                Vector xa = ya.getCrossProduct(nv).normalize();

                for (double theta = 0; theta < t; theta += division) {
                    double xi = xa.getX() * x + ya.getX() * y + nv.getX() * z;
                    double yi = xa.getY() * x + ya.getY() * y + nv.getY() * z;
                    double zi = xa.getZ() * x + ya.getZ() * y + nv.getZ() * z;

                    px = xi + nx;
                    py = yi + ny;
                    pz = zi + nz;

                    player.spawnParticle(particle, new Location(c.getWorld(), px, py, pz), 1, 0, 0, 0, 0);

                    for(Entity entity : getPlayer().getNearbyEntities(gun.getRange() + 1, gun.getRange() + 1, gun.getRange() + 1)) {
                        if(entity instanceof LivingEntity) {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            if(livingEntity != getPlayer()) {
                                double xmin = livingEntity.getLocation().getX() - (livingEntity.getWidth() / 2);
                                double xmax = livingEntity.getLocation().getX() + (livingEntity.getWidth() / 2);
                                double ymin = livingEntity.getLocation().getY();
                                double ymax = livingEntity.getLocation().getY() + livingEntity.getHeight();
                                double zmin = livingEntity.getLocation().getZ() - (livingEntity.getWidth() / 2);
                                double zmax = livingEntity.getLocation().getZ() + (livingEntity.getWidth() / 2);
                                boolean condition1 = xmin <= px && xmax >= px;
                                boolean condition2 = ymin <= py && ymax >= py;
                                boolean condition3 = zmin <= pz && zmax >= pz;
                                if (condition1 && condition2 && condition3) {
                                    double hxmin = livingEntity.getEyeLocation().getX() - (livingEntity.getEyeHeight() / 2);
                                    double hxmax = livingEntity.getEyeLocation().getX() + (livingEntity.getEyeHeight() / 2);
                                    double hymin = livingEntity.getEyeLocation().getY() - (livingEntity.getEyeHeight() / 2);
                                    double hymax = livingEntity.getEyeLocation().getY() + (livingEntity.getEyeHeight() / 2);
                                    double hzmin = livingEntity.getEyeLocation().getZ() - (livingEntity.getEyeHeight() / 2);
                                    double hzmax = livingEntity.getEyeLocation().getZ() + (livingEntity.getEyeHeight() / 2);
                                    boolean hcondition1 = hxmin <= px && hxmax >= px;
                                    boolean hcondition2 = hymin <= py && hymax >= py;
                                    boolean hcondition3 = hzmin <= pz && hzmax >= pz;
                                    boolean headShot = hcondition1 && hcondition2 && hcondition3;
                                    boolean passed = true;
                                    if(livingEntity instanceof Player) {
                                        if ((GunWar.getGame().getGameMode() == GwGameModes.NORMAL && ((GameModeNormal) GwGameModes.NORMAL).getMode() == GameModeNormal.Mode.TEAM) ||
                                                GunWar.getGame().getGameMode() == GwGameModes.CASTLE_SIEGE || GunWar.getGame().getGameMode() == GwGameModes.ZOMBIE_ESCAPE) {
                                            PlayerData data = GunWar.getGame().getPlayerData((Player) livingEntity);
                                            if(data != null) passed = data.getTeam() == getTeam();
                                        }
                                    }
                                    if(passed) return new HitEntity(livingEntity, headShot, headShot ? currentHSDamage : currentDamage,
                                            getPlayer().getEyeLocation(), new Location(livingEntity.getWorld(), px, py, pz));
                                }
                            }
                        }
                    }
                    Location loc = new Location(getPlayer().getWorld(), px, py, pz);

                    Block block = loc.getBlock();
                    if(block != null) {
                        if (block.getType() != Material.AIR && block.getType() != Material.LONG_GRASS && block.getType() != Material.DOUBLE_PLANT && block.getType() != Material.GRASS_PATH && block.getType() != Material.LAVA && block.getType() != Material.STATIONARY_LAVA && block.getType() != Material.WATER && block.getType() != Material.STATIONARY_WATER && block.getType() != Material.STRUCTURE_VOID) {
                            @SuppressWarnings("deprecation")
                            PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(2001,
                                    new BlockPosition(block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ()),
                                    block.getType().getId(), false);
                            List<Player> players = getPlayer().getWorld().getPlayers();
                            for(final Player p : players) {
                                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                            }
                            return new HitEntity(null, false, 0,
                                    getPlayer().getEyeLocation(), new Location(getPlayer().getWorld(), px, py, pz));
                        }
                        if(block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER) {
                            currentDamage /= 2;
                            currentHSDamage /= 2;
                        }
                        if(block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA) {
                            currentDamage /= 3;
                            currentHSDamage /= 3;
                        }
                    }
                }
                x += separateX;
                y += separateY;
                z += separateZ;
                currentDamage -= separateDamage;
                currentHSDamage -= separateHSDamage;
            }
            return new HitEntity(null, false, 0,
                    getPlayer().getEyeLocation(), new Location(getPlayer().getWorld(), px, py, pz));
        }
        return null;
    }

    @Override
    public void giveItem(GwItem item) {
        ItemStack i = item.getItem().clone();
        ((GunWarGame) GunWar.getGame()).addItemData(item instanceof GwGunItem ? new GunWarGunData((GwGunItem) item, i, getPlayer()) :
                new GunWarItemData(item, i, getPlayer()));
        if (GunWar.getGame().getItemData(i) != null) i = GunWar.getGame().getItemData(i).getItem();
        getPlayer().getInventory().addItem(i);
        getPlayer().updateInventory();
    }

    @Override
    public boolean isZoom() {
        return zoom;
    }

    @Override
    public void setZoom(boolean zoom, float zoomLevel) {
        this.zoom = zoom;
        this.zoomLevel = zoomLevel;
        if(zoom && zoomLevel > 0) {
            double d = 0.2 - (0.2 * zoomLevel / 100);
            player.setWalkSpeed((float) d);
        } else {
            player.setWalkSpeed(0.2F);
        }
    }
}
