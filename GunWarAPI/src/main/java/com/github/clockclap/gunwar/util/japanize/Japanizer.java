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

package com.github.clockclap.gunwar.util.japanize;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Japanizer {

    private static final Map<String, String> con;

    private static final String[] ll;
    private static final String[] hl;

    private Japanizer() {
    }

    static {
        con = new HashMap<>();

        con.put("a", "あ");
        con.put("i", "い");
        con.put("yi", "い");
        con.put("u", "う");
        con.put("wu", "う");
        con.put("whu", "う");
        con.put("e", "え");
        con.put("o", "お");

        con.put("wha", "うぁ");
        con.put("whi", "うぃ");
        con.put("wi", "うぃ");
        con.put("whe", "うぇ");
        con.put("we", "うぇ");
        con.put("who", "うぉ");

        con.put("wyi", "ゐ");
        con.put("wye", "ゑ");

        con.put("la", "ぁ");
        con.put("xa", "ぁ");
        con.put("li", "ぃ");
        con.put("xi", "ぃ");
        con.put("lyi", "ぃ");
        con.put("xyi", "ぃ");
        con.put("lu", "ぅ");
        con.put("xu", "ぅ");
        con.put("le", "ぇ");
        con.put("xe", "ぇ");
        con.put("lye", "ぇ");
        con.put("xye", "ぇ");
        con.put("lo", "ぉ");
        con.put("xo", "ぉ");

        con.put("ye", "いぇ");

        con.put("ka", "か");
        con.put("ca", "か");
        con.put("ki", "き");
        con.put("ku", "く");
        con.put("cu", "く");
        con.put("qu", "く");
        con.put("ke", "け");
        con.put("ko", "こ");
        con.put("co", "こ");

        con.put("kya", "きゃ");
        con.put("kyi", "きぃ");
        con.put("kyu", "きゅ");
        con.put("kye", "きぇ");
        con.put("kyo", "きょ");

        con.put("qya", "くゃ");
        con.put("qyu", "くゅ");
        con.put("qyo", "くょ");

        con.put("qwa", "くぁ");
        con.put("qa", "くぁ");
        con.put("kwa", "くぁ");
        con.put("qwi", "くぃ");
        con.put("qi", "くぃ");
        con.put("qyi", "くぃ");
        con.put("qwu", "くぅ");
        con.put("qwe", "くぇ");
        con.put("qe", "くぇ");
        con.put("qye", "くぇ");
        con.put("qwo", "くぉ");
        con.put("qo", "くぉ");
        con.put("kwo", "くぉ");

        con.put("ga", "が");
        con.put("gi", "ぎ");
        con.put("gu", "ぐ");
        con.put("ge", "げ");
        con.put("go", "ご");

        con.put("gya", "ぎゃ");
        con.put("gyi", "ぎぃ");
        con.put("gyu", "ぎゅ");
        con.put("gye", "ぎぇ");
        con.put("gyo", "ぎょ");

        con.put("gwa", "ぐぁ");
        con.put("gwi", "ぐぃ");
        con.put("gwu", "ぐぅ");
        con.put("gwe", "ぐぇ");
        con.put("gwo", "ぐぉ");

        con.put("lka", "ヵ");
        con.put("xka", "ヵ");
        con.put("lke", "ヶ");
        con.put("xke", "ヶ");

        con.put("sa", "さ");
        con.put("si", "し");
        con.put("ci", "し");
        con.put("shi", "し");
        con.put("su", "す");
        con.put("se", "せ");
        con.put("ce", "せ");
        con.put("so", "そ");

        con.put("sya", "しゃ");
        con.put("sha", "しゃ");
        con.put("syi", "しぃ");
        con.put("syu", "しゅ");
        con.put("shu", "しゅ");
        con.put("sye", "しぇ");
        con.put("she", "しぇ");
        con.put("syo", "しょ");
        con.put("sho", "しょ");

        con.put("swa", "すぁ");
        con.put("swi", "すぃ");
        con.put("swu", "すぅ");
        con.put("swe", "すぇ");
        con.put("swo", "すぉ");

        con.put("za", "ざ");
        con.put("zi", "じ");
        con.put("ji", "じ");
        con.put("zu", "ず");
        con.put("ze", "ぜ");
        con.put("zo", "ぞ");

        con.put("zya", "じゃ");
        con.put("ja", "じゃ");
        con.put("jya", "じゃ");
        con.put("zyi", "じぃ");
        con.put("jyi", "じぃ");
        con.put("zyu", "じゅ");
        con.put("ju", "じゅ");
        con.put("jyu", "じゅ");
        con.put("zye", "じぇ");
        con.put("je", "じぇ");
        con.put("jye", "じぇ");
        con.put("zyo", "じょ");
        con.put("jo", "じょ");
        con.put("jyo", "じょ");

        con.put("ta", "た");
        con.put("ti", "ち");
        con.put("chi", "ち");
        con.put("tu", "つ");
        con.put("tsu", "つ");
        con.put("te", "て");
        con.put("to", "と");

        con.put("tya", "ちゃ");
        con.put("cha", "ちゃ");
        con.put("cya", "ちゃ");
        con.put("tyi", "ちぃ");
        con.put("cyi", "ちぃ");
        con.put("tyu", "ちゅ");
        con.put("chu", "ちゅ");
        con.put("cyu", "ちゅ");
        con.put("tye", "ちぇ");
        con.put("che", "ちぇ");
        con.put("cye", "ちぇ");
        con.put("tyo", "ちょ");
        con.put("cho", "ちょ");
        con.put("cyo", "ちょ");

        con.put("tsa", "つぁ");
        con.put("tsi", "つぃ");
        con.put("tse", "つぇ");
        con.put("tso", "つぉ");

        con.put("tha", "てゃ");
        con.put("thi", "てぃ");
        con.put("thu", "てゅ");
        con.put("the", "てぇ");
        con.put("tho", "てょ");

        con.put("twa", "とぁ");
        con.put("twi", "とぃ");
        con.put("twu", "とぅ");
        con.put("twe", "とぇ");
        con.put("two", "とぉ");

        con.put("da", "だ");
        con.put("di", "ぢ");
        con.put("du", "づ");
        con.put("de", "で");
        con.put("do", "ど");

        con.put("dya", "ぢゃ");
        con.put("dyi", "ぢぃ");
        con.put("dyu", "ぢゅ");
        con.put("dye", "ぢぇ");
        con.put("dyo", "ぢょ");

        con.put("dha", "でゃ");
        con.put("dhi", "でぃ");
        con.put("dhu", "でゅ");
        con.put("dhe", "でぇ");
        con.put("dho", "でょ");

        con.put("dwa", "どぁ");
        con.put("dwi", "どぃ");
        con.put("dwu", "どぅ");
        con.put("dwe", "どぇ");
        con.put("dwo", "どぉ");

        con.put("ltu", "っ");
        con.put("xtu", "っ");
        con.put("ltsu", "っ");
        con.put("xtsu", "っ");

        con.put("na", "な");
        con.put("ni", "に");
        con.put("nu", "ぬ");
        con.put("ne", "ね");
        con.put("no", "の");

        con.put("nya", "にゃ");
        con.put("nyi", "にぃ");
        con.put("nyu", "にゅ");
        con.put("nye", "にぇ");
        con.put("nyo", "にょ");

        con.put("ha", "は");
        con.put("hi", "ひ");
        con.put("hu", "ふ");
        con.put("fu", "ふ");
        con.put("he", "へ");
        con.put("ho", "ほ");

        con.put("hya", "ひゃ");
        con.put("hyi", "ひぃ");
        con.put("hyu", "ひゅ");
        con.put("hye", "ひぇ");
        con.put("hyo", "ひょ");

        con.put("fwa", "ふぁ");
        con.put("fa", "ふぁ");
        con.put("fwi", "ふぃ");
        con.put("fi", "ふぃ");
        con.put("fyi", "ふぃ");
        con.put("fwu", "ふぅ");
        con.put("fwe", "ふぇ");
        con.put("fe", "ふぇ");
        con.put("fye", "ふぇ");
        con.put("fwo", "ふぉ");
        con.put("fo", "ふぉ");

        con.put("fya", "ふゃ");
        con.put("fyu", "ふゅ");
        con.put("fyo", "ふょ");

        con.put("ba", "ば");
        con.put("bi", "び");
        con.put("bu", "ぶ");
        con.put("be", "べ");
        con.put("bo", "ぼ");

        con.put("bya", "びゃ");
        con.put("byi", "びぃ");
        con.put("byu", "びゅ");
        con.put("bye", "びぇ");
        con.put("byo", "びょ");

        con.put("va", "ヴぁ");
        con.put("vi", "ヴぃ");
        con.put("vu", "ヴ");
        con.put("ve", "ヴぇ");
        con.put("vo", "ヴぉ");

        con.put("vya", "ヴゃ");
        con.put("vyi", "ヴぃ");
        con.put("vyu", "ヴゅ");
        con.put("vye", "ヴぇ");
        con.put("vyo", "ヴょ");

        con.put("pa", "ぱ");
        con.put("pi", "ぴ");
        con.put("pu", "ぷ");
        con.put("pe", "ぺ");
        con.put("po", "ぽ");

        con.put("pya", "ぴゃ");
        con.put("pyi", "ぴぃ");
        con.put("pyu", "ぴゅ");
        con.put("pye", "ぴぇ");
        con.put("pyo", "ぴょ");

        con.put("ma", "ま");
        con.put("mi", "み");
        con.put("mu", "む");
        con.put("me", "め");
        con.put("mo", "も");

        con.put("mya", "みゃ");
        con.put("myi", "みぃ");
        con.put("myu", "みゅ");
        con.put("mye", "みぇ");
        con.put("myo", "みょ");

        con.put("ya", "や");
        con.put("yu", "ゆ");
        con.put("yo", "よ");

        con.put("lya", "ゃ");
        con.put("xya", "ゃ");
        con.put("lyu", "ゅ");
        con.put("xyu", "ゅ");
        con.put("lyo", "ょ");
        con.put("xyo", "ょ");

        con.put("ra", "ら");
        con.put("ri", "り");
        con.put("ru", "る");
        con.put("re", "れ");
        con.put("ro", "ろ");

        con.put("rya", "りゃ");
        con.put("ryi", "りぃ");
        con.put("ryu", "りゅ");
        con.put("rye", "りぇ");
        con.put("ryo", "りょ");

        con.put("wa", "わ");
        con.put("wo", "を");

        con.put("lwa", "ゎ");
        con.put("xwa", "ゎ");

        con.put("n", "ん");
        con.put("nn", "ん");
        con.put("n'", "ん");
        con.put("xn", "ん");

        for ( Map.Entry<String, String> entry : con.entrySet() ) {
            String romaji = entry.getKey();
            String hiragana = entry.getValue();
            if (!StringUtils.startsWithAny(romaji, "a", "i", "u", "e", "o", "n")) {
                con.put(romaji.charAt(0) + romaji, "っ" + hiragana);
            }
        }

        con.put("-", "ー");
        con.put(",", "、");
        con.put(".", "。");
        con.put("?", "？");
        con.put("!", "！");
        con.put("[", "「");
        con.put("]", "」");

        con.put("&0", "\u00a70");
        con.put("&1", "\u00a71");
        con.put("&2", "\u00a72");
        con.put("&3", "\u00a73");
        con.put("&4", "\u00a74");
        con.put("&5", "\u00a75");
        con.put("&6", "\u00a76");
        con.put("&7", "\u00a77");
        con.put("&8", "\u00a78");
        con.put("&9", "\u00a79");
        con.put("&a", "\u00a7a");
        con.put("&b", "\u00a7b");
        con.put("&c", "\u00a7c");
        con.put("&d", "\u00a7d");
        con.put("&e", "\u00a7e");
        con.put("&f", "\u00a7f");
        con.put("&k", "\u00a7k");
        con.put("&l", "\u00a7l");
        con.put("&m", "\u00a7m");
        con.put("&n", "\u00a7n");
        con.put("&o", "\u00a7o");
        con.put("&r", "\u00a7r");

        ll = con.keySet().toArray(new String[0]);
        hl = con.values().toArray(new String[0]);
    }

    public static String japanize(String input, String japanizeCanceller, String imeCanceller, String escape, boolean colouredChat, boolean japanizePlayerName, String jformat) throws UnsupportedEncodingException {
        String msg = input;
        String result;
        boolean ime = true;
        byte[] buf = msg.getBytes("SJIS");
        if(msg.length() == buf.length && !msg.startsWith(japanizeCanceller)) {
            if(msg.startsWith(imeCanceller)) {
                msg = msg.substring(1);
                ime = false;
            }
            if(msg.startsWith(escape)) {
                msg = msg.substring(1);
            }
            String beforeconvert = msg;
            if(colouredChat) {
                beforeconvert = ChatColor.translateAlternateColorCodes('&', beforeconvert);
//                msg = msg.replaceAll("&0", "%COLOR_CH0719682009%");
//                msg = msg.replaceAll("&1", "%COLOR_CH1968829860%");
//                msg = msg.replaceAll("&2", "%COLOR_CH2386997101%");
//                msg = msg.replaceAll("&3", "%COLOR_CH3708126056%");
//                msg = msg.replaceAll("&4", "%COLOR_CH4675978972%");
//                msg = msg.replaceAll("&5", "%COLOR_CH5061000271%");
//                msg = msg.replaceAll("&6", "%COLOR_CH6999177200%");
//                msg = msg.replaceAll("&7", "%COLOR_CH7215630099%");
//                msg = msg.replaceAll("&8", "%COLOR_CH8017596028%");
//                msg = msg.replaceAll("&9", "%COLOR_CH9683716961%");
//                msg = msg.replaceAll("&(a|A)", "%COLOR_CHS0581265105%");
//                msg = msg.replaceAll("&(b|B)", "%COLOR_CHS1983716587%");
//                msg = msg.replaceAll("&(c|C)", "%COLOR_CHS2689102282%");
//                msg = msg.replaceAll("&(d|D)", "%COLOR_CHS3611000193%");
//                msg = msg.replaceAll("&(e|E)", "%COLOR_CHS4871006899%");
//                msg = msg.replaceAll("&(f|F)", "%COLOR_CHS5839195606%");
//                msg = msg.replaceAll("&(k|K)", "%COLOR_CHR4175912001%");
//                msg = msg.replaceAll("&(l|L)", "%COLOR_CHR0158849715%");
//                msg = msg.replaceAll("&(m|M)", "%COLOR_CHR1857120067%");
//                msg = msg.replaceAll("&(n|N)", "%COLOR_CHR2547106916%");
//                msg = msg.replaceAll("&(o|O)", "%COLOR_CHR3050819923%");
//                msg = msg.replaceAll("&(r|R)", "%COLOR_CHRESET%");
                msg = msg.replaceAll("&A", "&a");
                msg = msg.replaceAll("&B", "&b");
                msg = msg.replaceAll("&C", "&c");
                msg = msg.replaceAll("&D", "&d");
                msg = msg.replaceAll("&E", "&e");
                msg = msg.replaceAll("&F", "&f");
                msg = msg.replaceAll("&K", "&k");
                msg = msg.replaceAll("&L", "&l");
                msg = msg.replaceAll("&M", "&m");
                msg = msg.replaceAll("&N", "&n");
                msg = msg.replaceAll("&O", "&o");
                msg = msg.replaceAll("&R", "&r");
            }
            result = kana(msg);
            if(!japanizePlayerName) {
                Collection<? extends Player> players = Bukkit.getOnlinePlayers();
                for (Player p : players) {
                    String name = p.getName();
                    String japanizedName = kana(name);
                    result = result.replaceAll(japanizedName, name);
                }
            }
            if(ime) result = ime(result);
            result = result.replaceAll("０", "0");
            result = result.replaceAll("１", "1");
            result = result.replaceAll("２", "2");
            result = result.replaceAll("３", "3");
            result = result.replaceAll("４", "4");
            result = result.replaceAll("５", "5");
            result = result.replaceAll("６", "6");
            result = result.replaceAll("７", "7");
            result = result.replaceAll("８", "8");
            result = result.replaceAll("９", "9");
            if(colouredChat) {
                result = ChatColor.translateAlternateColorCodes('&', result);
            }
            result = jformat.replaceAll("%m", beforeconvert).replaceAll("%j", result);
        } else {
            if(msg.indexOf(japanizeCanceller) == 0) {
                if(msg.indexOf(imeCanceller) == 1) {
                    msg = msg.substring(2);
                } else {
                    msg = msg.substring(1);
                }
            } else if(msg.indexOf(imeCanceller) == 0) {
                if(msg.indexOf(japanizeCanceller) == 1) {
                    msg = msg.substring(2);
                } else {
                    msg = msg.substring(1);
                }
            }
            if(colouredChat) {
                msg = ChatColor.translateAlternateColorCodes('&', msg);
            }
            result = msg;
        }

        return result;
    }

    private static String kana(String romaji) {
        return StringUtils.replaceEach(romaji, ll, hl);
    }

    private static String ime(String text) {
        if ( text.length() == 0 ) {
            return "";
        }

        HttpURLConnection urlconn = null;
        BufferedReader reader = null;
        try {
            String encode = "UTF-8";
            String baseurl = "https://www.google.com/transliterate?langpair=ja-Hira|ja&text=" + URLEncoder.encode(text , encode);

            URL url = new URL(baseurl);
            urlconn = (HttpURLConnection)url.openConnection();
            urlconn.setRequestMethod("GET");
            urlconn.setInstanceFollowRedirects(false);
            urlconn.connect();

            reader = new BufferedReader(
                    new InputStreamReader(urlconn.getInputStream(), encode));
            @SuppressWarnings({ "all" })
            String json = CharStreams.toString(reader);
            return parseJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if ( urlconn != null ) urlconn.disconnect();
            if ( reader != null ) try {
                reader.close();
            } catch (IOException ignored) { }
        }
        return "";
    }

    private static String parseJson(String json) {
        StringBuilder sb = new StringBuilder();
        for (JsonElement response : new Gson().fromJson(json, JsonArray.class)) {
            sb.append(response.getAsJsonArray().get(1).getAsJsonArray().get(0).getAsString());
        }
        return sb.toString();
    }

}
