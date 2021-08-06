package xyz.n7mn.dev.gunwar.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.event.GunWarCommandRegisterEvent;
import xyz.n7mn.dev.gunwar.game.GameState;
import xyz.n7mn.dev.gunwar.game.data.ItemData;
import xyz.n7mn.dev.gunwar.game.data.PlayerData;
import xyz.n7mn.dev.gunwar.game.gamemode.GwGameMode;
import xyz.n7mn.dev.gunwar.game.gamemode.Shoutable;
import xyz.n7mn.dev.gunwar.item.GwWeaponItem;
import xyz.n7mn.dev.gunwar.item.WeaponType;
import xyz.n7mn.dev.gunwar.util.TextUtilities;

import java.util.HashMap;

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
        if(GunWar.getGame().getState() == GameState.PLAYING) {
            GwGameMode mode = GunWar.getGame().getGameMode();
            PlayerData data = GunWar.getPlayerData(e.getPlayer());
            String prefix = "";
            ChatColor color = ChatColor.GRAY;
            if (mode.getTeamNames().containsKey(data.getTeam())) prefix = mode.getTeamNames().get(data.getTeam());
            if (mode.getTeamColors().containsKey(data.getTeam())) color = mode.getTeamColors().get(data.getTeam());
            if(data.isSpectator()) {
                for(PlayerData d : GunWar.getOnlinePlayerData()) {
                    if(d.isSpectator())
                        d.sendMessage(ChatColor.DARK_GRAY + "(Spectator Chat) " + TextUtilities.chat(color, prefix, e.getPlayer().getName(), e.getMessage()));
                }
            } else if(mode instanceof Shoutable) {
                for(PlayerData d : GunWar.getOnlinePlayerData()) {
                    if(d.getTeam() == data.getTeam())
                        d.sendMessage(ChatColor.BLUE + "(Team Chat) " + TextUtilities.chat(color, prefix, e.getPlayer().getName(), e.getMessage()));
                }
            } else {
                Bukkit.broadcastMessage(TextUtilities.chat(color, prefix, e.getPlayer().getName(), e.getMessage()));
            }
        } else {
            Bukkit.broadcastMessage(TextUtilities.chat(e.getPlayer().getName(), e.getMessage()));
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if(p != null) {
            if(!p.isOp()) {
                e.setCancelled(true);
            } else {
                if(p.getGameMode() == GameMode.SURVIVAL) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if(p != null) {
            if(p.getGameMode() == GameMode.SURVIVAL && GunWar.getGame().getState() == GameState.PLAYING) {
                if (p.getInventory().getItemInMainHand() != null) {
                    ItemData data = GunWar.getGame().getItemData(p.getInventory().getItemInMainHand());
                    if (data.getGwItem() instanceof GwWeaponItem && ((GwWeaponItem) data.getGwItem()).getWeaponType() == WeaponType.KNIFE) {
                        if (e.getBlock() != null && e.getBlock().getType() == Material.GLASS) {
                            return;
                        }
                    }
                }
            }
            if(!p.isOp()) {
                e.setCancelled(true);
            } else {
                if(p.getGameMode() == GameMode.SURVIVAL) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
