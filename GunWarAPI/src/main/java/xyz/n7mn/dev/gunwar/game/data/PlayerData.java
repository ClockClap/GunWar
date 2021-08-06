package xyz.n7mn.dev.gunwar.game.data;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.gunwar.entity.HitEntity;
import xyz.n7mn.dev.gunwar.item.GwGunItem;
import xyz.n7mn.dev.gunwar.item.GwItem;
import xyz.n7mn.dev.gunwar.item.GwKnifeItem;
import xyz.n7mn.dev.gunwar.util.Angle;
import xyz.n7mn.dev.gunwar.util.PlayerWatcher;

import java.util.Map;

public interface PlayerData extends EntityData {

    public Player getPlayer();

    public PlayerWatcher getWatcher();

    public double getHealth();

    public double getMaxHealth();

    public int getTeam();

    public boolean isSpectator();

    public boolean isClickable();

    public boolean isDead();

    public boolean isZoom();

    public void setZoom(boolean zoom, float zoomLevel);

    public void setDead(boolean dead);

    public void setClickable(boolean clickable);

    public void setHealth(double health);

    public void setMaxHealth(double maxHealth);

    public void setTeam(int team);

    public void setSpectator(boolean spectator);

    public void infect();

    public void kill();

    public void drawParticleLine(Particle particle, double start, double far, double separate);

    public void drawParticleLine(Particle particle, double startX, double startY, double startZ, double far,
                                 double separateX, double separateY, double separateZ);

    public HitEntity drawParticleLine(Particle particle, double startX, double startY, double startZ, double far,
                                      double separateX, double separateY, double separateZ, GwGunItem gun);

    public HitEntity drawParticleLine(Particle particle, double startX, double startY,
                                      double startZ, double far, Angle angle, double separate, GwGunItem gun, boolean aim);

    public HitEntity drawParticleLine(Particle particle, double startX, double startY, double startZ, double far, double separate, GwKnifeItem gun);

    public void giveItem(GwItem item);

    public class Nanami extends EntityData.Nanami {
        public String getOldName() {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public void setName(String name) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public void setName(Player from, String name) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public void setNameByMap(Map<Player, String> map) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public void resetName() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void resetName(Player from) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public boolean canSee(LivingEntity from) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public String getName(Player player) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public void show(Player player) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public void hide(Player player) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public void updateName() {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

        public void updateName(Player player) {
            throw new UnsupportedOperationException( "Not supported yet." );
        }
    }

    public void sendMessage(String message);

    public void sendMessage(BaseComponent component);

    public void sendMessage(BaseComponent... components);

    public void sendMessage(String[] messages);

    public Nanami nanami();

}
