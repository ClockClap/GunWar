package xyz.n7mn.dev.gunwar.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.exception.InvalidTargetSelectorException;
import xyz.n7mn.dev.gunwar.entity.selector.TargetSelector;
import xyz.n7mn.dev.gunwar.exception.TargetSelectorException;
import xyz.n7mn.dev.gunwar.game.data.PlayerData;
import xyz.n7mn.dev.gunwar.item.GwItem;
import xyz.n7mn.dev.gunwar.item.GwItems;
import xyz.n7mn.dev.gunwar.util.PermissionInfo;
import xyz.n7mn.dev.gunwar.util.Reference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class GunWarItemCommand extends Command {

    public GunWarItemCommand() {
        super("gunwaritem", "ななみ銃撃戦のアイテムを入手します。", "使用法: /gunwaritem <player> <item>", Arrays.asList("gwitem", "gwi"));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        int required = GunWar.getConfig().getConfig().getInt("permission.command.gunwaritem", 1);
        if(sender instanceof Player) {
            Player p = (Player) sender;
            PermissionInfo info = GunWar.getUtilities().testPermission(p, required);
            if(!info.isPassed()) {
                p.sendMessage(Reference.getChatCommandPermissionError(info.getRequired(), info.getCurrent()));
                return true;
            }
        }
        if(args.length >= 2) {
            TargetSelector.Builder builder = TargetSelector.builder(TargetSelector.Type.ALL_ENTITY, null, null);
            if(sender instanceof Player) {
                builder = TargetSelector.builder(TargetSelector.Type.ALL_ENTITY, (Player) sender, ((Player) sender).getLocation());
            }
            TargetSelector selector;
            try {
                selector = builder.fromString(args[0]).build();
            } catch (TargetSelectorException ex) {
                sender.sendMessage(Reference.PREFIX + " " + Reference.CHAT_COMMAND_ERROR_INVALID_TARGET);
                return true;
            }

            Collection<? extends Entity> entity = selector.getTargets();

            for(Entity e : entity) {
                if(e instanceof Player) {
                    Player p = (Player) e;
                    PlayerData data = GunWar.getGame().getPlayerData(p);
                    for (GwItem i : GwItems.getRegisteredItems()) {
                        if (i.getName().equalsIgnoreCase(args[1])) {
                            data.giveItem(i);
                            sender.sendMessage(Reference.PREFIX + " " + Reference.CHAT_COMMAND_GIVE_ITEM
                                    .replaceAll("%PLAYER%", p.getName()).replaceAll("%ITEM%", i.getName()));
                            return true;
                        }
                    }
                }
            }
        }
        sender.sendMessage(Reference.PREFIX + " " + ChatColor.GRAY + getUsage());
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
