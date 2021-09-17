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

package com.github.clockclap.gunwar.util;

import com.github.clockclap.gunwar.GunWar;
import com.github.clockclap.gunwar.GwAPI;
import com.github.clockclap.gunwar.util.japanize.Japanizer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.UnsupportedEncodingException;

@GwAPI
public final class TextUtilities {

    private static final FileConfiguration lang = GunWar.getConfig().getLang();

    public static final String CHAT_PREFIX = format("chat.prefix", "&2[銃撃戦]&7");

    public static final String CHAT_ABOUT = format("chat.about", "&8[詳細]");
    private static final String CHAT_COMMAND_PERMISSION_ERROR_PRIVATE = format("chat.command.error.permission", "&cこのコマンドを実行する権限がありません");
    public static final String CHAT_COMMAND_ERROR_UNKNOWN_PLAYER = format("chat.command.error.unknown_player", "&c指定したプレイヤーが存在しないか、オンラインではありません。");
    public static final String CHAT_COMMAND_ERROR_DEBUG = format("chat.command.error.debug", "&cデバッグ中にエラーが発生しました");
    public static final String CHAT_COMMAND_ERROR_NOT_DEBUGGING_MODE = format("chat.command.error.not_debugging_mode", "&cデバッグモードが有効ではありません");
    public static final String CHAT_COMMAND_ERROR_CANT_USE_THIS_MODE = format("chat.command.error.cant_use_this_mode", "現在のモードではこのコマンドを使用することはできません。");
    public static final String CHAT_COMMAND_ERROR_GAME_NOT_STARTED = format("chat.command.error.game_not_started", "ゲームがまだ開始されていません。");
    public static final String CHAT_COMMAND_ERROR_ONLY_PLAYER = format("chat.command.error.only_player", "このコマンドはプレイヤーからのみ実行できます。");
    public static final String CHAT_COMMAND_ERROR_NO_FINAL_MESSAGE_TARGET = format("chat.command.error.no_final_message_target", "返信しようとしている相手がオンラインではない、またはプレイヤーではありません。");
    public static final String CHAT_COMMAND_ERROR_CANT_USE_SPECTATOR = format("chat.command.error_cant_use_spectator", "スペクテイターでは使用できません。");
    public static final String CHAT_COMMAND_RELOAD = format("chat.command.reload", "設定を再読み込みしました");
    public static final String CHAT_COMMAND_GIVE_ITEM = format("chat.command.give_item", "&7%PLAYER% に %ITEM% を与えました");
    public static final String CHAT_DETAIL_YOUR_PERMISSION = format("chat.detail.your_permission", "&7あなたの権限: &a%CURRENT%");
    public static final String CHAT_DETAIL_REQUIRED_PERMISSION = format("chat.detail.required_permission", "&7必要な権限: &a%REQUIRED%");
    public static final String CHAT_ACHIEVEMENT_EARNED = format("chat.achievement.earned", "%PLAYER%は実績 %ACHIEVEMENT% を達成しました。");
    public static final String CHAT_ACHIEVEMENT_TAKEN = format("chat.achievement.taken", "&c管理者に実績 %ACHIEVEMENT% が剥奪されました。");

    public static final String CHAT_PLAYER_FIRST_JOIN = format("chat.player_first_join", "&e%PLAYER%が初めてサーバーに参加しました！");
    public static final String CHAT_PLAYER_JOIN = format("chat.player_join", "%PLAYER%がサーバーに参加しました。");
    public static final String CHAT_PLAYER_QUIT = format("chat.player_quit", "%PLAYER%がサーバーから退出しました。");

