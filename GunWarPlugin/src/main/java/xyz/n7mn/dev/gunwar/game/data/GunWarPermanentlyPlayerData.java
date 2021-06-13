package xyz.n7mn.dev.gunwar.game.data;

import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.item.GwGunItem;
import xyz.n7mn.dev.gunwar.item.GwItem;
import xyz.n7mn.dev.gunwar.util.NanamiGunWarConfiguration;

import java.io.*;
import java.util.*;

public class GunWarPermanentlyPlayerData implements PermanentlyPlayerData, Serializable {

    private final UUID uniqueId;
    private File dataFile;
    private int coins;
    private List<GwItem> items;
    private List<GwItem> gifts;
    private Map<GwGunItem, Integer> playCount;
    private Map<GwGunItem, Integer> killCount;
    private int deathCount;
    private int infectCount;

    public GunWarPermanentlyPlayerData(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.coins = 0;
        this.items = new ArrayList<>();
        this.gifts = new ArrayList<>();
        this.playCount = new HashMap<>();
        this.killCount = new HashMap<>();
        this.deathCount = 0;
        this.infectCount = 0;
        this.dataFile = new File(((NanamiGunWarConfiguration) GunWar.getConfig()).getDataFolder().getPath() + "/players/" + uniqueId);
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public int getCoins() {
        return coins;
    }

    @Override
    public List<GwItem> getItemInProcessions() {
        return items;
    }

    @Override
    public List<GwItem> getGifts() {
        return gifts;
    }

    @Override
    public Map<GwGunItem, Integer> getPlayCount() {
        return playCount;
    }

    @Override
    public Map<GwGunItem, Integer> getKillCount() {
        return killCount;
    }

    @Override
    public int getDeathCount() {
        return Math.max(0, deathCount);
    }

    @Override
    public int getInfectCount() {
        return Math.max(0, deathCount);
    }

    @Override
    public void setCoins(int coins) {
        this.coins = coins;
    }

    @Override
    public void setDeathCount(int deathCount) {
        this.deathCount = deathCount;
    }

    @Override
    public void setInfectCount(int infectCount) {
        this.infectCount = infectCount;
    }

    @Override
    public File getDefaultDataFile() {
        return dataFile;
    }

    @Override
    public void save(File file) throws IOException {
        save(this, file);
    }

    private void save(Object obj, File file) throws IOException {
        if(!file.exists()) {
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        FileOutputStream output = new FileOutputStream(file);
        ObjectOutputStream objectOutput = new ObjectOutputStream(output);
        objectOutput.writeObject(obj);
        objectOutput.flush();
        objectOutput.close();
    }

    @Override
    public void load(File file) throws IOException, ClassNotFoundException {
        if(file.exists()) {
            FileInputStream input = new FileInputStream(file);
            ObjectInputStream objectInput = new ObjectInputStream(input);
            Object object = objectInput.readObject();
            if (object instanceof PermanentlyPlayerData) {
                PermanentlyPlayerData data = (PermanentlyPlayerData) object;
                if (data.getUniqueId() == getUniqueId()) {
                    this.coins = data.getCoins();
                    if(data.getItemInProcessions() != null) this.items = data.getItemInProcessions();
                    if(data.getGifts() != null) this.gifts = data.getGifts();
                    if(data.getPlayCount() != null) this.playCount = data.getPlayCount();
                    if(data.getKillCount() != null) this.killCount = data.getKillCount();
                    this.deathCount = data.getDeathCount();
                    this.infectCount = data.getInfectCount();
                }
            }
            objectInput.close();
        }
    }
}
