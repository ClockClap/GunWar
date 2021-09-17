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

import com.github.clockclap.gunwar.GwAPI;
import org.bukkit.Bukkit;
import org.xml.sax.SAXException;
import com.github.clockclap.gunwar.GunWar;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@GwAPI
public class XmlConfiguration {

    private Map<String, Object> map;
    private Map<String, Map<String, String>> attributes;

    private XmlConfiguration() {
        map = new HashMap<>();
        attributes = new HashMap<>();
    }

    public static XmlConfiguration load(String path) throws ParserConfigurationException, SAXException, IOException {
        return load(new File(path));
    }

    public static XmlConfiguration load(File file) throws ParserConfigurationException, SAXException, IOException {
        XmlConfiguration conf = new XmlConfiguration();
        if(file.exists()) {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            GwSAX sax = new GwSAX();
            SAXParser parser = factory.newSAXParser();
            parser.parse(file, sax);
            conf.attributes.putAll(sax.attributeMap());
            for(Map.Entry<String, String> entry : sax.valueMap().entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();
                Object obj = val;
                if(val.equalsIgnoreCase("true") || val.equalsIgnoreCase("false")) {
                    obj = Boolean.parseBoolean(val);
                }
                try {
                    obj = Integer.parseInt(val);
                } catch(Throwable ignored) { }
                try {
                    obj = Short.parseShort(val);
                } catch(Throwable ignored) { }
                try {
                    obj = Byte.parseByte(val);
                } catch(Throwable ignored) { }
                try {
                    obj = Long.parseLong(val);
                } catch(Throwable ignored) { }
                try {
                    obj = Double.parseDouble(val);
                } catch(Throwable ignored) { }
                try {
                    obj = Float.parseFloat(val);
                } catch(Throwable ignored) { }
                conf.map.put(key, obj);
            }
        }
        return conf;
    }

    public static XmlConfiguration loadXml(File file) {
        try {
            return load(file);
        } catch(ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            GunWar.getPlugin().getLogger().severe("An error occurred while loading detail configuration so the plugin will be disabled.");
            Bukkit.getPluginManager().disablePlugin(GunWar.getPlugin());
        }
        return null;
    }

    public Object get(String path, Object def) {
        Object result = def;
        if(map.containsKey(path)) {
            result = map.get(path);
        }
        return result;
    }

    public boolean getBoolean(String path) {
        return getBoolean(path, false);
    }

    public byte getByte(String path) {
        return getByte(path, (byte) 0);
    }

    public short getShort(String path) {
        return getShort(path, (short) 0);
    }

    public int getInt(String path) {
        return getInt(path, 0);
    }

    public long getLong(String path) {
        return getLong(path, 0L);
    }

    public double getDouble(String path) {
        return getDouble(path, 0D);
    }

    public float getFloat(String path) {
        return getFloat(path, 0F);
    }

    public String getString(String path) {
        return getString(path, "");
    }

    public boolean getBoolean(String path, boolean def) {
        Object obj = get(path, def);
        if(obj instanceof Boolean) {
            return (Boolean) obj;
        }
        return def;
    }

    public byte getByte(String path, byte def) {
        Object obj = get(path, def);
        if(obj instanceof Byte) {
            return (Byte) obj;
        }
        return def;
    }

    public short getShort(String path, short def) {
        Object obj = get(path, def);
        if(obj instanceof Short) {
            return (Short) obj;
        }
        return def;
    }

    public int getInt(String path, int def) {
        Object obj = get(path, def);
        if(obj instanceof Integer) {
            return (Integer) obj;
        }
        return def;
    }

    public long getLong(String path, long def) {
        Object obj = get(path, def);
        if(obj instanceof Long) {
            return (Long) obj;
        }
        return def;
    }

    public double getDouble(String path, double def) {
        Object obj = get(path, def);
        if(obj instanceof Double) {
            return (Double) obj;
        }
        return def;
    }

    public float getFloat(String path, float def) {
        Object obj = get(path, def);
        if(obj instanceof Float) {
            return (Float) obj;
        }
        return def;
    }

    public String getString(String path, String def) {
        Object obj = get(path, def);
        if(obj instanceof String) {
            return (String) obj;
        }
        return def;
    }

    public String getAttribute(String path, String name, String def) {
        String result = def;
        if(attributes.containsKey(path) && attributes.get(path).containsKey(name)) {
            result = attributes.get(path).get(name);
        }
        return result;
    }

    public String getAttribute(String path, String name) {
        return getAttribute(path, name, "");
    }

}
