package xyz.n7mn.dev.gunwar.achievement.achievements;

import org.bukkit.Material;
import xyz.n7mn.dev.gunwar.achievement.GwAchievementBase;
import xyz.n7mn.dev.gunwar.util.TextUtilities;

import java.util.Arrays;

public class CraftAchievementMasterKiller extends GwAchievementBase implements AchievementMasterKiller {

    public CraftAchievementMasterKiller() {
        super("master_killer", TextUtilities.format("achievement.master_killer.name", "マスターキラー"),
                Arrays.asList(TextUtilities.format("achievement.master_killer.description", "1試合で50キル")), Material.DIAMOND_SWORD);
    }

}
