package xyz.n7mn.dev.gunwar.game.data;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.entity.HitEntity;
import xyz.n7mn.dev.gunwar.item.GwGunItem;
import xyz.n7mn.dev.gunwar.item.GwItem;

import java.util.*;

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
        updateName();
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
        if(canFire && getAmmo() > 0) {
            canFire = false;

            Random random = new Random();
            float accuracy = ((GwGunItem) getGwItem()).getAccuracy();
            if (getOwner().isSneaking()) {
                accuracy = ((GwGunItem) getGwItem()).getAccuracyOnSneak();
            }

            double separateX = random.nextDouble() * (2 / accuracy) - (1 / accuracy);
            double separateY = random.nextDouble() * (2 / accuracy) - (1 / accuracy);

            HitEntity hitEntity = GunWar.getGame().getPlayerData(getOwner()).drawParticleLine(
                    Particle.SMOKE_NORMAL, 0, 0, 0.25, ((GwGunItem) getGwItem()).getRange(),
                    separateX, separateY, 0.25, (GwGunItem) getGwItem());
            if(hitEntity != null) {
                if (hitEntity.getEntity() instanceof Player) {
                    PlayerData data = GunWar.getGame().getPlayerData((Player) hitEntity.getEntity());
                    hitEntity.getEntity().damage(0, getOwner());
                    data.setHealth(Math.max(0, data.getHealth() - hitEntity.getDamage()));
                } else {
                    double damage = hitEntity.getDamage() * 0.25;
                    hitEntity.getEntity().damage(damage, getOwner());
                }

                double subX = hitEntity.getHitLocation().getX() - hitEntity.getFrom().getX();
                double subY = hitEntity.getHitLocation().getY() - hitEntity.getFrom().getY();
                double subZ = hitEntity.getHitLocation().getZ() - hitEntity.getFrom().getZ();
                double far = Math.sqrt(Math.pow(subX, 2) +
                        Math.pow(subY, 2) +
                        Math.pow(subZ, 2));
                double d = ((GwGunItem) getGwItem()).getKnockBack() / far;
                Vector vector = new Vector(subX * d, subY * d, subZ * d);
                hitEntity.getEntity().setVelocity(vector);
            }

            ((GwGunItem) getGwItem()).onShoot(getOwner());

            setAmmo(getAmmo() - 1);
            updateName();
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
        if(fire != null && !fire.isCancelled()) fire.cancel();
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
        List<ItemStack> items = Arrays.asList(getOwner().getInventory().getContents());
        for(ItemStack i : items) {
            if(i == null) {
                continue;
            }
            if(i.hasItemMeta()) {
                if(i.getItemMeta().getLore().contains(getGwItem().getId())); {
                    getOwner().getInventory().setItem(items.indexOf(i), getItem());
                }
            }
        }
        getOwner().updateInventory();
    }

    @Override
    public void cancelReload() {
        if(reload != null && !reload.isCancelled()) {
            reload.cancel();
            canFire = true;
            updateName();
        }

    }
}
