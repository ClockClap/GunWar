package xyz.n7mn.dev.gunwar.util;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

public final class Reference {

    public static final String PREFIX = ChatColor.DARK_GREEN + "[銃撃戦]" + ChatColor.GRAY;

    public static final String CHAT_ABOUT = ChatColor.DARK_GRAY + "[詳細]";
    private static final String CHAT_COMMAND_PERMISSION_ERROR_PRIVATE = ChatColor.RED + "このコマンドを実行する権限がありません";
    public static final String CHAT_COMMAND_RELOAD = "設定を再読み込みしました";

    public static BaseComponent getChatCommandPermissionError(String required, String now) {
        TextComponent component = new TextComponent();
        TextComponent component1 = new TextComponent();
        component.setText(Reference.PREFIX + " " + Reference.CHAT_COMMAND_PERMISSION_ERROR_PRIVATE + " ");
        component1.setText(Reference.CHAT_ABOUT);
        component1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{
                new TextComponent(Reference.CHAT_ABOUT + "\n"),
                new TextComponent(ChatColor.GRAY + "あなたの権限: " + ChatColor.GREEN + now + "\n"),
                new TextComponent(ChatColor.GRAY + "必要な権限: " + ChatColor.GREEN + required)
        }));
        component.addExtra(component1);
        return component;
    }

}
