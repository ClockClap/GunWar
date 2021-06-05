package xyz.n7mn.dev.gunwar.util;

import org.bukkit.command.Command;
import org.bukkit.plugin.Plugin;

public interface Utilities {

    public Plugin getPlugin();

    public void registerCommand(String fallbackPrefix, Command command);

}
