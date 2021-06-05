package xyz.n7mn.dev.gunwar.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.event.GunWarCommandRegisterEvent;

public class ServerListener implements Listener {

    @EventHandler
    public void onRegisterCommand(GunWarCommandRegisterEvent e) {
        if(e.getCommand().getName().equalsIgnoreCase("aboutgunwar")) {
            if(e.getThrowable() != null) {
                e.catchThrowable();
                GunWar.getPlugin().getLogger().info("Failed to register command: /" + e.getCommand().getName());
                return;
            }
            GunWar.getPlugin().getLogger().info("Succeeded registering command: /" + e.getCommand().getName());
        }
    }

}
