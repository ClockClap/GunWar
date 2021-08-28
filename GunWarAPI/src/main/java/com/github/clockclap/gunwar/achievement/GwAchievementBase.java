package com.github.clockclap.gunwar.achievement;

import com.github.clockclap.gunwar.GunWar;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import com.github.clockclap.gunwar.event.GwAchievementEarnEvent;
import com.github.clockclap.gunwar.event.GwAchievementTakeEvent;
import com.github.clockclap.gunwar.game.data.PermanentlyPlayerData;

import java.util.List;

public abstract class GwAchievementBase implements GwAchievement {

    private String name;
    private Material icon;
    private String displayName;
    private List<String> description;

    protected GwAchievementBase(String name, String displayName, List<String> description, Material icon) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.icon = icon;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Material getIcon() {
        return icon;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public List<String> getDescription() {
        return description;
    }

    @Override
    public void give(Player player) {
        if(player != null) {
            GwAchievementEarnEvent e = new GwAchievementEarnEvent(player, this);
            Bukkit.getPluginManager().callEvent(e);
            if(!e.isCancelled()) {
                PermanentlyPlayerData data = GunWar.getGame().getPermanentlyPlayerData(player.getUniqueId());
                data.getAchievements().add(this);
                Bukkit.broadcast(e.getEarnMessageComponent());
            }
        }
    }

    @Override
    public void take(Player player) {
        if(player != null) {
            GwAchievementTakeEvent e = new GwAchievementTakeEvent(player, this);
            Bukkit.getPluginManager().callEvent(e);
            if(!e.isCancelled()) {
                PermanentlyPlayerData data = GunWar.getGame().getPermanentlyPlayerData(player.getUniqueId());
                data.getAchievements().remove(this);
                player.sendMessage(e.getTakeMessageComponent());
            }
        }
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    protected void setDescription(List<String> description) {
        this.description = description;
    }

    protected void setIcon(Material icon) {
        this.icon = icon;
    }
}
