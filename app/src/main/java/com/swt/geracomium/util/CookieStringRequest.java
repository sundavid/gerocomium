package com.swt.geracomium.util;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dsun on 11/1/14.
 */
public class CookieStringRequest extends StringRequest {

    private Map<String, String> mHeaders = new HashMap<String, String>();

    public CookieStringRequest(String url, Response.Listener<String> listener,
                               Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public CookieStringRequest(int method, String url, Response.Listener<String> listener,
                               Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public void setCookie(String key, String value) {
        Cookie.getCookie().setCookie(key, value);
    }

    @Override
    public Map<String, String> getHeaders() {
        Log.v("connect", "request Cookie: " + Cookie.getCookie().toString());
        mHeaders.put("Cookie", Cookie.getCookie().toString());
        return mHeaders;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            Map<String, String> responseHeaders = response.headers;
            String rawCookies = responseHeaders.get("Set-Cookie");
            Log.v("connect", "Response Cookie: " + rawCookies);
            Cookie.getCookie().parse(rawCookies);
            String dataString = new String(response.data, "UTF-8");
            return Response.success(dataString, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}
