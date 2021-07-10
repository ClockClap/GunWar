package xyz.n7mn.dev.gunwar.game.data;

import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.item.GwGunItem;
import xyz.n7mn.dev.gunwar.item.GwItem;
import xyz.n7mn.dev.gunwar.mysql.GwMySQL;
import xyz.n7mn.dev.gunwar.mysql.GwMySQLDataPath;
import xyz.n7mn.dev.gunwar.util.NanamiGunWarConfiguration;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
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
        File f = file;
        try {
            GwMySQL.insertPlayerData(getUniqueId(), f.getPath());
            f = new File(GwMySQL.getPath(getUniqueId()));
            if(!f.exists()) {
                f = file;
                GwMySQL.updatePlayerData(getUniqueId(), f.getPath());
            }
            GwMySQLDataPath.insert(getUniqueId(), f.getPath());
            File f_ = new File(((NanamiGunWarConfiguration) GunWar.getConfig()).getDataFolder().getPath() + "/cache/" + getUniqueId().toString());
            if(!f_.exists()) {
                f_.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(f_);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(GwMySQLDataPath.get(getUniqueId()));
            oos.flush();
            oos.close();
            fos.close();
        } catch (SQLException ex) {
            File f_ = new File(((NanamiGunWarConfiguration) GunWar.getConfig()).getDataFolder().getPath() + "/cache/" + getUniqueId().toString());
            if(!f_.exists()) {
                f_.createNewFile();
                GwMySQLDataPath.insert(getUniqueId(), ((NanamiGunWarConfiguration) GunWar.getConfig()).getDataFolder().getPath()
                        + "/players/" + getUniqueId().toString());
                FileOutputStream fos = new FileOutputStream(f_);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(GwMySQLDataPath.get(getUniqueId()));
                oos.flush();
                oos.close();
                fos.close();
            }
            try {
                FileInputStream fis = new FileInputStream(f_);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object obj_ = ois.readObject();
                ois.close();
                fis.close();
                String str = "";
                try {
                    Class<?> clazz = obj.getClass();
                    Field field = clazz.getField("path");
                    field.setAccessible(true);
                    str = (String) field.get(obj_);
                } catch(Throwable throwable) {
                    ex.printStackTrace();
                }
                if(!str.isEmpty() && !str.equalsIgnoreCase(f.getPath())) {
                    f = new File(str);
                }
            } catch (ClassNotFoundException ignored) { }
        }
        if(!f.exists()) {
            if(!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            f.createNewFile();
        }
        FileOutputStream output = new FileOutputStream(f);
        ObjectOutputStream objectOutput = new ObjectOutputStream(output);
        objectOutput.writeObject(obj);
        objectOutput.flush();
        objectOutput.close();
        output.close();
        if(!file.getPath().equalsIgnoreCase(f.getPath())) {
            FileOutputStream output_ = new FileOutputStream(file);
            ObjectOutputStream objectOutput_ = new ObjectOutputStream(output_);
            objectOutput_.writeObject(obj);
            objectOutput_.flush();
            objectOutput_.close();
            output_.close();
        }
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void load(File file) throws IOException, ClassNotFoundException {
        File f = file;
        try {
            GwMySQL.insertPlayerData(getUniqueId(), f.getPath());
            f = new File(GwMySQL.getPath(getUniqueId()));
            if(!f.exists()) {
                f = file;
                GwMySQL.updatePlayerData(getUniqueId(), f.getPath());
            }
        } catch (SQLException ignored) {
        }
        if(f.exists()) {
            FileInputStream input = new FileInputStream(f);
            ObjectInputStream objectInput = new ObjectInputStream(input);
            Object object = objectInput.readObject();
            Class<?> clazz = object.getClass();
            boolean passed = false;
            for(Field fi : clazz.getFields()) {
                try {
                    if (fi.getName().equalsIgnoreCase("uniqueId")) {
                        fi.setAccessible(true);
                        Object o = fi.get(object);
                        if (o instanceof UUID && o == getUniqueId()) {
                            passed = true;
                        }
                    }
                    if (passed) {
                        if(fi.getName().equalsIgnoreCase("coins")) {
                            fi.setAccessible(true);
                            Object o = fi.get(object);
                            if (o instanceof Integer) {
                                this.coins = (Integer) o;
                            }
                        }
                        if(fi.getName().equalsIgnoreCase("items")) {
                            fi.setAccessible(true);
                            Object o = fi.get(object);
                            if(o instanceof List) {
                                try {
                                    this.items = (List<GwItem>) o;
                                } catch(Throwable ignored) { }
                            }
                        }
                        if(fi.getName().equalsIgnoreCase("gifts")) {
                            fi.setAccessible(true);
                            Object o = fi.get(object);
                            if(o instanceof List) {
                                try {
                                    this.gifts = (List<GwItem>) o;
                                } catch(Throwable ignored) { }
                            }
                        }
                        if(fi.getName().equalsIgnoreCase("playCount")) {
                            fi.setAccessible(true);
                            Object o = fi.get(object);
                            if(o instanceof Map) {
                                try {
                                    this.playCount = (Map<GwGunItem, Integer>) o;
                                } catch(Throwable ignored) { }
                            }
                        }
                        if(fi.getName().equalsIgnoreCase("killCount")) {
                            fi.setAccessible(true);
                            Object o = fi.get(object);
                            if(o instanceof Map) {
                                try {
                                    this.killCount = (Map<GwGunItem, Integer>) o;
                                } catch(Throwable ignored) { }
                            }
                        }
                        if(fi.getName().equalsIgnoreCase("deathCount")) {
                            fi.setAccessible(true);
                            Object o = fi.get(object);
                            if(o instanceof Integer) {
                                this.deathCount = (Integer) o;
                            }
                        }
                        if(fi.getName().equalsIgnoreCase("infectCount")) {
                            fi.setAccessible(true);
                            Object o = fi.get(object);
                            if(o instanceof Integer) {
                                this.infectCount = (Integer) o;
                            }
                        }
                    }
                } catch (IllegalAccessException ignored) {
                }
            }
            objectInput.close();
            input.close();
        }
    }
}
