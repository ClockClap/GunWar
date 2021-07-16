package xyz.n7mn.dev.gunwar.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.game.data.PlayerData;
import xyz.n7mn.dev.gunwar.item.GwItem;
import xyz.n7mn.dev.gunwar.item.GwItems;
import xyz.n7mn.dev.gunwar.util.PermissionInfo;
import xyz.n7mn.dev.gunwar.util.TextUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GunWarItemCommand extends Command {

    public GunWarItemCommand() {
        super("gunwaritem", TextUtilities.MISC_DESCRIPTION_COMMAND_GUNWARITEM, "使用法: /gunwaritem <player> <item>", Arrays.asList("gwitem", "gwi"));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        int required = GunWar.getConfig().getPermissionSetting().getInt("commands.gunwaritem", 1);
        if(sender instanceof Player) {
            Player p = (Player) sender;
            PermissionInfo info = GunWar.getUtilities().testPermission(p, required);
            if(!info.isPassed()) {
                p.sendMessage(TextUtilities.getChatCommandPermissionError(info.getRequired(), info.getCurrent()));
                return true;
            }
        }
        if(args.length >= 2) {
            Player p = Bukkit.getPlayer(args[0]);
            if(p == null) {
                sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + TextUtilities.CHAT_COMMAND_ERROR_UNKNOWN_PLAYER);
                return true;
            }
            PlayerData data = GunWar.getGame().getPlayerData(p);
            for (GwItem i : GwItems.getRegisteredItems()) {
                if (i.getName().equalsIgnoreCase(args[1])) {
                    data.giveItem(i);
                    sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + TextUtilities.CHAT_COMMAND_GIVE_ITEM
                            .replaceAll("%PLAYER%", p.getName()).replaceAll("%ITEM%", i.getName()));
                    return true;
                }
            }
        }
        sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + ChatColor.GRAY + getUsage());
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if(args.length == 1) {
            List<String> result = new ArrayList<>();
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(p.getName().startsWith(args[0]))
                result.add(p.getName());
            }
            return result;
        }
        if(args.length == 2) {
            List<String> result = new ArrayList<>();
            for(GwItem i : GwItems.getRegisteredItems()) {
                if(i.getName().startsWith(args[1]))
                result.add(i.getName());
            }
            return result;
        }
        return null;
    }
}
