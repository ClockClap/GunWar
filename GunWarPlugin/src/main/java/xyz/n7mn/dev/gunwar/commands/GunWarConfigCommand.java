package xyz.n7mn.dev.gunwar.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.util.PermissionInfo;
import xyz.n7mn.dev.gunwar.util.Reference;

import java.util.Arrays;

public class GunWarConfigCommand extends Command {

    public GunWarConfigCommand() {
        super("gunwarconfig");
        setAliases(Arrays.asList("gunwarconf", "gwconfig", "gwconf"));
        setDescription("銃撃戦プラグインの設定を確認できます。");
        setUsage("Usage: /gunwarconfig");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        int required = GunWar.getConfig().getConfig().getInt("permission.command.gunwarreload", 1);
        if(sender instanceof Player) {
            Player p = (Player) sender;
            PermissionInfo info = GunWar.getUtilities().testPermission(p, required);
            if(!info.isPassed()) {
                p.sendMessage(Reference.getChatCommandPermissionError(info.getRequired(), info.getCurrent()));
                return true;
            }
        }
        if(args.length <= 1) {
            sender.sendMessage("" +
                    ChatColor.DARK_GREEN + "=-=-=-=- 銃撃戦 -=-=-=-=" + "\n" +
                    ChatColor.YELLOW + "自動でゲーム開始: " + ChatColor.GREEN +
                    (GunWar.getConfig().getConfig().getBoolean("game.auto-start", true) ? "有効" : ChatColor.RED + "無効") + "\n" +
                    ChatColor.YELLOW + "ゲームモード: " + ChatColor.RED + GunWar.getConfig().getConfig().getString("game.gamemode", "NORMAL") + "\n" +
                    ChatColor.GOLD + "デバッグモード: " + ChatColor.GREEN +
                    (GunWar.getConfig().getConfig().getBoolean("debug", false) ? "有効" : ChatColor.RED + "無効") + "\n" +
                    ChatColor.DARK_GREEN + "=-=-=-=-=-=-=-=-=-=-=-=");
        }
        if(args.length > 1) {
            if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("normal")) {
                sender.sendMessage("" +
                        ChatColor.DARK_GREEN + "=-=-=-=- 銃撃戦 (Normal) -=-=-=-=" + "\n" +
                        ChatColor.DARK_GREEN + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            }
            if(args[1].equalsIgnoreCase("ze") || args[1].equalsIgnoreCase("zombie-escape") || args[1].equalsIgnoreCase("zombieescape")) {
                sender.sendMessage("" +
                        ChatColor.DARK_GREEN + "=-=-=-=- 銃撃戦 (Zombie Escape) -=-=-=-=" + "\n" +
                        ChatColor.DARK_GREEN + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            }
        }
        return true;
    }
}
