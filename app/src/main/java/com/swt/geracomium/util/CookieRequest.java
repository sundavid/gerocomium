package com.swt.geracomium.util;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.swt.geracomium.entity.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dsun on 11/9/14.
 */
public class CookieRequest {
    Map<String, String> headers = new HashMap<String, String>();

    public void setCookie(String key, String value) {
        Cookie.getCookie().setCookie(key, value);
    }

    public Map<String, String> getParams(int method, Map<String, String> params)
            throws com.android.volley.AuthFailureError {
        if (method != Request.Method.GET) params.put("csrfmiddlewaretoken", Utils.csrf_token);
        return params;
    }

    public Map<String, String> getHeaders(Map<String, String> headers) throws AuthFailureError {
        for (String key: headers.keySet()) {
            this.headers.put(key, headers.get(key));
        }
        this.headers.put("Cookie", Cookie.getCookie().toString());
        Log.v("Request Cookie", Cookie.getCookie().toString());
        return this.headers;
    }

    public void parseNetworkResponse(NetworkResponse response) {
        Map<String, String> responseHeaders = response.headers;
        String rawCookies = responseHeaders.get("Set-Cookie");
        Log.v("Response Cookie", rawCookies == null ? "null":rawCookies);
        Cookie.getCookie().parse(rawCookies);
    }
}
