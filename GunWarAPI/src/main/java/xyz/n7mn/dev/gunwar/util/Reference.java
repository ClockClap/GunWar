package xyz.n7mn.dev.gunwar.util;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import xyz.n7mn.dev.gunwar.GunWar;

public final class Reference {

    public static final String PREFIX = ChatColor.DARK_GREEN + "[銃撃戦]" + ChatColor.GRAY;

    public static final String CHAT_ABOUT = ChatColor.DARK_GRAY + "[詳細]";
    private static final String CHAT_COMMAND_PERMISSION_ERROR_PRIVATE = ChatColor.RED + "このコマンドを実行する権限がありません";
    public static final String CHAT_COMMAND_ERROR_INVALID_TARGET = ChatColor.RED + "無効なセレクターです。";
    public static final String CHAT_COMMAND_ERROR_UNKNOWN_PLAYER = ChatColor.RED + "指定したプレイヤーが存在しないか、オンラインではありません。";
    public static final String CHAT_COMMAND_RELOAD = "設定を再読み込みしました";
    public static final String CHAT_COMMAND_GIVE_ITEM = ChatColor.GRAY + "%PLAYER% に %ITEM% を与えました";

    public static final String TITLE_MAIN_INFECT = ChatColor.DARK_GREEN + "ゾンビに感染してしまった...";
    public static final String TITLE_MAIN_DIED_ZOMBIE = ChatColor.DARK_GREEN + "死んでしまった...";
    public static final String TITLE_SUB_INFECT = ChatColor.GRAY + "5秒後にゾンビとして復活します";

    public static final String SIDEBAR_TITLE = ChatColor.DARK_GREEN + "[ななみ銃撃戦]";
    public static final String SIDEBAR_GAMESTATE = ChatColor.GOLD + "ステータス";
    public static final String SIDEBAR_PLEASE_START = ChatColor.YELLOW + "ゲームを開始してください";
    public static final String SIDEBAR_PLEASE_WAIT = ChatColor.WHITE + "開始までしばらくお待ち下さい";
    public static final String SIDEBAR_STARTING_AT = ChatColor.WHITE + "開始まであと" + ChatColor.GREEN + "%SECOND%" + ChatColor.RESET + "秒";
    public static final String SIDEBAR_WAITING_PLAYER = ChatColor.WHITE + "開始にはあと" + ChatColor.GREEN + "%PLAYERS%" + ChatColor.RESET + "人必要です";
    public static final String SIDEBAR_REMAINING_TIME = ChatColor.RESET + "残りあと" + ChatColor.RED + "%SECOND%" + ChatColor.RESET + "秒";
    public static final String SIDEBAR_DEFENDED_TIME = ChatColor.RESET + "耐久時間";
    public static final String SIDEBAR_REMAINING_PLAYERS = ChatColor.RESET + "残り人数";
    public static final String SIDEBAR_PLAYERS = ChatColor.RESET + "プレイヤー数";
    public static final String SIDEBAR_TEAM_BLUE = ChatColor.BLUE + "" + ChatColor.BOLD + "青チーム";
    public static final String SIDEBAR_TEAM_RED = ChatColor.RED + "" + ChatColor.BOLD + "赤チーム";
    public static final String SIDEBAR_CURRENT_TURN = ChatColor.RESET + "現在のターン";
    public static final String SIDEBAR_DEFENDERS = ChatColor.GREEN + "守り";
    public static final String SIDEBAR_ATTACKERS = ChatColor.GREEN + "攻め";

    public static final String SIDEBAR_GAMESTATE_WAITING = ChatColor.GREEN + "待機中";
    public static final String SIDEBAR_GAMESTATE_GAMING = ChatColor.GREEN + "ゲーム中";
    public static final String SIDEBAR_GAMESTATE_ENDING = ChatColor.GREEN + "終了処理中";

    public static final String BOSSBAR_WAITING = ChatColor.RED + "待機中 - 人数が集まるまでしばらくお待ち下さい";
    public static final String BOSSBAR_STARTING = ChatColor.GOLD + "残り%SECOND%秒でゲームが開始します";

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
