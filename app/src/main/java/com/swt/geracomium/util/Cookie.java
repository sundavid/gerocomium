package com.swt.geracomium.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dsun on 11/1/14.
 */
public class Cookie {
    public static Cookie cookie;

    static {
        cookie = new Cookie();
    }

    public Map<String, String> cookies = new HashMap<String, String>();

    public static Cookie getCookie() {
        return cookie;
    }

    public void setCookie(String key, String value) {
        cookies.put(key, value);
    }

    public String getCookie(String key, String defaultValue) {
        if (!cookies.containsKey(key)) return defaultValue;
        return cookies.get(key);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        boolean isFirst = true;
        for (String key : cookies.keySet()) {
            if (!isFirst) {
                sb.append("; ");
            }
            sb.append(key);
            sb.append("=");
            sb.append(cookies.get(key));
            isFirst = false;
        }
        return sb.toString();
    }

    public void parse(String str) {
        if (str == null) return;
        for (String keyValue : str.split("; ")) {
            String[] keyValuePair = keyValue.split("=");
            if (keyValuePair.length != 2) continue;
            cookies.put(keyValuePair[0], keyValuePair[1]);
        }
    }
}
