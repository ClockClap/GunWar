package xyz.n7mn.dev.gunwar.game.data;

import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;

public interface ProjectileData extends EntityData {

    public Projectile getProjectile();

    public ProjectileSource getShooter();

}
