package xyz.n7mn.dev.gunwar.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.gunwar.mysql.MySQLSettingBuilder;

import java.io.*;

public class NanamiGunWarConfiguration implements GunWarConfiguration {

    private Plugin plugin;
    private File dataFolder;
    private FileConfiguration config;
    private FileConfiguration permissionSetting;
    private File configFile;
    private File permissionFile;
    private boolean nanamiNetwork;

    public NanamiGunWarConfiguration(Plugin plugin) {
        this.plugin = plugin;
        this.nanamiNetwork = false;
    }

    public void init() {
        try {
            String dataFolder = "plugins/" + plugin.getName();

            File f = new File(dataFolder);
            if (!f.exists()) {
                f.mkdir();
            }
            this.dataFolder = f;
            File f_ = new File(dataFolder + "/players");
            if(!f_.exists()) {
                f_.mkdir();
            }
            File f__ = new File(dataFolder + "/permission");
            if(!f__.exists()) {
                f__.mkdir();
            }

            configFile = new File(dataFolder + "/config.yml");
            boolean b0 = true;
            if (!configFile.exists()) {
                configFile.createNewFile();
                b0 = false;
            }
            if (!b0) {
                try {
                    InputStream inputStream = plugin.getResource("config.yml");
                    File file = configFile;
                    OutputStream out = new FileOutputStream(file);
                    byte[] buf = new byte['?'];
                    int length;
                    while ((length = inputStream.read(buf)) > 0) {
                        out.write(buf, 0, length);
                    }
                    out.close();
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            config = YamlConfiguration.loadConfiguration(configFile);

            MySQLSettingBuilder.builder().withSetting(config.getString("mysql.host", "localhost"),
                    config.getInt("mysql.port", 3306),
                    config.getString("mysql.database", ""),
                    config.getString("mysql.option", "?allowPublicKeyRetrieval=true&useSSL=false"),
                    config.getString("mysql.username", ""),
                    config.getString("mysql.password", "")).build();

            String ppath = config.getString("permission-setting", dataFolder + "/permission/default.yml");
            if(ppath.startsWith(":nanami-network:>")) {
                if(MySQLSettingBuilder.isEnabled()) nanamiNetwork = true;
                ppath = ppath.substring(17);
            } else {
                nanamiNetwork = false;
            }

            permissionFile = new File(ppath);
            boolean b1 = true;
            if (!permissionFile.exists()) {
                permissionFile.createNewFile();
                b1 = false;
            }
            if (!b1) {
                try {
                    InputStream inputStream = plugin.getResource("permission/default.yml");
                    File file = permissionFile;
                    OutputStream out = new FileOutputStream(file);
                    byte[] buf = new byte['?'];
                    int length;
                    while ((length = inputStream.read(buf)) > 0) {
                        out.write(buf, 0, length);
                    }
                    out.close();
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            permissionSetting = YamlConfiguration.loadConfiguration(permissionFile);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

    @Override
    public FileConfiguration getPermissionSetting() {
        return permissionSetting;
    }

    @Override
    public File getConfigFile() {
        return configFile;
    }

    @Override
    public File getPermissionSettingFile() {
        return permissionFile;
    }

    public void setPermissionFile(File file) {
        permissionFile = file;
    }

    @Override
    public boolean isNanamiNetwork() {
        return nanamiNetwork;
    }

    @Override
    public void setNanamiNetwork(boolean nanamiNetwork) {
        this.nanamiNetwork = nanamiNetwork;
    }

    public File getDataFolder() {
        return dataFolder;
    }
}
