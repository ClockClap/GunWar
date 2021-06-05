package xyz.n7mn.dev.gunwar.util;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public interface GunWarConfiguration {

    public FileConfiguration getConfig();

    public File getConfigFile();

}
