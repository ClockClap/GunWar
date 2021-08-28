package com.github.clockclap.gunwar.achievement;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public interface GwAchievement {

    public String getName();

    public Material getIcon();

    public String getDisplayName();

    public List<String> getDescription();

    public void give(Player player);

    public void take(Player player);

}
