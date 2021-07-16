package xyz.n7mn.dev.gunwar.commands;

import com.google.common.base.Charsets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.mysql.MySQLSettingBuilder;
import xyz.n7mn.dev.gunwar.util.NanamiGunWarConfiguration;
import xyz.n7mn.dev.gunwar.util.PermissionInfo;
import xyz.n7mn.dev.gunwar.util.TextUtilities;

import java.io.*;
import java.util.Arrays;

public class GunWarReloadCommand extends Command {
    public GunWarReloadCommand() {
        super("gunwarreload", TextUtilities.MISC_DESCRIPTION_COMMAND_GUNWARRELOAD, "Usage: /gunwarreload", Arrays.asList("gwreload"));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        int required = GunWar.getConfig().getPermissionSetting().getInt("commands.gunwarreload", 1);
        if(sender instanceof Player) {
            Player p = (Player) sender;
            PermissionInfo info = GunWar.getUtilities().testPermission(p, required);
            if(!info.isPassed()) {
                p.sendMessage(TextUtilities.getChatCommandPermissionError(info.getRequired(), info.getCurrent()));
                return true;
            }
        }
        reload();
        sender.sendMessage(TextUtilities.CHAT_PREFIX + " " + TextUtilities.CHAT_COMMAND_RELOAD);
        return true;
    }

    private void reload() {
        reloadConfig();
        reloadPerm();
    }

    private void reloadConfig() {
        FileConfiguration newConfig = YamlConfiguration.loadConfiguration(GunWar.getConfig().getConfigFile());

        final InputStream defConfigStream = GunWar.getPlugin().getResource("config.yml");
        if (defConfigStream == null) {
            return;
        }

        newConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
        try {
            GunWar.getConfig().getConfig().load(GunWar.getConfig().getConfigFile());
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void reloadPerm() {
        String path = GunWar.getConfig().getConfig()
                .getString("permission-setting", ((NanamiGunWarConfiguration) GunWar.getConfig()).getDataFolder() + "/permission/default.yml");
        if(path.startsWith(":nanami-network:>")) {
            if(MySQLSettingBuilder.isEnabled()) GunWar.getConfig().setNanamiNetwork(true);
            path = path.substring(17);
        } else {
            GunWar.getConfig().setNanamiNetwork(false);
        }
        ((NanamiGunWarConfiguration) GunWar.getConfig()).setPermissionFile(new File(path));
        File permissionFile = GunWar.getConfig().getPermissionSettingFile();
        boolean b1 = true;
        if (!permissionFile.exists()) {
            try {
                permissionFile.createNewFile();
            } catch(IOException ex) {
                return;
            }
            b1 = false;
        }
        if (!b1) {
            try {
                InputStream inputStream = GunWar.getPlugin().getResource("permission/default.yml");
                File file = permissionFile;
                OutputStream out = new FileOutputStream(file);
                byte[] buf = new byte['?'];
                int length;
                while ((length = inputStream.read(buf)) > 0) {
                    out.write(buf, 0, length);
                }
                out.close();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        FileConfiguration newConfig = YamlConfiguration.loadConfiguration(GunWar.getConfig().getPermissionSettingFile());

        final InputStream defConfigStream = GunWar.getPlugin().getResource("permission/default.yml");
        if (defConfigStream == null) {
            return;
        }

        newConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
        try {
            GunWar.getConfig().getPermissionSetting().load(GunWar.getConfig().getPermissionSettingFile());
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
