package xyz.n7mn.dev.gunwar.game.data;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.gunwar.entity.HitEntity;
import xyz.n7mn.dev.gunwar.item.GwGunItem;
import xyz.n7mn.dev.gunwar.item.GwItem;
import xyz.n7mn.dev.gunwar.util.PlayerWatcher;

public interface PlayerData extends EntityData {

    public Player getPlayer();

    public PlayerWatcher getWatcher();

    public float getHealth();

    public float getMaxHealth();

    public int getTeam();

    public boolean isSpectator();

    public boolean isClickable();

    public void setClickable(boolean clickable);

    public void setHealth(float health);

    public void setMaxHealth(float maxHealth);

    public void setTeam(int team);

    public void setSpectator(boolean spectator);

    public void drawParticleLine(Particle particle, double start, double far, double separate);

    public void drawParticleLine(Particle particle, double startX, double startY, double startZ, double far,
                                 double separateX, double separateY, double separateZ);

    public HitEntity drawParticleLine(Particle particle, double startX, double startY, double startZ, double far,
                                      double separateX, double separateY, double separateZ, GwGunItem gun);

    public void giveItem(GwItem item);

}
