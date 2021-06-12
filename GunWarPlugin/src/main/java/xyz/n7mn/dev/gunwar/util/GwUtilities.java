package xyz.n7mn.dev.gunwar.util;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.event.GunWarCommandRegisterEvent;
import xyz.n7mn.dev.gunwar.mysql.GwMySQLPlayerData;
import xyz.n7mn.dev.gunwar.mysql.NanamiServer;
import xyz.n7mn.dev.gunwar.nanamiserver.permission.NanamiServerRoleData;

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
    @NanamiServer
    public String getRoleNameById(int id) throws SQLException {
        return GwMySQLPlayerData.getRoleNameById(id);
    }

    @Override
    public PermissionInfo testPermission(Player player, int required) {
        int mode = GunWar.getConfig().getConfig().getInt("permission.mode", 0);
        if(mode == 1) {
            try {
                String requiredRole = getRoleNameById(required);
                String nowRole = "";
                int now = 0;
                List<NanamiServerRoleData> data = GwMySQLPlayerData.getList();
                for (NanamiServerRoleData d : data) {
                    if (d.getUniqueId() == player.getUniqueId()) {
                        nowRole = d.getName();
                        now = d.getRank();
                    }
                }
                boolean passed = true;
                if (required > now) {
                    passed = false;
                }
                return new PermissionInfo(requiredRole, nowRole, passed);
            } catch (SQLException e) {
                String requiredRole = "一般";
                String nowRole = "一般";
                boolean passed = true;
                if (player.isOp()) {
                    nowRole = "OP持ち";
                }
                if (required >= 1) {
                    requiredRole = "OP持ち";
                    if (!player.isOp()) {
                        passed = false;
                    }
                }
                return new PermissionInfo(requiredRole, nowRole, passed);
            }
        } else {
            String requiredRole = "一般";
            String nowRole = "一般";
            boolean passed = true;
            if (player.isOp()) {
                nowRole = "OP持ち";
            }
            if (required >= 1) {
                requiredRole = "OP持ち";
                if (!player.isOp()) {
                    passed = false;
                }
            }
            return new PermissionInfo(requiredRole, nowRole, passed);
        }
    }
}
