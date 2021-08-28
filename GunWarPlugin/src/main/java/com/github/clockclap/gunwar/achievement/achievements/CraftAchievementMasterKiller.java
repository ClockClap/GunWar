package com.github.clockclap.gunwar.achievement.achievements;

import com.github.clockclap.gunwar.achievement.GwAchievementBase;
import org.bukkit.Material;
import com.github.clockclap.gunwar.util.TextUtilities;

import java.util.Arrays;

public class CraftAchievementMasterKiller extends GwAchievementBase implements AchievementMasterKiller {

    public CraftAchievementMasterKiller() {
        super("master_killer", TextUtilities.format("achievement.master_killer.name", "マスターキラー"),
                Arrays.asList(TextUtilities.format("achievement.master_killer.description", "1試合で50キル")), Material.DIAMOND_SWORD);
    }

}
