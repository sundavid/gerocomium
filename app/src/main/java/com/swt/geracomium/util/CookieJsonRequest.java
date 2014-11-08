package com.swt.geracomium.util;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.swt.geracomium.entity.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dsun on 11/1/14.
 */
public class CookieJsonRequest extends JsonObjectRequest {

    private Map<String, String> mHeaders = new HashMap<String, String>(1);
    private Map<String, String> params;

    public CookieJsonRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                             Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
        mHeaders.put("Content-Type", "application/json");
    }

    public CookieJsonRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                             Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        mHeaders.put("Content-Type", "application/json");
        mHeaders.put("Accept", "application/json");
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
    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        if (this.getMethod() != Method.GET) params.put("csrfmiddlewaretoken", Utils.csrf_token);
        Log.d("connect", params.toString());
        return params;
    }

    ;

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            Map<String, String> responseHeaders = response.headers;
            String rawCookies = responseHeaders.get("Set-Cookie");
            Log.v("connect", "Response Cookie: " + rawCookies);
            Cookie.getCookie().parse(rawCookies);
            String dataString = new String(response.data, "UTF-8");
            return Response.success(new JSONObject(dataString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        }
    }

}
