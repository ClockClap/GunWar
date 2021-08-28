package com.github.clockclap.gunwar.event;

import com.github.clockclap.gunwar.achievement.GwAchievement;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

public abstract class GwAchievementEvent extends PlayerEvent {

    private GwAchievement achievement;

    protected GwAchievementEvent(Player who, GwAchievement achievement) {
        super(who);
        this.achievement = achievement;
    }

    public GwAchievement getAchievement() {
        return achievement;
    }
}
