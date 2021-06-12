package xyz.n7mn.dev.gunwar.mysql;

import org.bukkit.Bukkit;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.nanamiserver.permission.NanamiServerRoleData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public final class MySQLSettingBuilder {

    private GwMySqlSetting setting;

    private static GwMySqlSetting settingStatic;
    private static GwMySQLPlayerDataUpdater updaterStatic;

    public static MySQLSettingBuilder builder() {
        return new MySQLSettingBuilder();
    }

    public MySQLSettingBuilder withSetting(String host, int port, String database, String option, String username, String password) {
        setting = new GwMySqlSetting(host, port, database, option, username, password);
        return this;
    }

    public void build() {
        settingStatic = this.setting;
        updaterStatic = new GwMySQLPlayerDataUpdater(this.setting);
    }

    public static GwMySQLPlayerDataUpdater getUpdater() {
        return updaterStatic;
    }

    static GwMySqlSetting getSetting() {
        return settingStatic;
    }

}
