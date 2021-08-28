package com.github.clockclap.gunwar.commands;

import com.github.clockclap.gunwar.GunWar;
import com.github.clockclap.gunwar.util.PermissionInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.github.clockclap.gunwar.util.GwReference;
import com.github.clockclap.gunwar.util.TextUtilities;

import java.util.Arrays;

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
                p.sendMessage(TextUtilities.getChatCommandPermissionError(info.getRequired(), info.getCurrent()));
                return true;
            }
        }
        sender.sendMessage(ChatColor.DARK_GREEN + "=-=-=- ななみ銃撃戦 v" + GunWar.getPlugin().getDescription().getVersion() + " -=-=-=\n" +
                ChatColor.GRAY + "説明: " + ChatColor.RESET + "...\n" +
                ChatColor.GRAY + "作者: " + ChatColor.RESET + "ClockClap");
        return true;
    }
}
