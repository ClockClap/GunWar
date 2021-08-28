package com.github.clockclap.gunwar.mysql;

import com.github.clockclap.gunwar.nanamiserver.permission.NanamiServerRoleData;

import java.sql.*;
import java.util.*;

public final class GwMySQL {

    public static boolean createTablePlayerData() throws SQLException {
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

        PreparedStatement statement = con.prepareStatement("" +
                "CREATE TABLE IF NOT EXISTS ? (`uuid` VARCHAR(50),`path` VARCHAR(50))");
        statement.setString(1, "GwPlayerData");
        boolean result = statement.execute();
        statement.close();
        con.close();
        return result;
    }

    public static boolean insertPlayerData(UUID id, String path) throws SQLException {
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

        PreparedStatement statement = con.prepareStatement("" +
                "INSERT INTO `GwPlayerData` (uuid,path) SELECT * FROM (SELECT ? as uuid,? as path)" +
                " AS tmp WHERE NOT EXISTS (SELECT * FROM `GwPlayerData` WHERE `uuid`=?) LIMIT 1");
        statement.setString(1, id.toString());
        statement.setString(2, path);
        statement.setString(3, id.toString());
        boolean result = statement.execute();
        statement.close();
        con.close();
        return result;
    }

    public static boolean updatePlayerData(UUID id, String path) throws SQLException {
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

        PreparedStatement statement = con.prepareStatement("" +
                "UPDATE `GwPlayerData` SET path=? WHERE uuid=?");
        statement.setString(1, path);
        statement.setString(2, id.toString());
        boolean result = statement.execute();
        statement.close();
        con.close();
        return result;
    }

    public static boolean deletePlayerData(UUID id) throws SQLException {
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

        PreparedStatement statement = con.prepareStatement("" +
                "DELETE FROM `GwPlayerData` WHERE uuid=?");
        statement.setString(1, id.toString());
        boolean result = statement.execute();
        statement.close();
        con.close();
        return result;
    }

    public static String getPath(UUID id) throws SQLException {
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
        PreparedStatement statement = con.prepareStatement("" +
                "SELECT * FROM `GwPlayerData`");
        ResultSet resultSet = statement.executeQuery();
        String result = "";
        while(resultSet.next()) {
            if(resultSet.getString("uuid").equalsIgnoreCase(id.toString())) {
                result = resultSet.getString("path");
                break;
            }
        }
        resultSet.close();
        statement.close();
        con.close();

        return result;
    }

    public static boolean exists(UUID id) throws SQLException {
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
        PreparedStatement statement = con.prepareStatement("" +
                "SELECT * FROM `GwPlayerData`");
        ResultSet resultSet = statement.executeQuery();
        boolean exists = false;
        while(resultSet.next()) {
            if(resultSet.getString("uuid").equalsIgnoreCase(id.toString())) {
                exists = true;
                break;
            }
        }
        resultSet.close();
        statement.close();
        con.close();

        return exists;
    }

}
