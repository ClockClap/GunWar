package xyz.n7mn.dev.gunwar.util;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.n7mn.dev.gunwar.GunWar;

public final class Reference {

    private static final FileConfiguration lang = GunWar.getConfig().getLang();

    public static final String CHAT_PREFIX = ChatColor.translateAlternateColorCodes('&',
            lang.getString("chat.prefix", "&2[銃撃戦]&7"));

    public static final String CHAT_ABOUT = ChatColor.translateAlternateColorCodes('&',
            lang.getString("chat.about", "&8[詳細]"));
    private static final String CHAT_COMMAND_PERMISSION_ERROR_PRIVATE = ChatColor.translateAlternateColorCodes('&',
            lang.getString("chat.command.error.permission", "&cこのコマンドを実行する権限がありません"));
    public static final String CHAT_COMMAND_ERROR_UNKNOWN_PLAYER = ChatColor.translateAlternateColorCodes('&',
            lang.getString("chat.command.error.unknown_player", "&c指定したプレイヤーが存在しないか、オンラインではありません。"));
    public static final String CHAT_COMMAND_RELOAD = ChatColor.translateAlternateColorCodes('&',
            lang.getString("chat.command.reload", "設定を再読み込みしました"));
    public static final String CHAT_COMMAND_GIVE_ITEM = ChatColor.translateAlternateColorCodes('&',
            lang.getString("chat.command.give_item", "&7%PLAYER% に %ITEM% を与えました"));
    public static final String CHAT_DETAIL_YOUR_PERMISSION = ChatColor.translateAlternateColorCodes('&',
            lang.getString("chat.detail.your_permission", "&7あなたの権限: &a%CURRENT%"));
    public static final String CHAT_DETAIL_REQUIRED_PERMISSION = ChatColor.translateAlternateColorCodes('&',
            lang.getString("chat.detail.required_permission", "&7必要な権限: &a%REQUIRED%"));

    public static final String TITLE_MAIN_INFECT = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.title.main.infect", "&2ゾンビに感染してしまった..."));
    public static final String TITLE_MAIN_DIED_ZOMBIE = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.title.main.died_zombie", "&2死んでしまった..."));
    public static final String TITLE_SUB_INFECT = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.title.sub.infect", "&75秒後にゾンビとして復活します"));

    public static final String SIDEBAR_TITLE = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.sidebar.title", "&2[ななみ銃撃戦]"));
    public static final String SIDEBAR_GAMESTATE = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.sidebar.game_state.current", "&6ステータス"));
    public static final String SIDEBAR_PLEASE_START = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.sidebar.please_start", "&eゲームを開始してください"));
    public static final String SIDEBAR_PLEASE_WAIT = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.sidebar.please_wait", "&r開始までしばらくお待ち下さい"));
    public static final String SIDEBAR_STARTING_AT = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.sidebar.starting_at", "&r開始まであと&a%SECOND%&r秒"));
    public static final String SIDEBAR_WAITING_PLAYER = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.sidebar.waiting_player", "&r開始にはあと&a%PLAYERS%&r人必要です"));
    public static final String SIDEBAR_REMAINING_TIME = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.sidebar.remaining_time", "&r残りあと&c%SECOND%&r秒"));
    public static final String SIDEBAR_DEFENDED_TIME = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.sidebar.defended_time", "&r耐久時間"));
    public static final String SIDEBAR_REMAINING_PLAYERS = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.sidebar.remaining_players", "&r残り人数"));
    public static final String SIDEBAR_PLAYERS = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.sidebar.players", "&rプレイヤー数"));
    public static final String SIDEBAR_TEAM_BLUE = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.sidebar.team.blue", "&9&l青チーム"));
    public static final String SIDEBAR_TEAM_RED = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.sidebar.team.red", "&c&l赤チーム"));
    public static final String SIDEBAR_CURRENT_TURN = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.sidebar.current_turn", "&r現在のターン"));
    public static final String SIDEBAR_DEFENDERS = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.sidebar.defenders", "&a守り"));
    public static final String SIDEBAR_ATTACKERS = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.sidebar.attackers", "&a攻め"));

    public static final String SIDEBAR_GAMESTATE_WAITING = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.sidebar.game_state.waiting", "&a待機中"));
    public static final String SIDEBAR_GAMESTATE_GAMING = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.sidebar.game_state.playing", "&aゲーム中"));
    public static final String SIDEBAR_GAMESTATE_ENDING = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.sidebar.game_state.ending", "&a終了処理中"));

    public static final String BOSSBAR_WAITING = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.bossbar.waiting", "&c待機中 - 人数が集まるまでしばらくお待ち下さい"));
    public static final String BOSSBAR_STARTING = ChatColor.translateAlternateColorCodes('&',
            lang.getString("ui.bossbar.starting", "&6残り%SECOND%秒でゲームが開始します"));

    public static final String MISC_RULE_DEFENDER = ChatColor.translateAlternateColorCodes('&',
            lang.getString("misc.rule.team.defender", "攻め側の攻撃から大将を護衛しましょう。大将は倒されないように隠れましょう。"));
    public static final String MISC_RULE_ATTACKER = ChatColor.translateAlternateColorCodes('&',
            lang.getString("misc.rule.team.attacker", "要塞を攻略し、守り側の大将を倒しましょう。"));
    public static final String MISC_RULE_SURVIVOR = ChatColor.translateAlternateColorCodes('&',
            lang.getString("misc.rule.team.survivor", "セーフエリアに行って制限時間まで耐え、ゾンビから逃げ切りましょう。"));
    public static final String MISC_RULE_ZOMBIE = ChatColor.translateAlternateColorCodes('&',
            lang.getString("misc.rule.team.zombie", "生存者を左クリックで全員感染させましょう。またはセーフエリアを占拠しましょう。"));
    public static final String MISC_RULE_TEAM = ChatColor.translateAlternateColorCodes('&',
            lang.getString("misc.rule.mode.team", "２チームに分かれて、制限時間になるとスコアの多いチームの勝利になります。"));
    public static final String MISC_RULE_SOLO = ChatColor.translateAlternateColorCodes('&',
            lang.getString("misc.rule.mode.solo", "チームには分かれず、制限時間になるとスコアの最も多いプレイヤーの優勝になります。"));
    public static final String MISC_RULE_ZOMBIE_ESCAPE = ChatColor.translateAlternateColorCodes('&',
            lang.getString("misc.rule.mode.zombie_escape", "ゾンビから逃げるゲームです。ゾンビは武器を持っていません。"));
    public static final String MISC_RULE_CASTLE_SIEGE = ChatColor.translateAlternateColorCodes('&',
            lang.getString("misc.rule.mode.castle_siege", "攻め側と守り側に分かれて行う攻城戦です。"));

    public static BaseComponent getChatCommandPermissionError(String required, String now) {
        TextComponent component = new TextComponent();
        TextComponent component1 = new TextComponent();
        component.setText(Reference.CHAT_PREFIX + " " + Reference.CHAT_COMMAND_PERMISSION_ERROR_PRIVATE + " ");
        component1.setText(Reference.CHAT_ABOUT);
        component1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{
                new TextComponent(Reference.CHAT_ABOUT + "\n"),
                new TextComponent(CHAT_DETAIL_YOUR_PERMISSION.replaceAll("%CURRENT%", now) + "\n"),
                new TextComponent(CHAT_DETAIL_REQUIRED_PERMISSION.replaceAll("%REQUIRED%", required))
        }));
        component.addExtra(component1);
        return component;
    }

}
