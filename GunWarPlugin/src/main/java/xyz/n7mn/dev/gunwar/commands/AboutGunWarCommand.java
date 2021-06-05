package xyz.n7mn.dev.gunwar.commands;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.util.GwReference;
import xyz.n7mn.dev.gunwar.util.Reference;

import java.util.Arrays;

public class AboutGunWarCommand extends Command {
    public AboutGunWarCommand() {
        super("aboutgunwar", GwReference.COMMAND_AGW_DESCRIPTION, "Usage: /aboutgunwar", Arrays.asList("aboutgw", "agw"));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(!sender.isOp()) {
            sender.sendMessage(Reference.getChatCommandPermissionError("OP権限", "一般"));
            return true;
        }
        sender.sendMessage(ChatColor.DARK_GREEN + "=-=-=- ななみ銃撃戦 v" + GunWar.getPlugin().getDescription().getVersion() + " -=-=-=\n" +
                ChatColor.GRAY + "説明: " + ChatColor.RESET + "ゾンビと逃走者に分かれてやる銃撃戦です。銃撃戦とか言いながらゾンビ側は銃を持てません。\n" +
                ChatColor.GRAY + "作者: " + ChatColor.RESET + "ClockClap");
        return true;
    }
}
