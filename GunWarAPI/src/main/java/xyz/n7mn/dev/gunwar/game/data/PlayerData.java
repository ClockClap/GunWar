package xyz.n7mn.dev.gunwar.game.data;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.gunwar.util.PlayerWatcher;

public interface PlayerData extends EntityData {

    public Player getPlayer();

    public PlayerWatcher getWatcher();

    public float getHealth();

    public float getMaxHealth();

    public boolean isClickable();

    public void setClickable(boolean clickable);

    public void setHealth(float health);

    public void setMaxHealth(float maxHealth);

    public void drawParticleLine(Particle particle, double start, double far, double separate);

    public void drawParticleLine(Particle particle, double startX, double startY, double startZ, double far,
                                 double separateX, double separateY, double separateZ);

}
