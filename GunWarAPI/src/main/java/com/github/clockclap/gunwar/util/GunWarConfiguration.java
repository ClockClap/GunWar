package com.github.clockclap.gunwar.util;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public interface GunWarConfiguration {

    public FileConfiguration getConfig();

    public FileConfiguration getPermissionSetting();

    public XmlConfiguration getDetailConfig();

    public FileConfiguration getLang();

    public File getConfigFile();

    public File getPermissionSettingFile();

    public File getDetailConfigFile();

    public File getLangFile();

    public boolean isNanamiNetwork();

    public void setNanamiNetwork(boolean nanamiNetwork);

}
