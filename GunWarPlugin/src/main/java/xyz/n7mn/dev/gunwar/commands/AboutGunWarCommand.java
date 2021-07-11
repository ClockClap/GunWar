package xyz.n7mn.dev.gunwar.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.util.GwReference;
import xyz.n7mn.dev.gunwar.util.PermissionInfo;
import xyz.n7mn.dev.gunwar.util.TextUtilities;

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
