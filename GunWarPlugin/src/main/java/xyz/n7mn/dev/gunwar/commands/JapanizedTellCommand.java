package xyz.n7mn.dev.gunwar.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_12_R1.command.CraftBlockCommandSender;
import org.bukkit.craftbukkit.v1_12_R1.command.CraftConsoleCommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.util.MessageLog;
import xyz.n7mn.dev.gunwar.util.TextUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JapanizedTellCommand extends Command {

    public JapanizedTellCommand() {
        super("tell", "", "Usage: /tell <player> <message>", Arrays.asList("msg", "message", "m", "t", "w"));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length >= 2 && args[0] != null && args[1] != null) {
            Player to = Bukkit.getPlayer(args[0]);
            if(to != null && to.isOnline()) {
                StringBuilder message = new StringBuilder();
                for(int i = 1; i < args.length; i++) {
                    message.append(args[i]);
                    message.append(" ");
                }
                String name = sender instanceof ConsoleCommandSender ? "Server" : sender.getName();
                String s = TextUtilities.privateChat(sender.getName(), args[0], message.toString().trim());
                sender.sendMessage(s);
                to.sendMessage(s);
                MessageLog.finalMessageTarget.put(sender, to);
                MessageLog.finalMessageTarget.put(to, sender);
                return true;
            }
            sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + TextUtilities.CHAT_COMMAND_ERROR_UNKNOWN_PLAYER);
            return true;
        }
        sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + ChatColor.GRAY + getUsage());
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if(args.length == 1) {
            List<String> result = new ArrayList<>();
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(p != null && p.getName().startsWith(args[0])) {
                    if(p == sender) continue;
                    result.add(p.getName());
                }
            }
            return result;
        }
        return null;
    }
}
