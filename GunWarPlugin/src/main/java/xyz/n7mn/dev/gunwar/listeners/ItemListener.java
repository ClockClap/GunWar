package xyz.n7mn.dev.gunwar.listeners;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.game.data.GunData;
import xyz.n7mn.dev.gunwar.game.data.ItemData;
import xyz.n7mn.dev.gunwar.game.data.PlayerData;
import xyz.n7mn.dev.gunwar.item.GwGunItem;

public class ItemListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        PlayerData data = GunWar.getGame().getPlayerData(e.getPlayer());
        if(data != null && data.isClickable()) {
            data.setClickable(false);
            new BukkitRunnable() {
                @Override
                public void run() {
                    data.setClickable(true);
                }
            }.runTaskLater(GunWar.getPlugin(), 1);
            ItemData itemData = GunWar.getGame().getItemData(e.getItem());
            if (itemData != null) {
                if (itemData instanceof GunData) {
                    GunData gunData = (GunData) itemData;
                    if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        if (gunData.getAmmo() <= 0) gunData.reload();
                        gunData.fire();
                    } else if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                        if(data.isZoom()) {
                            data.setZoom(false, 0);
                        } else {
                            data.setZoom(true, ((GwGunItem) gunData.getGwItem()).getZoomLevel());
                        }
                    }
                }
            }
        }
    }



    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        ItemData data = GunWar.getGame().getItemData(e.getItemDrop().getItemStack());
        if(data instanceof GunData) {
            e.setCancelled(true);
            ((GunData) data).reload();
        }
    }

    @EventHandler
    public void onSlotChange(PlayerItemHeldEvent e) {
        for(ItemStack i : e.getPlayer().getInventory().getContents()) {
            ItemData data = GunWar.getGame().getItemData(i);
            if(data instanceof GunData) {
                GunData gunData = (GunData) data;
                gunData.cancelReload();
                PlayerData pdata = GunWar.getGame().getPlayerData(e.getPlayer());
                if(pdata != null) pdata.setZoom(false, 0);
            }
        }
    }

}
