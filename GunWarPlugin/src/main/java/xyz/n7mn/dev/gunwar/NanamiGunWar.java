package xyz.n7mn.dev.gunwar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.n7mn.dev.api.Role;
import xyz.n7mn.dev.gunwar.commands.AboutGunWarCommand;
import xyz.n7mn.dev.gunwar.commands.GunWarReloadCommand;
import xyz.n7mn.dev.gunwar.game.GunWarGame;
import xyz.n7mn.dev.gunwar.game.data.GunWarPermanentlyPlayerData;
import xyz.n7mn.dev.gunwar.game.data.GunWarPlayerData;
import xyz.n7mn.dev.gunwar.game.data.PermanentlyPlayerData;
import xyz.n7mn.dev.gunwar.game.data.PlayerData;
import xyz.n7mn.dev.gunwar.game.gamemode.GwGameModes;
import xyz.n7mn.dev.gunwar.item.GwItems;
import xyz.n7mn.dev.gunwar.listeners.ItemListener;
import xyz.n7mn.dev.gunwar.listeners.PlayerListener;
import xyz.n7mn.dev.gunwar.listeners.ServerListener;
import xyz.n7mn.dev.gunwar.mysql.GwMySQLPlayerDataUpdater;
import xyz.n7mn.dev.gunwar.mysql.MySQLSettingBuilder;
import xyz.n7mn.dev.gunwar.util.GwUtilities;
import xyz.n7mn.dev.gunwar.util.NanamiGunWarConfiguration;
import xyz.n7mn.dev.gunwar.util.PlayerWatcher;

import java.io.File;
import java.io.IOException;

public final class NanamiGunWar extends JavaPlugin {

    private NanamiGunWar plugin;
    private SimplePluginManager pluginManager;
    private GwUtilities utilities;
    private NanamiGunWarConfiguration config;
    public static Role role;
    public static GwMySQLPlayerDataUpdater updater;
    private GunWarGame game;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        GunWar.plugin = plugin;
        utilities = new GwUtilities(plugin);
        GunWar.utilities = utilities;
        config = new NanamiGunWarConfiguration(plugin);
        GunWar.config = config;
        config.init();
        MySQLSettingBuilder.builder().withSetting(config.getConfig().getString("mysql.host", "localhost"),
                config.getConfig().getInt("mysql.port", 3306),
                config.getConfig().getString("mysql.database", ""),
                config.getConfig().getString("mysql.option", "?allowPublicKeyRetrieval=true&useSSL=false"),
                config.getConfig().getString("mysql.username", ""),
                config.getConfig().getString("mysql.password", "")).build();
        role = new Role(config.getConfig().getString("mysql.host", "localhost"),
                config.getConfig().getInt("mysql.port", 3306),
                config.getConfig().getString("mysql.database", ""),
                config.getConfig().getString("mysql.option", "?allowPublicKeyRetrieval=true&useSSL=false"),
                config.getConfig().getString("mysql.username", ""),
                config.getConfig().getString("mysql.password", ""));
        GwGameModes.registerDefault();
        game = new GunWarGame(plugin);
        GunWar.game = game;
        GwItems.a();
        pluginManager = (SimplePluginManager) Bukkit.getPluginManager();
        registerListeners();
        registerCommands();
        for(Player p : Bukkit.getOnlinePlayers()) {
            try {
                GunWarPermanentlyPlayerData permanentlyPlayerData = new GunWarPermanentlyPlayerData(p.getUniqueId());
                File f = permanentlyPlayerData.getDefaultDataFile();
                if (!f.exists()) {
                    f.createNewFile();
                    permanentlyPlayerData.save(f);
                } else {
                    permanentlyPlayerData.load(f);
                }
                ((GunWarGame) GunWar.getGame()).addPermanentlyPlayerData(permanentlyPlayerData);
            } catch(Throwable ex) {
                p.kickPlayer("" +
                        ChatColor.DARK_GREEN + "=== ななみ銃撃戦 ===\n" +
                        ChatColor.RED + "接続が切れました。\n" +
                        ChatColor.GRAY + "原因: " + ChatColor.WHITE + "プレイヤーデータ読み込み時のエラー発生\n" +
                        ChatColor.GRAY + "解決策: " + ChatColor.WHITE + "Discordの" + ChatColor.BLUE + "#銃撃戦-バグ報告" + ChatColor.WHITE + "にて報告してください。\n" +
                        "\n" +
                        ChatColor.WHITE + "詳細はななみ鯖公式Discordをご確認ください。\n" +
                        ChatColor.GOLD + "" + ChatColor.UNDERLINE + GunWar.getConfig().getConfig().getString("discord"));
                ex.printStackTrace();
            }
            GunWarPlayerData data = new GunWarPlayerData(p);
            PlayerWatcher watcher = new PlayerWatcher(GunWar.getPlugin(), data);
            watcher.startWatch();
            watcher.startWatch10Ticks();
            data.setWatcher(watcher);
            ((GunWarGame) GunWar.getGame()).addPlayerData(data.getUniqueId(), data);
        }
    }

    private void registerListeners() {
        pluginManager.registerEvents(new PlayerListener(), plugin);
        pluginManager.registerEvents(new ServerListener(), plugin);
        pluginManager.registerEvents(new ItemListener(), plugin);
    }

    private void registerCommands() {
        utilities.registerCommand(plugin.getName(), new AboutGunWarCommand());
        utilities.registerCommand(plugin.getName(), new GunWarReloadCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for(Player p : Bukkit.getOnlinePlayers()) {
            PlayerData data = GunWar.getGame().getPlayerData(p);
            if(data != null) {
                PlayerWatcher watcher = data.getWatcher();
                watcher.stopWatch();
                watcher.stopWatch10Ticks();
                ((GunWarGame) GunWar.getGame()).removePlayerData(data.getUniqueId());
            }
            PermanentlyPlayerData data_ = GunWar.getGame().getPermanentlyPlayerData(p.getUniqueId());
            if(data_ != null) {
                try {
                    data_.save(data_.getDefaultDataFile());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ((GunWarGame) GunWar.getGame()).removePermanentlyPlayerData(p.getUniqueId());
            }
        }
        GwGameModes.clear();
        GwItems.clear();
    }
}
