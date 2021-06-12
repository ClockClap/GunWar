package xyz.n7mn.dev.gunwar.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class GwGunBase extends GwWeaponBase implements GwGunItem {

    private int ammo;
    private double range;
    private float recoil;
    private float recoilSneaking;
    private long reload;
    private long fire;
    private float zoom;
    private float damageHeadShot;
    private float accuracy;
    private float accuracySneaking;
    private float knockback;
    private GwGunType gunType;

    protected GwGunBase() {
        this(0, Material.STONE, "", "", "",
                new ArrayList<>(), 0F, 0F, 0, 0,
                0F, 0F, 0L, 0L, 0F, 0F, 0F, 0F,  GwGunType.ASSAULT_RIFLE);
    }

    protected GwGunBase(int index, Material type, String name, String displayName, String id, List<String> description, float damage, float damageHeadShot,
                        int ammo, double range, float recoil, float recoilSneaking, long reload, long fire, float zoom, float accuracy,
                        float accuracySneaking, float knockback, GwGunType gunType) {
        super(index, type, name, displayName, id, description, damage);
        this.ammo = ammo;
        this.range = range;
        this.recoil = recoil;
        this.recoilSneaking = recoilSneaking;
        this.reload = reload;
        this.fire = fire;
        this.zoom = zoom;
        this.damageHeadShot = damageHeadShot;
        this.accuracy = accuracy;
        this.accuracySneaking = accuracySneaking;
        this.knockback = knockback;
        this.gunType = gunType;
    }

    public int getAmmo() {
        return ammo;
    }

    public double getRange() {
        return range;
    }

    public float getRecoil() {
        return recoil;
    }

    public float getRecoilOnSneak() {
        return recoilSneaking;
    }

    public long getReload() {
        return reload;
    }

    public long getFire() {
        return fire;
    }

    public float getZoomLevel() {
        return zoom;
    }

    public float getHeadShotDamage() {
        return damageHeadShot;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public float getAccuracyOnSneak() {
        return accuracySneaking;
    }

    public float getKnockBack() {
        return knockback;
    }

    public GwGunType getGunType() {
        return gunType;
    }

    protected void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    protected void setRange(float range) {
        this.range = range;
    }

    protected void setRecoil(float recoil) {
        this.recoil = recoil;
    }

    protected void setRecoilOnSneak(float recoilSneaking) {
        this.recoilSneaking = recoilSneaking;
    }

    protected void setReload(long reload) {
        this.reload = reload;
    }

    protected void setFire(long fire) {
        this.fire = fire;
    }

    protected void setZoomLevel(float zoom) {
        this.zoom = zoom;
    }

    protected void setHeadShotDamage(float damageHeadShot) {
        this.damageHeadShot = damageHeadShot;
    }

    protected void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    protected void setAccuracyOnSneak(float accuracySneaking) {
        this.accuracySneaking = accuracySneaking;
    }

    protected void setKnockBack(float knockback) {
        this.knockback = knockback;
    }

    protected void setGunType(GwGunType gunType) {
        this.gunType = gunType;
    }

    protected void setDescription(List<String> description) {
        this.description = description;
        a();
    }

    protected void setId(String id) {
        this.id = id;
        a();
    }

    private void a() {
        ItemMeta meta = getItem().getItemMeta();
        List<String> lore = new ArrayList<>(getDescription());
        String guntype = "";
        switch (getGunType()) {
            case ASSAULT_RIFLE:
                guntype = "Assault Rifle";
                break;
            case SNIPER_RIFLE:
                guntype = "Sniper Rifle";
                break;
            case SUBMACHINE_GUN:
                guntype = "Sub-Machine Gun";
                break;
            case SHOTGUN:
                guntype = "Shotgun";
                break;
            case HAND_GUN:
                guntype = "Hand Gun";
                break;
        }
        lore.add("");
        lore.add(ChatColor.GRAY + "攻撃力 (ヘッドショット): " + ChatColor.GOLD + getAttackDamage()
                + ChatColor.GRAY + "(" + ChatColor.GOLD + getHeadShotDamage() + ChatColor.GRAY + ")");
        lore.add(ChatColor.GRAY + "射程: " + ChatColor.GOLD + getRange());
        lore.add(ChatColor.GRAY + "連射: " + ChatColor.GOLD + getFire());
        lore.add(ChatColor.GRAY + "精度 (スニーク時): " + ChatColor.GOLD + getAccuracy()
                + ChatColor.GRAY + "(" + ChatColor.GOLD + getAccuracyOnSneak() + ChatColor.GRAY + ")");
        lore.add(ChatColor.GRAY + "リロード時間: " + ChatColor.GOLD + getReload());
        lore.add(ChatColor.GRAY + "ノックバック: " + ChatColor.GOLD + getKnockBack());
        lore.add(ChatColor.GRAY + "反動 (スニーク時): " + ChatColor.GOLD + getRecoil()
                + ChatColor.GRAY + "(" + ChatColor.GOLD + getRecoilOnSneak() + ChatColor.GRAY + ")");
        lore.add(ChatColor.GRAY + "装填数: " + ChatColor.GOLD + getAmmo());
        lore.add("");
        lore.add(ChatColor.YELLOW + guntype);
        lore.add(ChatColor.DARK_GRAY + getId());
        meta.setLore(lore);
        getItem().setItemMeta(meta);
    }
}
