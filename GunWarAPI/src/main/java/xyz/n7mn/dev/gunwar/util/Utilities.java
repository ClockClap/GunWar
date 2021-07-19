package xyz.n7mn.dev.gunwar.util;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface Utilities {

    public Plugin getPlugin();

    public void registerCommand(String fallbackPrefix, Command command);

    public String getRoleNameById(int id) throws SQLException;

    public PermissionInfo testPermission(Player player, int required);

}
