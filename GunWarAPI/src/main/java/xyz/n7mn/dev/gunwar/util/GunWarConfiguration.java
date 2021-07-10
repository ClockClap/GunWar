package xyz.n7mn.dev.gunwar.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.w3c.dom.Document;

import java.io.File;

public interface GunWarConfiguration {

    public FileConfiguration getConfig();

    public FileConfiguration getPermissionSetting();

    public Document getDetailConfig();

    public FileConfiguration getLang();

    public File getConfigFile();

    public File getPermissionSettingFile();

    public File getDetailConfigFile();

    public File getLangFile();

    public boolean isNanamiNetwork();

    public void setNanamiNetwork(boolean nanamiNetwork);

}
