package xyz.n7mn.dev.gunwar.game.data;

import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.item.GwGunItem;
import xyz.n7mn.dev.gunwar.item.GwItem;
import xyz.n7mn.dev.gunwar.util.NanamiGunWarConfiguration;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        return Math.max(0, infectCount);
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

    @SuppressWarnings({"unchecked"})
    @Override
    public void load(File file) throws IOException, ClassNotFoundException {
        if(file.exists()) {
            FileInputStream input = new FileInputStream(file);
            ObjectInputStream objectInput = new ObjectInputStream(input);
            Object object = objectInput.readObject();
            Class<?> clazz = object.getClass();
            boolean passed = false;
            for(Method m : clazz.getMethods()) {
                try {
                    if (m.getName().equalsIgnoreCase("getUniqueId")) {
                        Object o = m.invoke(object);
                        if (o instanceof UUID && o == getUniqueId()) {
                            passed = true;
                        }
                    }
                    if (passed) {
                        if(m.getName().equalsIgnoreCase("getCoins")) {
                            Object o = m.invoke(object);
                            if (o instanceof Integer) {
                                this.coins = (Integer) o;
                            }
                        }
                        if(m.getName().equalsIgnoreCase("getItemInProcessions")) {
                            Object o = m.invoke(object);
                            if(o instanceof List) {
                                try {
                                    this.items = (List<GwItem>) o;
                                } catch(Throwable ignored) { }
                            }
                        }
                        if(m.getName().equalsIgnoreCase("getGifts")) {
                            Object o = m.invoke(object);
                            if(o instanceof List) {
                                try {
                                    this.gifts = (List<GwItem>) o;
                                } catch(Throwable ignored) { }
                            }
                        }
                        if(m.getName().equalsIgnoreCase("getPlayCount")) {
                            Object o = m.invoke(object);
                            if(o instanceof Map) {
                                try {
                                    this.playCount = (Map<GwGunItem, Integer>) o;
                                } catch(Throwable ignored) { }
                            }
                        }
                        if(m.getName().equalsIgnoreCase("getKillCount")) {
                            Object o = m.invoke(object);
                            if(o instanceof Map) {
                                try {
                                    this.killCount = (Map<GwGunItem, Integer>) o;
                                } catch(Throwable ignored) { }
                            }
                        }
                        if(m.getName().equalsIgnoreCase("getDeathCount")) {
                            Object o = m.invoke(object);
                            if(o instanceof Integer) {
                                this.deathCount = (Integer) o;
                            }
                        }
                        if(m.getName().equalsIgnoreCase("getInfectCount")) {
                            Object o = m.invoke(object);
                            if(o instanceof Integer) {
                                this.infectCount = (Integer) o;
                            }
                        }
                    }
                } catch (InvocationTargetException | IllegalAccessException ignored) {
                }
            }
            objectInput.close();
        }
    }
}
