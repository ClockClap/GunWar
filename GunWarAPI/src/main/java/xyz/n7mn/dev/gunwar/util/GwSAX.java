package xyz.n7mn.dev.gunwar.util;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

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