    public static final String TITLE_MAIN_INFECT = format("ui.title.main.infect", "&2ゾンビに感染してしまった...");
    public static final String TITLE_MAIN_DIED_ZOMBIE = format("ui.title.main.died_zombie", "&2死んでしまった...");
    public static final String TITLE_SUB_INFECT = format("ui.title.sub.infect", "&75秒後にゾンビとして復活します");
    public static final String TITLE_MAIN_INFECTION_RESPAWN = format("ui.title.main.infection_respawn", "&2ゾンビとして復活しました");
    public static final String TITLE_SUB_INFECTION_RESPAWN = format("ui.title.sub.infection_respawn", "&7残りの生存者を感染させよう");
    public static final String TITLE_MAIN_SURVIVOR = format("ui.title.main.survivor", "&3感染から逃れられた");
    public static final String TITLE_SUB_SURVIVOR = format("ui.title.sub.survivor", "&7ゾンビから逃げ切ろう");
    public static final String TITLE_MAIN_VICTORY = format("ui.title.main.victory", "&6勝利");
    public static final String TITLE_MAIN_GAMEOVER = format("ui.title.main.gameover", "&c敗北");
    public static final String TITLE_SUB_VICTORY_SURVIVOR = format("ui.title.sub.victory.survivor", "&3生存者側の勝利");
    public static final String TITLE_SUB_VICTORY_ZOMBIE = format("ui.title.sub.victory.zombie", "&2ゾンビ側の勝利");
    public static final String TITLE_SUB_VICTORY_BLUE = format("ui.title.sub.victory.blue", "&9青チームの勝利");
    public static final String TITLE_SUB_VICTORY_RED = format("ui.title.sub.victory.red", "&c赤チームの勝利");

    public static final String SIDEBAR_TITLE = format("ui.sidebar.title", "&2[ななみ銃撃戦]");
    public static final String SIDEBAR_GAMESTATE = format("ui.sidebar.game_state.current", "&6ステータス");
    public static final String SIDEBAR_PLEASE_START = format("ui.sidebar.please_start", "&eゲームを開始してください");
    public static final String SIDEBAR_PLEASE_WAIT = format("ui.sidebar.please_wait", "&r開始までしばらくお待ち下さい");
    public static final String SIDEBAR_STARTING_AT = format("ui.sidebar.starting_at", "&r開始まであと&a%SECOND%&r秒");
    public static final String SIDEBAR_WAITING_PLAYER = format("ui.sidebar.waiting_player", "&r開始にはあと&a%PLAYERS%&r人必要です");
    public static final String SIDEBAR_REMAINING_TIME = format("ui.sidebar.remaining_time", "&r残りあと&c%SECOND%&r秒");
    public static final String SIDEBAR_DEFENDED_TIME = format("ui.sidebar.defended_time", "&r耐久時間");
    public static final String SIDEBAR_REMAINING_PLAYERS = format("ui.sidebar.remaining_players", "&r残り人数");
    public static final String SIDEBAR_PLAYERS = format("ui.sidebar.players", "&rプレイヤー数");
    public static final String SIDEBAR_TEAM_BLUE = format("ui.sidebar.team.blue", "&9&l青チーム");
    public static final String SIDEBAR_TEAM_RED = format("ui.sidebar.team.red", "&c&l赤チーム");
    public static final String SIDEBAR_CURRENT_TURN = format("ui.sidebar.current_turn", "&r現在のターン");
    public static final String SIDEBAR_DEFENDERS = format("ui.sidebar.defenders", "&a守り");
    public static final String SIDEBAR_ATTACKERS = format("ui.sidebar.attackers", "&a攻め");

    public static final String SIDEBAR_GAMESTATE_WAITING = format("ui.sidebar.game_state.waiting", "&a待機中");
    public static final String SIDEBAR_GAMESTATE_GAMING = format("ui.sidebar.game_state.playing", "&aゲーム中");
    public static final String SIDEBAR_GAMESTATE_ENDING = format("ui.sidebar.game_state.ending", "&a終了処理中");

    public static final String BOSSBAR_WAITING = format("ui.bossbar.waiting", "&c待機中 - 人数が集まるまでしばらくお待ち下さい");
    public static final String BOSSBAR_STARTING = format("ui.bossbar.starting", "&6残り%SECOND%秒でゲームが開始します");

    public static final String MISC_WEAPON_SET = format("misc.weapon-set", "武器セット");

