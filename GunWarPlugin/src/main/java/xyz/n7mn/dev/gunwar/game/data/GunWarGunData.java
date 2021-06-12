package xyz.n7mn.dev.gunwar.game.data;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.item.GwGunItem;
import xyz.n7mn.dev.gunwar.item.GwItem;

import java.util.Random;
import java.util.UUID;

public class GunWarGunData extends GunWarItemData implements GunData {

    private boolean reloading;
    private boolean canFire;
    private int ammo;
    private BukkitRunnable fire;
    private BukkitRunnable reload;

    public GunWarGunData(GwGunItem gwitem, ItemStack item, Player owner) {
        super(gwitem, item, owner);
        reloading = false;
        canFire = true;
        ammo = gwitem.getAmmo();
    }

    @Override
    public boolean isReloading() {
        return reloading;
    }

    @Override
    public boolean canFire() {
        return canFire;
    }

    @Override
    public int getAmmo() {
        return ammo;
    }

    @Override
    public void setAmmo(int ammo) {
        this.ammo = Math.max(0, ammo);
        if(this.ammo <= 0) canFire = false;
        if(this.ammo > 0) canFire = true;
    }

    @Override
    public void fire() {
        if(canFire) {
            canFire = false;
            Random random = new Random();
            float accuracy = ((GwGunItem) getGwItem()).getAccuracy();
            if (getOwner().isSneaking()) {
                accuracy = ((GwGunItem) getGwItem()).getAccuracyOnSneak();
            }
            double separateX = random.nextDouble() * (2 / accuracy) - (1 / accuracy);
            double separateY = random.nextDouble() * (2 / accuracy) - (1 / accuracy);
            GunWar.getGame().getPlayerData(getOwner()).drawParticleLine(Particle.SMOKE_NORMAL, 0, 0, 0.2,
                    ((GwGunItem) getGwItem()).getRange(), separateX, separateY, 0.25);
            ((GwGunItem) getGwItem()).onShoot(getOwner());
            setAmmo(getAmmo() - 1);
            if(getAmmo() <= 0) {
                reload();
                return;
            }
            fire = new BukkitRunnable() {
                @Override
                public void run() {
                    canFire = true;
                }
            };
            fire.runTaskLater(GunWar.getPlugin(), ((GwGunItem) getGwItem()).getFire());
        }
    }

    @Override
    public void cancelFireCooldown() {
        if(fire != null) fire.cancel();
    }

    @Override
    public void reload() {
        if(!isReloading()) {
            reloading = true;
            canFire = false;
            updateName();
            reload = new BukkitRunnable() {
                @Override
                public void run() {
                    reloading = false;
                    setAmmo(((GwGunItem) getGwItem()).getAmmo());
                    updateName();
                    canFire = true;
                }
            };
            reload.runTaskLater(GunWar.getPlugin(), ((GwGunItem) getGwItem()).getReload());
        }
    }

    public void updateName() {
        ItemMeta meta = getItem().getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + getGwItem().getDisplayName() + " " + (reloading ? "▫" : "▪") + " " + (reloading ? ChatColor.DARK_GRAY : ChatColor.WHITE) + "«" + ammo + "»");
        getItem().setItemMeta(meta);
    }

    @Override
    public void cancelReload() {
        if(reload != null) {
            reload.cancel();
            canFire = true;
            updateName();
        }

    }
}
