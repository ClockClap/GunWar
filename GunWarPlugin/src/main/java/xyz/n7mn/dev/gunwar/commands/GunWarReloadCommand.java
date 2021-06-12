package xyz.n7mn.dev.gunwar.commands;

import com.google.common.base.Charsets;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.block.CraftBlock;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.api.data.RoleData;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.NanamiGunWar;
import xyz.n7mn.dev.gunwar.game.data.GunWarEntityData;
import xyz.n7mn.dev.gunwar.game.data.GunWarPlayerData;
import xyz.n7mn.dev.gunwar.util.PermissionInfo;
import xyz.n7mn.dev.gunwar.util.Reference;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class GunWarReloadCommand extends Command {
    public GunWarReloadCommand() {
        super("gunwarreload", "銃撃戦プラグインの設定を再読み込みします", "Usage: /gunwarreload", Arrays.asList("gwreload"));
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
        reload();
        sender.sendMessage(Reference.PREFIX + " " + Reference.CHAT_COMMAND_RELOAD);
        return true;
    }

    private void reload() {
        FileConfiguration newConfig = YamlConfiguration.loadConfiguration(GunWar.getConfig().getConfigFile());

        final InputStream defConfigStream = GunWar.getPlugin().getResource("config.yml");
        if (defConfigStream == null) {
            return;
        }

        newConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
        try {
            GunWar.getConfig().getConfig().load(GunWar.getConfig().getConfigFile());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
