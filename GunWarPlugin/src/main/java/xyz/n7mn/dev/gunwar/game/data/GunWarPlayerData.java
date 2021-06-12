package xyz.n7mn.dev.gunwar.game.data;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import xyz.n7mn.dev.gunwar.mysql.GwMySQLPlayerData;
import xyz.n7mn.dev.gunwar.util.PlayerWatcher;

public class GunWarPlayerData extends GunWarEntityData implements PlayerData {

    private final Player player;
    private PlayerWatcher watcher;
    private float health;
    private float maxHealth;
    private boolean clickable;

    public GunWarPlayerData(Player player) {
        super(player);
        this.player = player;
        this.health = 100;
        this.maxHealth = 100;
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

}
