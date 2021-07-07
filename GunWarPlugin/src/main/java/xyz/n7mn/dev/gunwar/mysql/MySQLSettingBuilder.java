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
    private static boolean enabled = false;

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

        try {
            boolean found = false;
            Enumeration<Driver> drivers = DriverManager.getDrivers();

            while (drivers.hasMoreElements()){
                Driver driver = drivers.nextElement();
                if (driver.equals(new com.mysql.cj.jdbc.Driver())){
                    found = true;
                    break;
                }
            }

            if (!found){
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Connection con = DriverManager.getConnection("" +
                            "jdbc:mysql://" +
                            MySQLSettingBuilder.getSetting().getHost()+ ":" +
                            MySQLSettingBuilder.getSetting().getPort() + "/" +
                            MySQLSettingBuilder.getSetting().getDatabase() +
                            MySQLSettingBuilder.getSetting().getOption(),
                    MySQLSettingBuilder.getSetting().getUsername(),
                    MySQLSettingBuilder.getSetting().getPassword());
            con.close();
            enabled = true;
        } catch(SQLException ex) {
            enabled = false;
            ex.printStackTrace();
        }
    }

    public static GwMySQLPlayerDataUpdater getUpdater() {
        return updaterStatic;
    }

    static GwMySqlSetting getSetting() {
        return settingStatic;
    }

    public static boolean isEnabled() {
        return enabled;
    }

}
