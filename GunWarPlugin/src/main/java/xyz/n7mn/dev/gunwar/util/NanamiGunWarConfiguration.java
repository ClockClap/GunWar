package xyz.n7mn.dev.gunwar.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;

public class NanamiGunWarConfiguration implements GunWarConfiguration {

    private Plugin plugin;
    private FileConfiguration config;
    private File configFile;

    public NanamiGunWarConfiguration(Plugin plugin) {
        this.plugin = plugin;
    }

    public void init() {
        try {
            String dataFolder = "plugins/" + plugin.getName();

            File f = new File(dataFolder);
            if (!f.exists()) {
                f.mkdir();
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
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

    @Override
    public File getConfigFile() {
        return configFile;
    }
}
