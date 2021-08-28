package xyz.n7mn.dev.gunwar.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.game.GunWarGame;
import xyz.n7mn.dev.gunwar.game.data.*;
import xyz.n7mn.dev.gunwar.mysql.GwMySQLDataPath;
import xyz.n7mn.dev.gunwar.util.PlayerWatcher;
import xyz.n7mn.dev.gunwar.util.TextUtilities;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent e) {
        try {
            GunWarPermanentlyPlayerData permanentlyPlayerData = new GunWarPermanentlyPlayerData(e.getUniqueId());
            File f = permanentlyPlayerData.getDefaultDataFile();
            if (!f.exists()) {
                permanentlyPlayerData.save(f);
            } else {
                permanentlyPlayerData.load(f);
            }
            ((GunWarGame) GunWar.getGame()).addPermanentlyPlayerData(permanentlyPlayerData);
        } catch(Throwable ex) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "" +
                    ChatColor.DARK_GREEN + "=== " + TextUtilities.MISC_TITLE + " ===\n" +
                    ChatColor.RED + TextUtilities.MISC_FAILED_TO_CONNECT + "\n" +
                    ChatColor.GRAY + TextUtilities.MISC_CAUSE + ": " + ChatColor.WHITE + TextUtilities.ERROR_ON_LOGGING_IN + "\n" +
                    ChatColor.GRAY + TextUtilities.MISC_SOLUTION + ": " + ChatColor.WHITE + TextUtilities.MISC_PLEASE_REPORT.replaceAll("%CHANNEL%", ChatColor.BLUE + "#銃撃戦-バグ報告" + ChatColor.WHITE) + "\n" +
                    "\n" +
                    ChatColor.WHITE + TextUtilities.MISC_MORE + "\n" +
                    ChatColor.GOLD + "" + ChatColor.UNDERLINE + GunWar.getConfig().getConfig().getString("discord", "https://discord.gg/nbRUAmmypS"));
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(p.hasPlayedBefore()) {
            e.setJoinMessage((GunWar.getConfig().getLang().getBoolean("chat.player_join.raw", false) ? "" : TextUtilities.CHAT_PREFIX + " ") +
                    TextUtilities.CHAT_PLAYER_JOIN.replaceAll("%PLAYER%", p.getName()));
        } else {
            e.setJoinMessage((GunWar.getConfig().getLang().getBoolean("chat.player_first_join.raw", false) ? "" : TextUtilities.CHAT_PREFIX + " ") +
                    TextUtilities.CHAT_PLAYER_FIRST_JOIN.replaceAll("%PLAYER%", p.getName()));
        }
        GunWarPlayerData data = new GunWarPlayerData(p);
        PlayerWatcher watcher = new PlayerWatcher(GunWar.getPlugin(), data);
        watcher.startWatch();
        watcher.startWatch10Ticks();
        data.setWatcher(watcher);
        ((GunWarGame) GunWar.getGame()).addPlayerData(data.getUniqueId(), data);
        data.detail().setName(ChatColor.GRAY + data.detail().getOldName());
        p.setPlayerListName(ChatColor.GRAY + data.detail().getOldName());
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for(Player pl : players) {
            data.detail().hide(pl);
            data.detail().show(pl);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage((GunWar.getConfig().getLang().getBoolean("chat.player_quit.raw", false) ? "" : TextUtilities.CHAT_PREFIX + " ") +
                TextUtilities.CHAT_PLAYER_QUIT.replaceAll("%PLAYER%", e.getPlayer().getName()));
        PlayerData data = GunWar.getGame().getPlayerData(e.getPlayer());
        if(data != null) {
            data.detail().resetName();
            PlayerWatcher watcher = data.getWatcher();
            watcher.stopWatch();
            watcher.stopWatch10Ticks();
            ((GunWarGame) GunWar.getGame()).removePlayerData(data.getUniqueId());
        }
        PermanentlyPlayerData data_ = GunWar.getGame().getPermanentlyPlayerData(e.getPlayer().getUniqueId());
        if(data_ != null) {
            try {
                data_.save(data_.getDefaultDataFile());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            ((GunWarGame) GunWar.getGame()).removePermanentlyPlayerData(e.getPlayer().getUniqueId());
        }
        GwMySQLDataPath.delete(e.getPlayer().getUniqueId());
    }

}