    public static final String MISC_RULE_DEFENDER = format("misc.rule.team.defender", "攻め側の攻撃から大将を護衛しましょう。大将は倒されないように隠れましょう。");
    public static final String MISC_RULE_ATTACKER = format("misc.rule.team.attacker", "要塞を攻略し、守り側の大将を倒しましょう。");
    public static final String MISC_RULE_SURVIVOR = format("misc.rule.team.survivor", "セーフエリアに行って制限時間まで耐え、ゾンビから逃げ切りましょう。");
    public static final String MISC_RULE_ZOMBIE = format("misc.rule.team.zombie", "生存者を左クリックで全員感染させましょう。またはセーフエリアを占拠しましょう。");
    public static final String MISC_RULE_TEAM = format("misc.rule.mode.team", "２チームに分かれて、制限時間になるとスコアの多いチームの勝利になります。");
    public static final String MISC_RULE_SOLO = format("misc.rule.mode.solo", "チームには分かれず、制限時間になるとスコアの最も多いプレイヤーの優勝になります。");
    public static final String MISC_RULE_ZOMBIE_ESCAPE = format("misc.rule.mode.zombie_escape", "ゾンビから逃げるゲームです。ゾンビは武器を持っていません。");
    public static final String MISC_RULE_CASTLE_SIEGE = format("misc.rule.mode.general_siege", "攻め側と守り側に分かれて行う大将戦です。");

    public static final String SLOT_WEAPON = format("slot.weapon", "武器スロット");

    public static final String SLOT_WEAPON_MAIN = format("slot.weapon.main", "メイン武器");
    public static final String SLOT_WEAPON_SUB = format("slot.weapon.sub", "サブ武器");
    public static final String SLOT_WEAPON_KNIFE = format("slot.weapon.knife", "ナイフ");


    public static final String MISC_DESCRIPTION_COMMAND_GUNWARITEM = format("misc.description.command.gunwaritem", "ななみ銃撃戦のアイテムを与えます。");
    public static final String MISC_DESCRIPTION_COMMAND_GUNWARRELOAD = format("misc.description.command.gunwarreload", "銃撃戦プラグインの設定を再読み込みします。");
    public static final String MISC_DESCRIPTION_COMMAND_GWDEBUG = format("misc.description.command.gwdebug", "銃撃戦プラグインのデバッグをします。デバッグモードが有効である必要があります。");

    public static final String MISC_TITLE = format("misc.title", "ななみ銃撃戦");
    public static final String MISC_LOST_CONNECTION = format("misc.lost_connection", "接続が切れました。");
    public static final String MISC_FAILED_TO_CONNECT = format("misc.failed_to_connect", "接続に失敗しました。");
    public static final String MISC_CAUSE = format("misc.cause", "原因");
    public static final String MISC_SOLUTION = format("misc.solution", "解決策");
    public static final String MISC_MORE = format("misc.more", "詳細はななみ鯖公式Discordをご確認ください。");
    public static final String MISC_PLEASE_REPORT = format("misc.please_report", "Discordの%CHANNEL%にて報告してください。");

    public static final String ERROR_LOADING_PLAYERDATA = format("error.loading_playerdata", "プレイヤーデータ読み込み時のエラー発生");
    public static final String ERROR_ON_LOGGING_IN = format("error.logging_in", "ログイン時のエラー発生");

    public static String chat(String playerName, String message) {
        return chat(ChatColor.RESET, "", playerName, message);
    }

