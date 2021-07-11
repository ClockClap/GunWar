package xyz.n7mn.dev.gunwar.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.event.GunWarCommandRegisterEvent;
import xyz.n7mn.dev.gunwar.util.TextUtilities;

public class ServerListener implements Listener {

    @EventHandler
    public void onRegisterCommand(GunWarCommandRegisterEvent e) {
        if(e.getCommand().getName().equalsIgnoreCase("gunwarreload") ||
                e.getCommand().getName().equalsIgnoreCase("gunwaritem")) {
            if(e.getThrowable() != null) {
                e.catchThrowable();
                GunWar.getPlugin().getLogger().info("Failed to register command: /" + e.getCommand().getName());
                return;
            }
            GunWar.getPlugin().getLogger().info("Succeeded registering command: /" + e.getCommand().getName());
        }
    }

    @EventHandler
    public void onChatAsync(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Bukkit.broadcastMessage(TextUtilities.chat(e.getPlayer().getName(), e.getMessage()));
    }

}
