/*
 * Copyright (c) 2021, ClockClap. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.clockclap.gunwar.event;

import com.github.clockclap.gunwar.GunWar;
import com.github.clockclap.gunwar.achievement.GwAchievement;
import com.github.clockclap.gunwar.util.TextUtilities;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class GwAchievementEarnEvent extends GwAchievementEvent implements Cancellable {

    public static HandlerList handlers = new HandlerList();

    private boolean cancel;
    private BaseComponent message;
    private boolean showMessage;

    public GwAchievementEarnEvent(Player who, GwAchievement achievement) {
        super(who, achievement);
        cancel = false;
        showMessage = true;
        String msg = TextUtilities.CHAT_ACHIEVEMENT_EARNED.replaceAll("%PLAYER%", GunWar.getPlayerData(who).detail().getOldName());
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
