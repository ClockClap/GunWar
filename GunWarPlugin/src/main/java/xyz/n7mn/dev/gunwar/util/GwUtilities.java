package xyz.n7mn.dev.gunwar.util;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.api.data.RoleData;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.NanamiGunWar;
import xyz.n7mn.dev.gunwar.event.GunWarCommandRegisterEvent;

import java.sql.*;
import java.util.Enumeration;
import java.util.List;

public class GwUtilities implements Utilities {

    private final Plugin plugin;

    public GwUtilities(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void registerCommand(String fallbackPrefix, Command command) {
        try {
            CommandMap map = Bukkit.getServer().getCommandMap();
            map.getKnownCommands().put(command.getName(), command);
            for (String alias : command.getAliases()) {
                map.getKnownCommands().put(alias, command);
            }
            Bukkit.getServer().getCommandMap().register(fallbackPrefix, command);
            GunWarCommandRegisterEvent event = new GunWarCommandRegisterEvent(command, null);
            Bukkit.getPluginManager().callEvent(event);
        } catch(Throwable throwable) {
            GunWarCommandRegisterEvent event = new GunWarCommandRegisterEvent(command, throwable);
            Bukkit.getPluginManager().callEvent(event);
            if(!event.isCaught()) throw throwable;
        }
    }

    @Override
    public String getRoleNameById(int id) throws SQLException {
        String result;
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

        Connection con = DriverManager.getConnection("jdbc:mysql://" +
                        GunWar.getConfig().getConfig().getString("mysql.host") + ":" +
                        GunWar.getConfig().getConfig().getInt("mysql.port") + "/" +
                        GunWar.getConfig().getConfig().getString("mysql.database")+
                        GunWar.getConfig().getConfig().getString("mysql.option"),
                GunWar.getConfig().getConfig().getString("mysql.username"),
                GunWar.getConfig().getConfig().getString("mysql.password")
        );
        con.setAutoCommit(true);

        PreparedStatement statement = con.prepareStatement("SELECT * FROM RoleRankList");
        ResultSet set = statement.executeQuery();

        StringBuffer sb = new StringBuffer();
        while (set.next()){
            if (set.getInt("Rank") == id){
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

    @Override
    public PermissionInfo testPermission(Player player, int required) {
        try {
            String requiredRole = GunWar.getUtilities().getRoleNameById(required);
            String nowRole = "";
            int now = 0;
            List<RoleData> data = NanamiGunWar.role.getList();
            for(RoleData d : data) {
                if(d.getUUID() == player.getUniqueId()) {
                    nowRole = d.getRoleName();
                    now = d.getRoleRank();
                }
            }
            boolean passed = true;
            if(required > now) {
                passed = false;
            }
            return new PermissionInfo(requiredRole, nowRole, passed);
        } catch (SQLException e){
            String requiredRole = "一般";
            String nowRole = "一般";
            boolean passed = true;
            if(player.isOp()) {
                nowRole = "OP持ち";
            }
            if (required >= 1) {
                requiredRole = "OP持ち";
                if(!player.isOp()) {
                    passed = false;
                }
            }
            return new PermissionInfo(requiredRole, nowRole, passed);
        }
    }
}
