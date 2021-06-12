package xyz.n7mn.dev.gunwar.game.data;

import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;

public class GunWarProjectileData extends GunWarEntityData implements ProjectileData {

    private final Projectile projectile;

    public GunWarProjectileData(Projectile projectile) {
        super(projectile);
        this.projectile = projectile;
    }

    @Override
    public Projectile getProjectile() {
        return projectile;
    }

    @Override
    public ProjectileSource getShooter() {
        return projectile.getShooter();
    }
}
