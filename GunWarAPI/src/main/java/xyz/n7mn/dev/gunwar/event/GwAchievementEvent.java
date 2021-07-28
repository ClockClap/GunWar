package xyz.n7mn.dev.gunwar.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import xyz.n7mn.dev.gunwar.achievement.GwAchievement;
import xyz.n7mn.dev.gunwar.achievement.GwAchievementBase;

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
