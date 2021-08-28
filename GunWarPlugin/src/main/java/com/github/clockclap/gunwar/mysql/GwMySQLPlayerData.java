package com.github.clockclap.gunwar.mysql;

import com.github.clockclap.gunwar.nanamiserver.permission.NanamiServerRoleData;
import com.github.clockclap.gunwar.game.data.PlayerData;

import java.sql.*;
import java.util.*;

public final class GwMySQLPlayerData {

    private static Map<PlayerData, GwMySQLPlayerData> dataMap = new HashMap<>();

    public static GwMySQLPlayerData getPlayerData(PlayerData data) {
        return GwMySQLPlayerData.dataMap.get(data);
    }

    @NanamiServer
    public static String getRoleNameById(int id) throws SQLException {
        String result;
        boolean found = false;
        Enumeration<Driver> drivers = DriverManager.getDrivers();

        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver.equals(new com.mysql.cj.jdbc.Driver())) {
                found = true;
                break;
            }
        }

        if (!found) {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        }

        Connection con = DriverManager.getConnection("jdbc:mysql://" +
                        MySQLSettingBuilder.getSetting().getHost() + ":" +
                        MySQLSettingBuilder.getSetting().getPort() + "/" +
                        MySQLSettingBuilder.getSetting().getDatabase() +
                        MySQLSettingBuilder.getSetting().getOption(),
                MySQLSettingBuilder.getSetting().getUsername(),
                MySQLSettingBuilder.getSetting().getPassword()
        );
        con.setAutoCommit(true);

        PreparedStatement statement = con.prepareStatement("SELECT * FROM RoleRankList");
        ResultSet set = statement.executeQuery();

        StringBuffer sb = new StringBuffer();
        while (set.next()) {
            if (set.getInt("Rank") == id) {
                sb.append(set.getString("Name"));
                sb.append(",");
            }
        }
        result = sb.toString();

        set.close();
        statement.close();
        con.close();

        return result;
    }

    @NanamiServer
    public static List<NanamiServerRoleData> getList() throws SQLException {
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
        } catch (SQLException e){
            e.printStackTrace();
        }

        List<NanamiServerRoleData> list = new ArrayList<>();
        Connection con = DriverManager.getConnection("" +
                "jdbc:mysql://" +
                        MySQLSettingBuilder.getSetting().getHost()+ ":" +
                        MySQLSettingBuilder.getSetting().getPort() + "/" +
                        MySQLSettingBuilder.getSetting().getDatabase() +
                        MySQLSettingBuilder.getSetting().getOption(),
                MySQLSettingBuilder.getSetting().getUsername(),
                MySQLSettingBuilder.getSetting().getPassword());

        PreparedStatement statement = con.prepareStatement("SELECT * FROM RoleRankList");
        ResultSet set = statement.executeQuery();

        while (set.next()){
            list.add(new NanamiServerRoleData(
                    UUID.fromString(set.getString("UUID")),
                    set.getString("DiscordRoleID"),
                    set.getString("Name"),
                    set.getInt("Rank")
            ));
        }
        set.close();
        statement.close();
        con.close();

        return list;
    }

    private PlayerData data;
    private int coins;

    public GwMySQLPlayerData(PlayerData data) {
        this.data = data;
        this.coins = 0;
    }

    public UUID getUniqueId() {
        return this.data.getUniqueId();
    }

    public int getRank() {
        try {
            List<NanamiServerRoleData> data = getList();
            for (NanamiServerRoleData d : data) {
                if (d.getUniqueId() == this.data.getPlayer().getUniqueId()) {
                    return d.getRank();
                }
            }
            return 0;
        } catch(SQLException ex) {
            return this.data.getPlayer().isOp() ? 1 : 0;
        }
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addToList() {
        dataMap.put(this.data, this);
    }

    public void removeFromList() {
        dataMap.remove(this.data);
    }

}
