package xyz.n7mn.dev.gunwar.listeners;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.NanamiGunWar;
import xyz.n7mn.dev.gunwar.game.GunWarGame;
import xyz.n7mn.dev.gunwar.game.data.*;
import xyz.n7mn.dev.gunwar.util.NanamiGunWarConfiguration;
import xyz.n7mn.dev.gunwar.util.PlayerWatcher;

import java.io.File;
import java.io.IOException;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent e) {
        try {
            GunWarPermanentlyPlayerData permanentlyPlayerData = new GunWarPermanentlyPlayerData(e.getUniqueId());
            File f = permanentlyPlayerData.getDefaultDataFile();
            if (!f.exists()) {
                f.createNewFile();
                permanentlyPlayerData.save(f);
            } else {
                permanentlyPlayerData.load(f);
            }
            ((GunWarGame) GunWar.getGame()).addPermanentlyPlayerData(permanentlyPlayerData);
        } catch(Throwable ex) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "" +
                    ChatColor.DARK_GREEN + "=== ななみ銃撃戦 ===\n" +
                    ChatColor.RED + "接続に失敗しました。\n" +
                    ChatColor.GRAY + "原因: " + ChatColor.WHITE + "ログイン時のエラー発生\n" +
                    ChatColor.GRAY + "解決策: " + ChatColor.WHITE + "Discordの" + ChatColor.BLUE + "#銃撃戦-バグ報告" + ChatColor.WHITE + "にて報告してください。\n" +
                    "\n" +
                    ChatColor.WHITE + "詳細はななみ鯖公式Discordをご確認ください。\n" +
                    ChatColor.GOLD + "" + ChatColor.UNDERLINE + GunWar.getConfig().getConfig().getString("discord"));
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(ChatColor.GREEN + "[+] " + ChatColor.GRAY + e.getPlayer().getName());
        GunWarPlayerData data = new GunWarPlayerData(e.getPlayer());
        PlayerWatcher watcher = new PlayerWatcher(GunWar.getPlugin(), data);
        watcher.startWatch();
        watcher.startWatch10Ticks();
        data.setWatcher(watcher);
        ((GunWarGame) GunWar.getGame()).addPlayerData(data.getUniqueId(), data);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(ChatColor.RED + "[-] " + ChatColor.GRAY + e.getPlayer().getName());
        PlayerData data = GunWar.getGame().getPlayerData(e.getPlayer());
        if(data != null) {
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
    }

}
