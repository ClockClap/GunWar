package xyz.n7mn.dev.gunwar.event;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import xyz.n7mn.dev.gunwar.GunWar;
import xyz.n7mn.dev.gunwar.achievement.GwAchievement;
import xyz.n7mn.dev.gunwar.util.TextUtilities;

public class GwAchievementEarnEvent extends GwAchievementEvent implements Cancellable {

    public static HandlerList handlers = new HandlerList();

    private boolean cancel;
    private BaseComponent message;
    private boolean showMessage;

    public GwAchievementEarnEvent(Player who, GwAchievement achievement) {
        super(who, achievement);
        cancel = false;
        showMessage = true;
        String msg = TextUtilities.CHAT_ACHIEVEMENT_EARNED.replaceAll("%PLAYER%", GunWar.getPlayerData(who).nanami().getOldName());
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

    public void setEarnMessage(String message) {
        this.message = new TextComponent(message);
    }

    public void setEarnMessage(BaseComponent component) {
        this.message = component;
    }

    public String getEarnMessage() {
        return message.toPlainText();
    }

    public BaseComponent getEarnMessageComponent() {
        return message;
    }

    public boolean isShowingMessage() {
        return showMessage;
    }

    public void setShowingMessage(boolean showMessage) {
        this.showMessage = showMessage;
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
