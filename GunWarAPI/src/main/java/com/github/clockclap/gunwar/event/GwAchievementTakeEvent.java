package com.github.clockclap.gunwar.event;

import com.github.clockclap.gunwar.achievement.GwAchievement;
import com.github.clockclap.gunwar.util.TextUtilities;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class GwAchievementTakeEvent extends GwAchievementEvent implements Cancellable {

    public static HandlerList handlers = new HandlerList();

    private boolean cancel;
    private BaseComponent message;

    public GwAchievementTakeEvent(Player who, GwAchievement achievement) {
        super(who, achievement);
        cancel = false;
        String msg = TextUtilities.CHAT_ACHIEVEMENT_TAKEN;
        String[] array = msg.split("%ACHIEVEMENT%");
        TextComponent component = new TextComponent();
        for(int i = 0; i < array.length; i++) {
            String s = array[i];
            component.addExtra(s);
            if(i < array.length - 1) {
                TextComponent text = new TextComponent(achievement.getDisplayName());
                for(String line : achievement.getDescription()) text.addExtra("\n" + line);
                TextComponent component1 = new TextComponent("[" + achievement.getDisplayName() + "]");
                component1.setColor(ChatColor.GREEN);
                component1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] { text }));
            }
        }
    }

    public void setTakeMessage(String message) {
        this.message = new TextComponent(message);
    }

    public void setTakeMessage(BaseComponent component) {
        this.message = component;
    }

    public String getTakeMessage() {
        return message.toPlainText();
    }

    public BaseComponent getTakeMessageComponent() {
        return message;
    }

    public boolean isCancelled() {
        return cancel;
    }

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
