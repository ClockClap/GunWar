package xyz.n7mn.dev.gunwar.commands;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.n7mn.dev.api.data.RoleData;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.NanamiGunWar;
import xyz.n7mn.dev.gunwar.util.GwReference;
import xyz.n7mn.dev.gunwar.util.PermissionInfo;
import xyz.n7mn.dev.gunwar.util.Reference;

import java.sql.*;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class AboutGunWarCommand extends Command {
    public AboutGunWarCommand() {
        super("aboutgunwar", GwReference.COMMAND_AGW_DESCRIPTION, "Usage: /aboutgunwar", Arrays.asList("aboutgw", "agw"));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        int required = GunWar.getConfig().getPermissionSetting().getInt("commands.aboutgunwar", 0);
        if(sender instanceof Player) {
            Player p = (Player) sender;
            PermissionInfo info = GunWar.getUtilities().testPermission(p, required);
            if(!info.isPassed()) {
                p.sendMessage(Reference.getChatCommandPermissionError(info.getRequired(), info.getCurrent()));
                return true;
            }
        }
        sender.sendMessage(ChatColor.DARK_GREEN + "=-=-=- ななみ銃撃戦 v" + GunWar.getPlugin().getDescription().getVersion() + " -=-=-=\n" +
                ChatColor.GRAY + "説明: " + ChatColor.RESET + "...\n" +
                ChatColor.GRAY + "作者: " + ChatColor.RESET + "ClockClap");
        return true;
    }
}