    public static String chat(ChatColor prefixColor, String prefix, String playerName, String message) {
        XmlConfiguration conf = GunWar.getConfig().getDetailConfig();
        String format = conf.getString("config.chat.format", "%{color}7%p%{color}8: %{color}r%m");
        String japanizeFormat = conf.getString("config.chat.japanize_format", "%m %{color}7%j");
        String prefixFormat = conf.getString("config.chat.prefix_format", "%{color}8[%c%t%{color}8] %c%p");
        String japanizeCanceller = conf.getString("config.chat.japanize.japanize_canceller", "#");
        String imeCanceller = conf.getString("config.chat.japanize.ime_canceller", "!");
        String escape = conf.getString("config.chat.escape", ".");
        boolean japanizeText = conf.getBoolean("config.chat.japanize.enabled", true);
        boolean japanizePlayerName = conf.getBoolean("config.chat.japanize.player_name", false);
        boolean colouredChat = conf.getBoolean("config.chat.coloured_chat", true);
        format = translateAlternateColorCodes("%{color}", format);
        japanizeFormat = translateAlternateColorCodes("%{color}", japanizeFormat);
        prefixFormat = translateAlternateColorCodes("%{color}", prefixFormat);
        String p = playerName;
        if(!prefix.isEmpty()) p = prefixFormat.replaceAll("%c", prefixColor + "").replaceAll("%t", prefix).replaceAll("%p", playerName);
        String m = message;
        if(japanizeText) {
            try {
                m = Japanizer.japanize(message, japanizeCanceller, imeCanceller, escape, colouredChat, japanizePlayerName, japanizeFormat);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return format.replaceAll("%p", p).replaceAll("%m", m);
    }

    public static String privateChat(String from, String to, String message) {
        XmlConfiguration conf = GunWar.getConfig().getDetailConfig();
        String format = conf.getString("config.chat.private_format", "%{color7}[%p -> %t]%{color}r %m");
        String japanizeFormat = conf.getString("config.chat.japanize_format", "%m %{color}7%j");
        String japanizeCanceller = conf.getString("config.chat.japanize.japanize_canceller", "#");
        String imeCanceller = conf.getString("config.chat.japanize.ime_canceller", "!");
        String escape = conf.getString("config.chat.escape", ".");
        boolean japanizeText = conf.getBoolean("config.chat.japanize.enabled", true);
        boolean japanizePlayerName = conf.getBoolean("config.chat.japanize.player_name", false);
        boolean colouredChat = conf.getBoolean("config.chat.coloured_chat", true);
        format = translateAlternateColorCodes("%{color}", format);
        japanizeFormat = translateAlternateColorCodes("%{color}", japanizeFormat);
        String m = message;
        if(japanizeText) {
            try {
                m = Japanizer.japanize(message, japanizeCanceller, imeCanceller, escape, colouredChat, japanizePlayerName, japanizeFormat);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return format.replaceAll("%p", from).replaceAll("%t", to).replaceAll("%m", m);
    }

    public static String translateAlternateColorCodes(String regex, String textToTranslate) {
        String b = textToTranslate;
        String r = "\\Q" + regex + "\\E";
        char[] colors = "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".toCharArray();
        for(char c : colors) {
            String s = Character.toString(c);
            b = b.replaceAll(r + s, ChatColor.COLOR_CHAR + s);
        }
        return b;
    }

    public static BaseComponent getChatCommandPermissionError(String required, String now) {
        TextComponent component = new TextComponent();
        TextComponent component1 = new TextComponent();
        component.setText(TextUtilities.CHAT_PREFIX + " " + TextUtilities.CHAT_COMMAND_PERMISSION_ERROR_PRIVATE + " ");
        component1.setText(TextUtilities.CHAT_ABOUT);
        component1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{
                new TextComponent(TextUtilities.CHAT_ABOUT + "\n"),
                new TextComponent(CHAT_DETAIL_YOUR_PERMISSION.replaceAll("%CURRENT%", now) + "\n"),
                new TextComponent(CHAT_DETAIL_REQUIRED_PERMISSION.replaceAll("%REQUIRED%", required))
        }));
        component.addExtra(component1);
        return component;
    }

    public static String format(String unformattedText) {
        return ChatColor.translateAlternateColorCodes('&',
                lang.getString(unformattedText,unformattedText));
    }

    public static String format(String unformattedText, String defaultText) {
        return ChatColor.translateAlternateColorCodes('&',
                lang.getString(unformattedText,defaultText));
    }

}
