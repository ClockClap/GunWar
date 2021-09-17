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

package com.github.clockclap.gunwar.entity.selector;

import com.github.clockclap.gunwar.GwAPI;

import java.io.Serializable;
import java.util.*;

@GwAPI
@Deprecated
public final class OptionMap<K, V> implements Serializable {

    public static final class Entry<K, V> {

        private K key;
        private Class<?> type;
        private V value;
        private String param;
        private String string;

        private Entry(K key, Class<?> type, V value, String param) {
            this.key = key;
            this.type = type;
            this.value = value;
            this.param = param;
            this.string = key.toString() + "=" + value.toString() + " (" + type.getName() + ")";
        }

        public K getKey() {
            return key;
        }

        public String getParam() {
            return param;
        }

        public Class<?> getType() {
            return type;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value) ^ Objects.hashCode(type);
        }
    }


    private transient Set<OptionMap.Entry<K, V>> entrySet;
    private transient Set<K> keySet;

    public OptionMap() {
        entrySet = new HashSet<>();
        keySet = new HashSet<>();
    }

    public OptionMap(OptionMap<K, V> option) {
        entrySet = new HashSet<>();
        keySet = new HashSet<>();
        entrySet.addAll(option.entrySet());
        keySet.addAll(option.keySet());
    }

    public Set<OptionMap.Entry<K, V>> entrySet() {
        Set<OptionMap.Entry<K, V>> es = entrySet;
        if(es == null) {
            es = new HashSet<>();
            entrySet = es;
        }
        return es;
    }

    public Set<K> keySet() {
        Set<K> ks = keySet;
        if(ks == null) {
            ks = new HashSet<>();
            keySet = ks;
        }
        return ks;
    }

    public void add(K key, Class<?> type, V value, String param) {
        if(!keySet.contains(key)) {
            OptionMap.Entry<K, V> entry = new OptionMap.Entry<>(key, type, value, param);
            entrySet.add(entry);
            keySet.add(key);
        }
    }

    public void set(K key, V value) {
        if(keySet.contains(key)) {
            for(OptionMap.Entry<K, V> entry : entrySet) {
                if(entry.getKey() instanceof String && key instanceof String) {
                    if (((String) entry.getKey()).equalsIgnoreCase((String)key)) {
                        if(entry.getType().isInstance(value)) entry.setValue(value);
                        break;
                    }
                } else {
                    if (entry.getKey() == key) {
                        if(entry.getType().isInstance(value)) entry.setValue(value);
                        break;
                    }
                }
            }
        }
    }

    public void setByParam(String param, V value) {
        for(OptionMap.Entry<K, V> entry : entrySet) {
            if(entry.getParam().equalsIgnoreCase(param)) {
                if(entry.getType().isInstance(value)) entry.setValue(value);
                break;
            }
        }
    }

    public boolean containsKey(K key) {
        return keySet.contains(key);
    }

    public void remove(K key) {
        if(keySet.contains(key)) {
            Set<Entry<K, V>> es = new HashSet<>(entrySet);
            for(OptionMap.Entry<K, V> entry : es) {
                if(entry.getKey() == key) {
                    entrySet.remove(entry);
                    break;
                }
            }
            keySet.remove(key);
        }
    }

    public V getValue(K key) {
        if(keySet.contains(key)) {
            for(OptionMap.Entry<K, V> entry : entrySet) {
                if(entry.getKey() == key) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    public Class<?> getType(K key) {
        if(keySet.contains(key)) {
            for(OptionMap.Entry<K, V> entry : entrySet) {
                if(entry.getKey() == key) {
                    return entry.getType();
                }
            }
        }
        return null;
    }

}
