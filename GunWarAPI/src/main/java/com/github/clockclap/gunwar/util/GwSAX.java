/*
 * Copyright (c) 2021, ClockClap. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.github.clockclap.gunwar.util;

import com.github.clockclap.gunwar.GwAPI;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

@GwAPI
final class GwSAX extends DefaultHandler {

    private Map<String, String> map;
    private Map<String, Map<String, String>> attr;

    public GwSAX() {
        map = new HashMap<>();
        attr = new HashMap<>();
    }

    public Map<String, String> valueMap() {
        return map;
    }

    public Map<String, Map<String, String>> attributeMap() {
        return attr;
    }

    public void startDocument() {
    }

    public void endDocument() {
    }

    private String tmp = "";

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        String tmp = this.tmp + "." + qName;
        if(tmp.startsWith(".")) tmp = tmp.substring(1);
        this.tmp = tmp;
        attr.put(tmp, new HashMap<>());
        for(int i = 0; i < attributes.getLength(); i++) {
            String qname = attributes.getQName(i);
            String value = attributes.getValue(i);
            attr.get(tmp).put(qname, value);
        }
    }

    public void endElement(String uri, String localName, String qName) {
        this.tmp = this.tmp.substring(0, Math.max(0, this.tmp.length() - qName.length() - 1));
    }

    public void characters(char[] ch, int start, int length) {
        String str = new String(ch, start, length);
        map.put(tmp, str);
    }

}
